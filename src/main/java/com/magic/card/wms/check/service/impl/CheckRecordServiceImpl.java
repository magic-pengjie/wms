package com.magic.card.wms.check.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
import com.magic.card.wms.check.service.ICheckRecordService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.SessionKeyConstants;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.service.RedisService;
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
	private RedisService redisService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public CheckRecord countCheckRecord(CheckCountDto dto) throws BusinessException {
		/**
		 * 	盘点维度：area_code：库区，commodity_id：商品id，customer_id：客户id
		 *  1，根据盘点维度查询统计库表↓
		 *  2.登记盘点记录表
		 *  3.冻结库位
		 *  4.盘点完成后，修改盘点记录，更新库位库存
		 *  5.打印盘点记录单
		 */
		Wrapper<StorehouseConfig> scWrapper = new EntityWrapper<StorehouseConfig>();
		log.info("===>> countCheckRecord.params:{}", dto);
		if(!StringUtil.isNullOrEmpty(dto.getCustomerId())) {//按商家盘点
			scWrapper.eq("customer_id", dto.getCustomerId());
		}else  if(!CollectionUtils.isEmpty(dto.getCommodityId())) {//按商品盘点
			scWrapper.in("commodity_id", dto.getCommodityId());
		}else if(!CollectionUtils.isEmpty(dto.getAreaCode())) {//按库区盘点
			scWrapper.in("area_code", dto.getAreaCode());
		}
		scWrapper.eq("state", StateEnum.normal.getCode());
		//根据盘点维度查询库位关系表
		List<StorehouseConfig> storeList = storehouseConfigMapper.selectList(scWrapper);
		if(CollectionUtils.isEmpty(storeList)) {
			log.error("===>> select.storehouseConfig is empty,req:{}", dto);	
			throw new BusinessException(0001, "未查询到库存！");
		}
		//获取当前登录人信息
		User user = (User)redisService.get(SessionKeyConstants.USER_SESSION_KEY+dto.getUserId());
		
		List<String> commodityIdList = null;
		List<String> storehouseIdList = null;
		//统计库位库存信息
		storeList.stream().forEach(sc -> {
			commodityIdList.add(sc.getCommodityId());
			storehouseIdList.add(sc.getStorehouseId());
		});
		if(CollectionUtils.isEmpty(commodityIdList)) {
			throw new BusinessException(400002, "未查询到商品的库存信息！");
		}
		List<Commodity> commodityInfoList = commodityInfoMapper.selectBatchIds(commodityIdList);
		List<String> skuCodeList = commodityInfoList.stream().map(Commodity::getCommodityCode).collect(Collectors.toList());
		Wrapper<CommoditySku> skuWrapper = new EntityWrapper<CommoditySku>();
		skuWrapper.in("sku_code", skuCodeList);
		skuWrapper.eq("state", StateEnum.normal.getCode());
		//查询商品信息+sku
		List<CommoditySku> commSkuInfoList = commoditySkuMapper.selectList(skuWrapper);
		
		if(CollectionUtils.isEmpty(storehouseIdList)) {
			throw new BusinessException(400003, "未查询库位信息！");
		}
		//查询库位信息
		List<StorehouseInfo> sotreInfoList = storehouseInfoMapper.selectBatchIds(storehouseIdList);
		
		
		//统计商品零拣库位库存
		//统计商品 存储库位(多个)库存
		//统计总库存
		
		//冻结库位
		List<String> storeIdList = storeList.stream().map(StorehouseConfig::getStorehouseId).collect(Collectors.toList());
		Wrapper<StorehouseInfo> siWrapper = new EntityWrapper<StorehouseInfo>();
		siWrapper.in("id", storeIdList);
		StorehouseInfo si = new StorehouseInfo();
		si.setIsFrozen("Y");//冻结
		si.setUpdateTime(new Date());
		si.setUpdateUser(user.getName());
		Integer updateStoreFrozen = storehouseInfoMapper.update(si,siWrapper);
		log.info("===>> storehouceInfo.updateByIds,chanage:[{}] rows!", updateStoreFrozen);
		
		
		return null;
	}

}