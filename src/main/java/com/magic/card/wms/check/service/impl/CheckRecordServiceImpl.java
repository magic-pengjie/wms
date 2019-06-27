package com.magic.card.wms.check.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.check.mapper.CheckRecordMapper;
import com.magic.card.wms.check.model.po.CheckRecord;
import com.magic.card.wms.check.model.po.dto.CheckCountDto;
import com.magic.card.wms.check.service.ICheckRecordService;

/**
 * <p>
 * 盘点记录表 服务实现类
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-26
 */
@Service
public class CheckRecordServiceImpl extends ServiceImpl<CheckRecordMapper, CheckRecord> implements ICheckRecordService {

	
	@Override
	public CheckRecord countCheckRecord(CheckCountDto dto) {
		/**
		 * 
		 */
		return null;
	}

}
