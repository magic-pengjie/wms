package com.magic.card.wms.check.mapper;


import java.util.List;

import com.magic.card.wms.check.model.dto.CheckRecordInfoResponse;
import com.magic.card.wms.check.model.dto.CheckRecordQueryResponse;
import com.magic.card.wms.statistic.model.dto.OutStorehouseResponseDto;
import com.magic.card.wms.statistic.model.dto.StorehouseCountResponseDto;
import com.magic.card.wms.statistic.model.dto.StorehouseUsedResponseDto;
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


	public List<CheckRecordQueryResponse> queryCheckRecordList(@Param("cr") CheckRecord cr);


	public List<StorehouseCountResponseDto> queryStorehouseCountList(@Param("customerCode") String customerCode);


	public List<StorehouseUsedResponseDto> queryStorehouseUsedList(@Param("customerCode") String customerCode);

	public List<OutStorehouseResponseDto> queryOutStorehouseList(@Param("customerCode") String customerCode,@Param("startDate") String startDate,@Param("endDate") String endDate);

}
