package com.magic.card.wms.check.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.check.model.po.CheckRecord;
import com.magic.card.wms.check.model.po.dto.CheckCountDto;
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

	CheckRecord countCheckRecord(CheckCountDto dto) throws BusinessException;

}
