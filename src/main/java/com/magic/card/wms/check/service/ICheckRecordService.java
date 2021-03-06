package com.magic.card.wms.check.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.check.model.dto.AuditCheckRecordDto;
import com.magic.card.wms.check.model.dto.CheckCountDto;
import com.magic.card.wms.check.model.dto.CheckRecordCanellDto;
import com.magic.card.wms.check.model.dto.CheckRecordInfoDto;
import com.magic.card.wms.check.model.dto.CheckRecordStartDto;
import com.magic.card.wms.check.model.dto.QueryAuditCheckRecordDto;
import com.magic.card.wms.check.model.po.CheckRecord;
import com.magic.card.wms.common.exception.BusinessException;

/**
 * <p>
 * 	盘点记录表 服务类
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-26
 */
public interface ICheckRecordService extends IService<CheckRecord> {

	/**
	 * 根据盘点条件查询统计库存信息
	 * @param dto 盘点条件
	 */
	List<CheckRecordInfoDto> countCheckRecord(CheckCountDto dto) throws BusinessException;

	List<CheckRecord> checkRecordStart(CheckRecordStartDto dto)throws BusinessException;
	
	boolean updateRecordInfo(List<CheckRecord> checkRecordList)throws BusinessException;

	boolean cannelCheckRecord(CheckRecordCanellDto dto)throws BusinessException;

	List<CheckRecord> queryCheckRecord(QueryAuditCheckRecordDto auditDto)throws BusinessException;
	
	boolean auditCheckRecord(AuditCheckRecordDto auditDto)throws BusinessException;
}
