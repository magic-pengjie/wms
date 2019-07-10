package com.magic.card.wms.check.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.mapper.CommodityInfoMapper;
import com.magic.card.wms.baseset.mapper.CommoditySkuMapper;
import com.magic.card.wms.baseset.mapper.StorehouseConfigMapper;
import com.magic.card.wms.baseset.mapper.StorehouseInfoMapper;
import com.magic.card.wms.baseset.model.po.Commodity;
import com.magic.card.wms.baseset.model.po.CommoditySku;
import com.magic.card.wms.baseset.model.po.StorehouseConfig;
import com.magic.card.wms.baseset.model.po.StorehouseInfo;
import com.magic.card.wms.check.mapper.CheckRecordMapper;
import com.magic.card.wms.check.model.po.CheckRecord;
import com.magic.card.wms.check.model.po.dto.CheckCountDto;
import com.magic.card.wms.check.model.po.dto.CheckRecordInfoDto;
import com.magic.card.wms.check.model.po.dto.CheckRecordStartDto;
import com.magic.card.wms.check.model.po.dto.QueryCheckRecordDto;
import com.magic.card.wms.check.service.ICheckRecordService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.BillState;
import com.magic.card.wms.common.model.enums.IsFrozenEnum;
import com.magic.card.wms.common.model.enums.SessionKeyConstants;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.model.enums.StoreTypeEnum;
import com.magic.card.wms.common.model.po.UserSessionUo;
import com.magic.card.wms.common.service.RedisService;
import com.magic.card.wms.common.utils.WebUtil;
import com.magic.card.wms.user.model.po.User;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 	盘点记录表 服务实现类
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-27
 */
@Slf4j
@Service
public class CheckRecordServiceImpl extends ServiceImpl<CheckRecordMapper, CheckRecord> implements ICheckRecordService {

	@Autowired
	private StorehouseConfigMapper storehouseConfigMapper;
	
	@Autowired 
	private StorehouseInfoMapper storehouseInfoMapper;
	
	@Autowired
	private CommodityInfoMapper commodityInfoMapper;
	
	@Autowired
	private CommoditySkuMapper commoditySkuMapper;
	
	@Autowired
	private WebUtil webUtil;
	
	//根据盘点条件查询统计库存信息
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public List<CheckRecordInfoDto> countCheckRecord(CheckCountDto dto) throws BusinessException {
		/**
		 * 	盘点维度：area_code：库区，commodity_id：商品id，customer_id：客户id
		 *  1，根据盘点维度查询统计库表↓
		 *  2.登记盘点记录表
		 *  3.冻结库位
		 *  4.盘点完成后，修改盘点记录，更新库位库存
		 *  5.打印盘点记录单
		 */
		log.info("===>> CountCheckRecord.params:{}", dto);
		List<CheckRecordInfoDto> commStoreList =  new ArrayList<CheckRecordInfoDto>();
		QueryCheckRecordDto crDto = new QueryCheckRecordDto();
		if(!StringUtils.isEmpty(dto.getCustomerId())) {//按商家盘点
			crDto.setCustomerId(dto.getCustomerId());
		}
		if(!CollectionUtils.isEmpty(dto.getCommodityId())) {//按商品盘点
			crDto.setCommodityIdList(dto.getCommodityId());
		}
		List<Long> storehouseIdList = new ArrayList<Long>();
		if(!CollectionUtils.isEmpty(dto.getAreaCode())) {//按库区盘点
			Wrapper<StorehouseInfo> siWrapper = new EntityWrapper<StorehouseInfo>();
			siWrapper.eq("state", StateEnum.normal.getCode());
			siWrapper.in("area_code", dto.getAreaCode());
			List<StorehouseInfo> storeInfoList = storehouseInfoMapper.selectList(siWrapper);
			if(!CollectionUtils.isEmpty(storeInfoList)) {
				storehouseIdList = storeInfoList.stream().map(StorehouseInfo::getId).collect(Collectors.toList());
			}
		}
		if(!CollectionUtils.isEmpty(storehouseIdList)) {
			crDto.setStoreIdList(storehouseIdList);
		}
		log.info("===>> queryCommoidtyStoreList:{}",crDto);
		//按商家的库区或商品查询库存信息
		commStoreList = storehouseConfigMapper.queryCommoidtyStoreList(crDto);
		if(CollectionUtils.isEmpty(commStoreList)) {
			log.error("===>> select.storehouseConfig is empty,req:{}", dto);	
			throw new BusinessException(40001, "未查询到库存！");
		}
		//统计盘点总库存
		long allCount = commStoreList.stream().map(CheckRecordInfoDto::getStoreNums).count();
		return commStoreList;
	}

	//开始盘点
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public boolean checkRecordStart(CheckRecordStartDto dto) throws BusinessException {
		//获取当前登录人信息
		UserSessionUo userSession = webUtil.getUserSession();
		//冻结库位
		Wrapper<StorehouseInfo> siWrapper = new EntityWrapper<StorehouseInfo>();
		siWrapper.in("id", dto.getStoreIdList());
		StorehouseInfo si = new StorehouseInfo();
		si.setIsFrozen(IsFrozenEnum.FROZEN.getCode());//冻结
		si.setUpdateTime(new Date());
		si.setUpdateUser(userSession.getName());
		Integer updateStoreFrozen = storehouseInfoMapper.update(si,siWrapper);
		log.info("===>> storehouceInfo.updateByIds,chanage:[{}] rows!", updateStoreFrozen);
		log.info("===>> 冻结成功！");
		
		//生成盘点记录
		List<CheckRecordInfoDto> commStoreList =  new ArrayList<CheckRecordInfoDto>();
		QueryCheckRecordDto crDto = new QueryCheckRecordDto();
		if(!CollectionUtils.isEmpty(dto.getStoreIdList())) {
			crDto.setStoreIdList(dto.getStoreIdList());
		}
		//按商家的库区或商品查询库存信息
		commStoreList = storehouseConfigMapper.queryCommoidtyStoreList(crDto);
		if(CollectionUtils.isEmpty(commStoreList)) {
			log.error("===>> select.storehouseConfig is empty,req:{}", dto);	
			throw new BusinessException(40001, "未查询到库存！");
		}
		List<CheckRecord> crList = new ArrayList<CheckRecord>();
		Date date = new Date();
		commStoreList.stream().forEach(cs->{
			CheckRecord cr = new CheckRecord();
			BeanUtils.copyProperties(cs, cr);
			if(cs.getHouseCode()!=null && StoreTypeEnum.JHQ.getCode().equals(cs.getHouseCode())) {
				cr.setStorehouseS(cs.getStorehouseId());
			}else {
				cr.setStorehouseP(cs.getStoreCode());
			}
			cr.setBillState(BillState.checker_save.getCode());
			cr.setCheckDate(date);
			cr.setCheckUser(userSession.getName());
			cr.setCreateTime(date);
			cr.setCreateUser(userSession.getName());
			cr.setState(StateEnum.normal.getCode());
			crList.add(cr);
		});
		log.info("===>> Add CheckRecordInfo List:{}",crList);
		if(!CollectionUtils.isEmpty(crList)) {
			boolean insertBatch = this.insertBatch(crList);
			log.info("===>> Add CheckRecordInfo :{}", insertBatch);
			return insertBatch;
		}
		return false;
	}

	@Override
	public boolean updateRecordInfo(CheckRecordInfoDto dto) throws BusinessException {
		
		return false;
	}
	
	
	
	

}
