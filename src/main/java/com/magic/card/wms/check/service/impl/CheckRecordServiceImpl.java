package com.magic.card.wms.check.service.impl;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.magic.card.wms.baseset.mapper.StorehouseConfigMapper;
import com.magic.card.wms.baseset.mapper.StorehouseInfoMapper;
import com.magic.card.wms.baseset.model.po.StorehouseConfig;
import com.magic.card.wms.baseset.model.po.StorehouseInfo;
import com.magic.card.wms.check.mapper.CheckRecordMapper;
import com.magic.card.wms.check.model.dto.AuditCheckRecordDto;
import com.magic.card.wms.check.model.dto.CheckCountDto;
import com.magic.card.wms.check.model.dto.CheckRecordCanellDto;
import com.magic.card.wms.check.model.dto.CheckRecordDto;
import com.magic.card.wms.check.model.dto.CheckRecordInfoDto;
import com.magic.card.wms.check.model.dto.CheckRecordStartDto;
import com.magic.card.wms.check.model.dto.QueryAuditCheckRecordDto;
import com.magic.card.wms.check.model.dto.QueryCheckRecordDto;
import com.magic.card.wms.check.model.po.CheckRecord;
import com.magic.card.wms.check.service.ICheckRecordService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.BillState;
import com.magic.card.wms.common.model.enums.IsFrozenEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.model.po.UserSessionUo;
import com.magic.card.wms.common.utils.BeanCopyUtil;
import com.magic.card.wms.common.utils.WebUtil;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

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

	@Resource
	private StorehouseConfigMapper storehouseConfigMapper;
	
	@Resource
	private StorehouseInfoMapper storehouseInfoMapper;
	
	@Resource
	private CheckRecordMapper checkRecordMapper;
	
	@Resource
	private WebUtil webUtil;
	
	//根据盘点条件查询统计库存信息
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public List<CheckRecordInfoDto> countCheckRecord(CheckCountDto dto) throws BusinessException {
		/**
		 * 	盘点维度：customer_id：客户id,  根据盘点维度查询统计库表
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
		return commStoreList;
	}

	//开始盘点
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public List<CheckRecord> checkRecordStart(CheckRecordStartDto dto) throws BusinessException {
		log.info("===>> 冻结开始...");
		//获取当前登录人信息
		UserSessionUo userSession = webUtil.getUserSession();
		//冻结库位
		Integer updateStoreFrozen = updateStoreFrozenState(userSession, dto.getStoreIdList(), IsFrozenEnum.FROZEN);
		if(updateStoreFrozen == 0) {
			log.info("===>> 冻结失败！");
			throw new BusinessException(400005,"未查询到库位信息，冻结失败！");
		}

		//生成盘点记录
		log.info("===>> 生成盘点记录开始..");
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
		log.info("===>> 查询库存：queryCommoidtyStoreList{}", commStoreList);
		List<CheckRecord> crList = new ArrayList<CheckRecord>();
		Date date = new Date();
		commStoreList.forEach(cs->{
			CheckRecord cr = new CheckRecord();
			BeanUtils.copyProperties(cs, cr);
			cr.setStorehouseType(cs.getStoreType());
			cr.setStorehouseCode(cs.getStoreCode());
			cr.setBillState(BillState.checker_save.getCode());//初始化状态
			cr.setCheckDate(date);
			cr.setCheckUser(userSession.getUserNo());
			cr.setCreateTime(date);
			cr.setCreateUser(userSession.getName());
			cr.setState(StateEnum.normal.getCode());
			crList.add(cr);
		});
		log.info("===>> Add CheckRecordInfo List:{}",crList);
		if(!CollectionUtils.isEmpty(crList)) {
			boolean insertBatch = this.insertBatch(crList);
			log.info("===>> 生成盘点记录结束：{}", insertBatch);
			
		}
		return crList;
	}

	//取消盘点
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public boolean cannelCheckRecord(CheckRecordCanellDto dto) throws BusinessException {
		log.info("===>> 取消盘点，解除冻结开始.cannelCheckRecord start...");
		//获取当前登录人信息
		UserSessionUo userSession = webUtil.getUserSession();
		//解冻库位
		Integer updateStoreFrozen = updateStoreFrozenState(userSession, dto.getStoreIdList(), IsFrozenEnum.UNFROZEN);
		if(updateStoreFrozen>0) {
			log.info("===>> 库位解冻成功，盘点记录作废开始...");
			//修改盘点记录为删除
			Wrapper<CheckRecord> crWrapper = new EntityWrapper<CheckRecord>();
			crWrapper.in("id", dto.getCheckRecordIdList());
			crWrapper.eq("state", StateEnum.normal.getCode());
			CheckRecord checkRecord = new CheckRecord();
			checkRecord.setBillState(BillState.checker_cancel.getCode());//盘点记录作废
			checkRecord.setState(StateEnum.delete.getCode());//数据删除
			checkRecord.setUpdateTime(new Date());
			checkRecord.setUpdateUser(userSession.getName());
			log.info("===>> checkRecordMapper.update.params：{}", checkRecord);
			Integer update = checkRecordMapper.update(checkRecord,crWrapper);
			if(update>0) {
				log.info("===>> checkRecordMapper.update,chanage:[{}] rows!", update);
				return true;
			}
		}
		return false;
	}
	

	//盘点结束：修改盘点记录，状态设置为，审批中，后续审批通过后，修改库存
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public boolean updateRecordInfo(List<CheckRecord> checkRecordList) throws BusinessException {
		log.info("===>> 修改盘点记录开始..parmas:{}", checkRecordList);
		//修改盘点记录
		if(!CollectionUtils.isEmpty(checkRecordList)) {
			//获取当前登录人信息
			UserSessionUo userSession = webUtil.getUserSession();
			Date date = new Date();
			boolean checkUpdate = false;
			for (CheckRecord checkRecord : checkRecordList) {
				//计算盘点差异值
				if(!StringUtils.isEmpty(checkRecord.getThirdCheckNums())){
					checkRecord.setDiffNums(checkRecord.getStoreNums()-checkRecord.getThirdCheckNums());
				}else if(!StringUtils.isEmpty(checkRecord.getSecondCheckNums())){
					checkRecord.setDiffNums(checkRecord.getStoreNums()-checkRecord.getSecondCheckNums());
				}else if(!StringUtils.isEmpty(checkRecord.getFirstCheckNums())){
					checkRecord.setDiffNums(checkRecord.getStoreNums()-checkRecord.getFirstCheckNums());
				}
				checkRecord.setBillState(BillState.checker_approving.getCode());//审批中
				checkRecord.setUpdateTime(date);
				checkRecord.setUpdateUser(userSession.getName());
				checkRecord.setState(StateEnum.normal.getCode());
				checkUpdate = this.updateById(checkRecord);
			}
			log.info("===>> update.CheckRecord end :{} rows", checkRecordList.size());
			if(checkUpdate) {
				log.info("===>> StoreHouseInfo UnFrozen start...");
				List<String> storeCodeList = checkRecordList.stream().map(CheckRecord::getStorehouseCode).collect(Collectors.toList());
				Wrapper<StorehouseInfo> siWrapper = new EntityWrapper<StorehouseInfo>();
				siWrapper.in("store_code", storeCodeList);
				siWrapper.eq("state", StateEnum.normal.getCode());
				StorehouseInfo storeInfo = new StorehouseInfo();
				storeInfo.setIsFrozen(IsFrozenEnum.UNFROZEN.getCode());//解冻
				storeInfo.setUpdateTime(date);
				storeInfo.setUpdateUser(userSession.getName());
				Integer update = storehouseInfoMapper.update(storeInfo,siWrapper);
				log.info("===>> update.StorehouseInfo unFrozen :{} rows", update);
			}
		}
		log.info("===>> 修改盘点记录结束..");
		return true;
	}

	//查询盘点记录
	@Override
	public List<CheckRecord> queryCheckRecord(QueryAuditCheckRecordDto auditDto) throws BusinessException{
		CheckRecord cr = new CheckRecord();
		if(!StringUtils.isEmpty(auditDto.getCheckDate())) {
			cr.setCheckDate(auditDto.getCheckDate());
		}
		if(!StringUtils.isEmpty(auditDto.getCheckUser())) {
			cr.setCheckUser(auditDto.getCheckUser());
		}
		if(!StringUtils.isEmpty(auditDto.getCustomerCode())) {
			cr.setCustomerCode(auditDto.getCustomerCode());
		}
		if(!StringUtils.isEmpty(auditDto.getBillState())) {
			cr.setBillState(auditDto.getBillState());
		}
		return checkRecordMapper.queryCheckRecordList(cr);
	}

	
	//审批盘点记录
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public boolean auditCheckRecord(AuditCheckRecordDto auditDto) throws BusinessException {
		/*
		 * 修改 盘点记录表数据
		 * 审批通过：修改库位关系表的库存
		 * 审批不通过，不更新
		 */
		List<CheckRecordDto> checkRecordList = auditDto.getCheckRecordList();
		//获取当前登录用户
		UserSessionUo userSession = webUtil.getUserSession();
		Date nowDate = new Date();
		String name = userSession.getName();
		List<CheckRecord> checkRecords = new ArrayList<CheckRecord>();
		for (CheckRecordDto checkRecordDto : checkRecordList) {
			CheckRecord  cr = BeanCopyUtil.copy(checkRecordDto, CheckRecord.class);
			cr.setBillState(auditDto.getBillState());
			cr.setRemark(auditDto.getRemark());
			cr.setApprover(name);
			cr.setApproveTime(nowDate);
			cr.setUpdateTime(nowDate);
			cr.setUpdateUser(name);
			checkRecords.add(cr);
		}
		log.info("===>> update CheckRecord.params:{}",checkRecords);
		boolean updateBatchFlag = false;
		if(!CollectionUtils.isEmpty(checkRecords)) {
			//修改盘点记录
			updateBatchFlag = this.updateBatchById(checkRecords);
			log.info("===>> update CheckRecord isSuccess:{}",updateBatchFlag);
		}
		if(updateBatchFlag && BillState.checker_approved.getCode().equals(auditDto.getBillState())) {//审核通过
			//取盘点数量不为空的库位code
			List<String> storeCodeList = checkRecordList.stream()
					.filter(cr-> (!StringUtils.isEmpty(cr.getFirstCheckNums())||!StringUtils.isEmpty(cr.getSecondCheckNums())||!StringUtils.isEmpty(cr.getThirdCheckNums())))
					.map(CheckRecordDto::getStorehouseCode).collect(Collectors.toList());
			Wrapper<StorehouseInfo> siWrapper = new EntityWrapper<StorehouseInfo>();
			siWrapper.in("store_code", storeCodeList);
			siWrapper.eq("state", StateEnum.normal.getCode());
			List<StorehouseInfo> storeInfoList = storehouseInfoMapper.selectList(siWrapper);
			if(CollectionUtils.isEmpty(storeInfoList)) {
				log.info("===>> storehouseInfoMapper.selectList is empty！");
				throw new BusinessException(40009,"未查询到库位信息，取消盘点失败！");
			}
			Integer updateNum = 0;
			for (CheckRecordDto checkRecordDto : checkRecordList) {
				if(!StringUtils.isEmpty(checkRecordDto.getFirstCheckNums())
						||!StringUtils.isEmpty(checkRecordDto.getSecondCheckNums())
						||!StringUtils.isEmpty(checkRecordDto.getThirdCheckNums())) {
					StorehouseConfig sc = new StorehouseConfig();
					Wrapper<StorehouseConfig> scWrapper = new EntityWrapper<StorehouseConfig>();
					scWrapper.eq("state", StateEnum.normal.getCode());
					for (StorehouseInfo storehouseInfo : storeInfoList) {
						if(checkRecordDto.getStorehouseCode().equals(storehouseInfo.getStoreCode())) {
							scWrapper.eq("storehouse_id",storehouseInfo.getId());
							//1.2.3盘 正确结束， 两个一样结束， 三盘为准。审核结束，取消冻结。
							if(!StringUtils.isEmpty(checkRecordDto.getThirdCheckNums())){//终盘数量
								sc.setStoreNums(checkRecordDto.getThirdCheckNums());
							}else if (!StringUtils.isEmpty(checkRecordDto.getThirdCheckNums())){//复盘数量
								sc.setStoreNums(checkRecordDto.getThirdCheckNums());
							}else if (!StringUtils.isEmpty(checkRecordDto.getFirstCheckNums())){//初盘数量
								sc.setStoreNums(checkRecordDto.getThirdCheckNums());
							}
							sc.setUpdateTime(nowDate);
							sc.setUpdateUser(name);
							//修改库存
							updateNum = storehouseConfigMapper.update(sc,scWrapper);
							log.info("===>> update storehouseConfig change:{} rows",updateNum);
							break;
						}
					}
				}
			}
		}
		return true;
	}


	/**
	 * 冻结或解冻库位
	 * @param userSession
	 * @param storeIdList
	 * @param frozen
	 * @return
	 */
	private Integer updateStoreFrozenState(UserSessionUo userSession, List<Long> storeIdList, IsFrozenEnum frozen) {
		Wrapper<StorehouseInfo> siWrapper = new EntityWrapper<StorehouseInfo>();
		siWrapper.in("id", storeIdList);
		StorehouseInfo si = new StorehouseInfo();
		si.setIsFrozen(frozen.getCode());//冻结
		si.setUpdateTime(new Date());
		si.setUpdateUser(userSession.getName());
		Integer updateStoreFrozen = storehouseInfoMapper.update(si, siWrapper);
		log.info("===>> storehouceInfo.updateByIds,chanage:[{}] rows!", updateStoreFrozen);
		return updateStoreFrozen;
	}
}
