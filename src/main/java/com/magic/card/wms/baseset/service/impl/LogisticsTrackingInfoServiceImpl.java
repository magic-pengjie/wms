package com.magic.card.wms.baseset.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.mapper.LogisticsTrackingInfoMapper;
import com.magic.card.wms.baseset.mapper.MailPickingMapper;
import com.magic.card.wms.baseset.model.dto.MailDTO;
import com.magic.card.wms.baseset.model.po.LogisticsTrackingInfo;
import com.magic.card.wms.baseset.model.po.MailPicking;
import com.magic.card.wms.baseset.model.po.WarningAgentInfo;
import com.magic.card.wms.baseset.model.vo.LogisticsRepHeader;
import com.magic.card.wms.baseset.model.vo.LogisticsReqHeader;
import com.magic.card.wms.baseset.model.vo.MailDetailVO;
import com.magic.card.wms.baseset.service.ILogisticsTrackingInfoService;
import com.magic.card.wms.baseset.service.IMailPickingService;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.AgentTypeEnum;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.utils.DateUtil;
import com.magic.card.wms.common.utils.Digest;
import com.magic.card.wms.common.utils.HttpUtil;
import com.magic.card.wms.warehousing.model.po.PurchaseBill;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 物流跟踪信息表 服务实现类
 * </p>
 *
 * @author pengjie
 * @since 2019-07-13
 */
@Service
@Slf4j
public class LogisticsTrackingInfoServiceImpl extends ServiceImpl<LogisticsTrackingInfoMapper, LogisticsTrackingInfo> implements ILogisticsTrackingInfoService {

	private static String food_warning_time=" 16:00:00";
	
	@Value("${post.query.trackingInfo.url}")
	private String postUrl;
	@Autowired
	private IMailPickingService mailPickingService;
	@Autowired
	private MailPickingMapper mailPickingMapper;
	
	@Override
	public ResponseData getTrackingInfo(List<String> mailNos) throws UnsupportedEncodingException {
		LogisticsReqHeader header = new LogisticsReqHeader();
		String msgBody = JSONObject.toJSONString(mailNos);
		String dataDigest = Digest.Md5Base64(msgBody+header.getReceiveID());
		StringBuffer sb = new StringBuffer(postUrl);
		sb.append("?sendID=").append(header.getSendID())
		  .append("&proviceNo=").append(header.getProviceNo())
		  .append("&msgKind=").append(header.getMsgKind())
		  .append("&serialNo=").append(header.getReceiveID()+header.getSendDate())
		  .append("&sendDate=").append(header.getSendDate())
		  .append("&receiveID=").append(header.getReceiveID())
		  .append("&dataType=").append(header.getDataType())
		  .append("&dataDigest=").append(dataDigest)
		  .append("&msgBody=").append(Digest.urlEncode(msgBody));
		log.info("reuqest url:{}",sb.toString());
		String resultStr = HttpUtil.restGet(sb.toString());
		LogisticsRepHeader rspHeader = JSONObject.parseObject(resultStr,LogisticsRepHeader.class);
		log.info("response result:{}",resultStr);
		if(rspHeader.getResponseState()) {
			return ResponseData.ok(rspHeader.getResponseItems());
		}
		return ResponseData.ok(rspHeader.getErrorDesc());
	}
	/***
	 * 查询无物流信息的快递单
	 */
	@Override
	public Page<MailDetailVO> selectNonLogisticsInfoList(MailDTO dto,PageInfo pageInfo) {
		if(ObjectUtils.isEmpty(dto.getIsFinish())) {
			dto.setIsFinish(1);
		}
		if(ObjectUtils.isEmpty(dto.getLogisticsState())) {
			dto.setLogisticsState(0);
		}
		Page<MailDetailVO> page = new Page<>(pageInfo.getCurrent(), pageInfo.getPageSize());
		Integer counts = mailPickingMapper.getNonLogisticsInfoCounts(dto);
		if(counts>0) {
			page = mailPickingMapper.getNonLogisticsInfoList(page, dto);
		}
		return page;
	}

	/***
	 * 对接邮政进行快递物流信息批量查询(每次30条)。
	 */
	@Override
	@Transactional
	public void runLogisticsInfo() {
		MailDTO dto = new MailDTO();
		dto.setLogisticsState(Constants.ZERO);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageSize(30);
		List<MailPicking> list = selectNonLogisticsInfoListToPost(pageInfo).getRecords();
		int current = 1;
		while(!ObjectUtils.isEmpty(list)) {
			log.info("runLogisticsInfo select size:{}",list.size());
			List<String> mailNos = list.stream().map(e->e.getMailNo()).collect(Collectors.toList());
			try {
				//发送邮政
				ResponseData<LogisticsTrackingInfo> data = getTrackingInfo(mailNos);
				if(data.getStatus()==0) {
					List<LogisticsTrackingInfo> logisticsList = (List<LogisticsTrackingInfo>)data.getData();
					mailNos = logisticsList.stream().map(e->e.getTraceNo()).collect(Collectors.toList());
					Wrapper<LogisticsTrackingInfo> w = new EntityWrapper<>();
					w.in("trace_no", mailNos);
					this.delete(w);
					this.insertBatch(logisticsList);
					//修改快递单状态
					updateMialState(list, mailNos);
				}
				if(list.size()<30) {
					break;
				}
				pageInfo.setCurrent(++current);
				list = selectNonLogisticsInfoListToPost(pageInfo).getRecords();
			} catch (Exception e) {
				log.error("runLogisticsInfo getTrackingInfo error:{}",e);
			}
		}
	}
	
	/***
	 * 获取无物流信息的包裹，发送邮政获取物流信息
	 * @return
	 */
	public Page<MailPicking> selectNonLogisticsInfoListToPost(PageInfo pageInfo) {
		Page<MailPicking> page = new Page<>(pageInfo.getCurrent(), pageInfo.getPageSize());
		//查询已发送邮政且无物流信息的快递单号
		Wrapper<MailPicking> w = new EntityWrapper<>();
		w.eq("logistics_state",Constants.ZERO);
		w.eq("state",Constants.STATE_1);
		w.eq("is_finish",Constants.ONE);
		List<String> orderParam = new ArrayList<>();
		orderParam.add("create_time");
		orderParam.add("update_time");
		orderParam.add("pick_no");
		w.orderDesc(orderParam);
		page = mailPickingService.selectPage(page, w);
		return page;
	}
	
	/***
	 * 物流信息预警
	 */
	@Override
	public void runLogisticsInfoWarning() {
		//查询当天无物流信息数据
		String endDateStr = DateUtil.getStringDateShort();
		String startDateStr = DateUtil.getNextDay(endDateStr, "-1");
		Date startDate = DateUtil.strToDateLong(startDateStr+food_warning_time);
		Date endDate = DateUtil.strToDateLong(endDateStr+food_warning_time);
		List<MailPicking>  curList = mailPickingMapper.getLogisticsInfoWarningList(startDate, endDate);
		//查询无物流的历史快递单
		List<MailPicking>  historyList = mailPickingMapper.getLogisticsInfoWarningList(startDate, null);
		if(ObjectUtils.isEmpty(curList) && ObjectUtils.isEmpty(historyList)) {
			log.info("未查询到无物流信息数据");
			return ;
		}
		WarningAgentInfo warningAgentInfo = new WarningAgentInfo();
		warningAgentInfo.setTypeCode(AgentTypeEnum.Logistics.getCode());
		warningAgentInfo.setTypeName(AgentTypeEnum.Logistics.getName());
		StringBuffer sb = new StringBuffer();
		if(!ObjectUtils.isEmpty(curList)) {
			sb.append("未查询到物流流转信息:")
			  .append(curList.size())
			  .append("条,");
		}else if(!ObjectUtils.isEmpty(curList)){
			sb.append("未查询到历史物流信息:")
			  .append(historyList.size())
			  .append("条,");
		}
		warningAgentInfo.setAgentName(sb.toString());
		warningAgentInfo.setAgentState(Constants.STATE_0);
		warningAgentInfo.insert();
	}
	
	/***
	 * 修改快递单物流状态
	 * @param list
	 * @param mailNos
	 */
	public void updateMialState(List<MailPicking> list,List<String> mailNos) {
		List<MailPicking> updateList = new ArrayList<>();
		for (String mailNo : mailNos) {
			for (MailPicking mailPicking : list) {
				if(mailNo.equals(mailPicking.getMailNo())) {
					mailPicking.setLogisticsState(Constants.STATE_1);
					mailPicking.setUpdateTime(new Date());
					updateList.add(mailPicking);
				}
			}
		}
		mailPickingService.updateBatchById(updateList);
	}

}
