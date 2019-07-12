package com.magic.card.wms.baseset.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.magic.card.wms.baseset.model.po.StorehouseConfig;
import com.magic.card.wms.check.model.dto.CheckRecordInfoDto;
import com.magic.card.wms.check.model.dto.QueryCheckRecordDto;

/**
 * com.magic.card.wms.baseset.mapper
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/19/019 17:42
 * @since : 1.0.0
 */
public interface StorehouseConfigMapper extends BaseMapper<StorehouseConfig> {
	
	/**
	 * 按商家或商品查询库存
	 */
	List<CheckRecordInfoDto> queryCommoidtyStoreList(@Param("cr") QueryCheckRecordDto params);

	/**
	 * 加载库位配置信息
	 * @param wrapper
	 * @return
	 */
	List<Map> storehouseConfig(@Param("ew")Wrapper wrapper);
}
