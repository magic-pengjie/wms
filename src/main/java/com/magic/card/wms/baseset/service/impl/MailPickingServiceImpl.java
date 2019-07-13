package com.magic.card.wms.baseset.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.magic.card.wms.baseset.mapper.MailPickingMapper;
import com.magic.card.wms.baseset.mapper.OrderInfoMapper;
import com.magic.card.wms.baseset.model.dto.OrderCommodityDTO;
import com.magic.card.wms.baseset.model.dto.OrderInfoDTO;
import com.magic.card.wms.baseset.model.po.MailPicking;
import com.magic.card.wms.baseset.model.po.Order;
import com.magic.card.wms.baseset.model.xml.OrderCommodityXml;
import com.magic.card.wms.baseset.model.xml.OrderDTO;
import com.magic.card.wms.baseset.model.xml.PersionXml;
import com.magic.card.wms.baseset.model.xml.RequestOrderXml;
import com.magic.card.wms.baseset.model.xml.ResponsesXml;
import com.magic.card.wms.baseset.service.IMailPickingService;
import com.magic.card.wms.baseset.service.IOrderCommodityService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.utils.Digest;
import com.magic.card.wms.common.utils.HttpUtil;
import com.magic.card.wms.common.utils.PoUtil;
import com.magic.card.wms.common.utils.XmlUtil;
import com.magic.card.wms.foreign.util.DateConverter;
import com.thoughtworks.xstream.XStream;

import lombok.extern.slf4j.Slf4j;

/**
 * com.magic.card.wms.baseset.service.impl
 *  快递篮拣货服务接口实现类
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/25/025 9:58
 * @since : 1.0.0
 */
@Slf4j
@Service
public class MailPickingServiceImpl extends ServiceImpl<MailPickingMapper, MailPicking> implements IMailPickingService {
    @Autowired
    private IOrderCommodityService orderCommodityService;
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    /**
     * 获取拣货单所有漏检商品数据信息
     *
     * @param pickNo
     * @param state
     * @return
     */
    @Override
    public List<Map> omitOrderCommodityList(String pickNo, Integer state) {
        return baseMapper.omitOrderCommodityList(pickNo, state);
    }

    /**
     * 加载拣货单所有拣货篮数据
     *
     * @param pickNo
     * @return
     */
    @Override
    public List<Map> loadMailPickings(String pickNo) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("pick_no", pickNo).
                eq("state", StateEnum.normal.getCode()).
                orderBy("basket_num");
        List<Map> mailPickings = baseMapper.selectMaps(wrapper);

        if (CollectionUtils.isNotEmpty(mailPickings)){
            List<String> orderNos = mailPickings.stream().map(
                    map -> map.get("orderNo").toString()
                ).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(orderNos)) {
                Map<String, List> orderCommodities = orderCommodityService.loadBatchOrderCommodityGrid(orderNos);
                mailPickings.stream().forEach(mailPicking ->
                        mailPicking.put(
                                "orderCommodities",
                                orderCommodities.get(
                                    MapUtils.getString(mailPicking, "orderNo")
                                )
                        )
                );
            }


        }

        return mailPickings;
    }

    /**
     * 自动生成快递篮拣货单服务
     *
     * @param mailPicking
     * @param operator
     */
    @Override @Transactional
    public void generatorMailPicking(MailPicking mailPicking, String operator) {
        PoUtil.add(mailPicking, operator);
        this.insert(mailPicking);
        
        try {
			//sendOrder(mailPicking.getPickNo(), null);
			log.info("send order success ");
		} catch (Exception e) {
			log.error("send order error:{}",e);
		}
    }

    /**
     * 自动生成配货单清单
     * @param pickNo 拣货单号
     * @return
     */
    @Override
    public List<Map> generatorInvoiceList(String pickNo) {
        //拣货区拿去货物
        List<Map> invoiceList = this.baseMapper.invoiceList(
                pickNo,
                Constants.BILL_STATE_CANCEL,
                Constants.PICKING_AREA_CODE
        );

        if (invoiceList == null && invoiceList.isEmpty()) {
            throw OperationException.addException("配货单生成异常，数据为空请核实数据！");
        }

        //补货预警提示
        List<Map> replenishmentNotices = Lists.newLinkedList();
        invoiceList.forEach(invoice->{
            int bayNums = MapUtils.getIntValue(invoice, "bayNums");
            int availableNums = MapUtils.getIntValue(invoice, "availableNums");
            // 可用量小于购买量
            if (availableNums < bayNums) {
                replenishmentNotices.add(invoice);
            }
        });

        new Thread(() -> {
            //TODO 拣货区库存不足 补货提醒功能待实现
            if (CollectionUtils.isNotEmpty(replenishmentNotices)) {
                replenishmentNotices.forEach(map -> {
                    log.warn("拣货区库存不足请及时补货： 商品条形码：{}", map.get("barCode"));
                });
            }

        }, "JHQ-Notice-Thread-NO." + System.currentTimeMillis()).start();

        return invoiceList;
    }

    /**
     * 自动更新拣货篮所有订单完成状态
     *
     * @param pickNo
     */
    @Override @Transactional
    public void autoUpdatePickingFinishState(String pickNo, String operator) {
        // TODO 自动更新拣货篮所有订单完成状态
    }

    /**
     * 更新拣货篮指定订单 -> 完成状态
     *
     * @param pickNo
     * @param orderNo
     */
    @Override @Transactional
    public void updatePickingFinishState(String pickNo, String orderNo, String operator) {
        // 获取对应的拣货篮信息
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("pick_no", pickNo).
                eq("order_no", orderNo);
        MailPicking mailPicking = selectOne(wrapper);

        if (mailPicking == null && mailPicking.getIsFinish() == 1) return;

        if (baseMapper.countOrderCommodityUnfinished(orderNo) == 0) {
            // 设置状态
            mailPicking.setIsFinish(1);
            PoUtil.update(mailPicking, operator);
            updateById(mailPicking);
        }

    }

    /**
     * 配货单清单检查数据
     *
     * @param pickNo
     * @param commodityCode
     * @return
     */
    @Override
    public List<Map> invoiceCheckList(String pickNo, String commodityCode) {
        return baseMapper.invoiceCheckList(pickNo, commodityCode);
    }

    /**
     * 获取拣货单各个拣货篮的完成状态
     *
     * @param pickNo
     * @return
     */
    @Override
    public List<Map> obtainPickingFinishState(String pickNo) {
        return baseMapper.obtainPickingFinishState(pickNo);
    }

	@Override
	public void sendOrder(String pickNo,String orderNo) throws UnsupportedEncodingException {
		//查询订单信息
		List<OrderInfoDTO>  orderInfo = orderInfoMapper.selectOrderByNo(pickNo, orderNo);
		if(ObjectUtils.isEmpty(orderInfo)) {
			throw new OperationException(-1,"订单数据不存在");
		}
		//组装订单xml格式报文
		OrderDTO order = new OrderDTO();
		order.setEcCompanyId(Constants.ECCOMPANY_ID);
		order.setMsg_type(Constants.MSG_TYPE_CREATE); //创建订单
		for (OrderInfoDTO orderInfoDTO : orderInfo) {
			//订单无商品，则无需发送
			if(ObjectUtils.isEmpty(orderInfoDTO.getCommodities())) {
				continue;
			}
			String xml = getXmlBaseInfo(orderInfoDTO);
			
			log.info("订单xml:\n{}",xml);
			order.setLogistics_interface(Digest.urlEncode(xml));
			order.setData_digest(signature(order));
			//TODO 推送数据至邮政
			String reason = "数据推送异常";
			try {
				String result = HttpUtil.restPost("", order);
				log.info("restPost finish result:{}",result);
				ResponsesXml response = new ResponsesXml();
				response = (ResponsesXml) XmlUtil.parseXml(response, xml);
				boolean success = response.getResponseItems().get(0).getSuccess();
				if(success) {
					reason = response.getResponseItems().get(0).getReason();
				}
				//修改发送状态
				updateSendFlag(orderInfoDTO, success,reason);
			} catch (Exception e) {
				log.error("parseXml error:{}",e);
				updateSendFlag(orderInfoDTO, false,reason);
				continue;
			}
		}
		
	}
	public String getXmlBaseInfo(OrderInfoDTO orderInfoDTO) {
		//订单基础信息
		RequestOrderXml requestOrderXml = new RequestOrderXml();
		requestOrderXml.setEcCompanyId(Constants.ECCOMPANY_ID);
		requestOrderXml.setLogisticProviderID(Constants.LOGISTICPROVIDER_ID);
		requestOrderXml.setTxLogisticID(orderInfoDTO.getOrderNo());
		requestOrderXml.setMailNo(orderInfoDTO.getMailNo());
		requestOrderXml.setCustomerId(Constants.CUSTOMERID);
		requestOrderXml.setOrderType(Constants.ORDER_TYPE);
		requestOrderXml.setServiceType(Constants.ORDER_TYPE);
		requestOrderXml.setWeight(orderInfoDTO.getPresetWeight().longValue());
		//收货人信息
		PersionXml sender = new PersionXml();
		sender.setName(orderInfoDTO.getReciptName());
		sender.setPostCode(orderInfoDTO.getPostCode());
		sender.setPhone(orderInfoDTO.getReciptPhone());
		sender.setMobile(orderInfoDTO.getReciptPhone());
		sender.setProv(orderInfoDTO.getProv());
		sender.setCity(orderInfoDTO.getCity());
		sender.setAddress(orderInfoDTO.getReciptAddr());
		requestOrderXml.setSender(sender);
		List<OrderCommodityDTO> commodities = orderInfoDTO.getCommodities();
		List<OrderCommodityXml> orderCommoditys = new ArrayList<OrderCommodityXml>();
		commodities.forEach(OrderCommodityDTO->{
			OrderCommodityXml commodityXml = new OrderCommodityXml();
			commodityXml.setItemName(OrderCommodityDTO.getCommodityName());
			commodityXml.setNumber(OrderCommodityDTO.getNumbers());
			orderCommoditys.add(commodityXml);
			
		});
		String xml = XmlUtil.toXml(requestOrderXml);
		return xml;
	}
	
	/**
	 * 订单签名
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String signature(OrderDTO order) throws UnsupportedEncodingException {
		StringBuffer checkStr = new StringBuffer();
		checkStr.append(order.getEcCompanyId())
				.append(order.getMsg_type())
				.append(order.getLogistics_interface());
		String signature = Digest.urlEncode(Digest.Md5Base64(checkStr.toString()));
		return signature;
	}
	
	/**
	 * 修改发送
	 */
	public void updateSendFlag(OrderInfoDTO orderInfoDTO,boolean success,String failReason) {
		MailPicking mailPicking = new MailPicking();
		mailPicking.setId(orderInfoDTO.getId());
		if(success){
			mailPicking.setSendState("1");
		}else {
			mailPicking.setFailReason(failReason);
		}
		mailPicking.setSendNums(orderInfoDTO.getSendNums()+1);
		mailPicking.setUpdateTime(new Date());
		this.updateById(mailPicking);
	}
}
