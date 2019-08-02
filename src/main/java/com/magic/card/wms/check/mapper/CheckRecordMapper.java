package com.magic.card.wms.check.mapper;


import java.util.List;

import com.magic.card.wms.check.model.dto.CheckRecordInfoResponse;
import com.magic.card.wms.check.model.dto.CheckRecordQueryResponse;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.magic.card.wms.check.model.po.CheckRecord;

/**
 * <p>
 * 盘点记录表 Mapper 接口
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-26
 */
public interface CheckRecordMapper extends BaseMapper<CheckRecord> {

	
	public List<CheckRecordQueryResponse> queryCheckRecordList(@Param("cr")CheckRecord cr);
	
}
