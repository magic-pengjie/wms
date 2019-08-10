package com.magic.card.wms.baseset.mapper;

import com.magic.card.wms.baseset.model.po.LogisticsTrackingInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 物流跟踪信息表 Mapper 接口
 * </p>
 *
 * @author pengjie
 * @since 2019-07-13
 */
public interface LogisticsTrackingInfoMapper extends BaseMapper<LogisticsTrackingInfo> {

	/**
	 * 查询本地物流信息
	 * @param orderNo 订单
	 * @param mailNo 包裹单
	 * @return
	 */
	List<LogisticsTrackingInfo> getTrackingInfoByMailOrOrderNo(@Param("orderNo")String orderNo,@Param("mailNo")String mailNo);
}
