package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.po.LogisticsTrackingInfo;

/**
 * <p>
 * 物流跟踪信息表 服务类
 * </p>
 *
 * @author pengjie
 * @since 2019-07-13
 */
public interface ILogisticsTrackingInfoService extends IService<LogisticsTrackingInfo> {

	/**
	 * 获取列表信息
	 * @return LogisticsTrackingInfo
	 */
	Page<LogisticsTrackingInfo> getList();
}
