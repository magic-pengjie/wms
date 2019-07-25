package com.magic.card.wms.baseset.service.impl;

import com.magic.card.wms.baseset.model.po.LogisticsTrackingInfo;
import com.magic.card.wms.baseset.mapper.LogisticsTrackingInfoMapper;
import com.magic.card.wms.baseset.service.ILogisticsTrackingInfoService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 物流跟踪信息表 服务实现类
 * </p>
 *
 * @author pengjie
 * @since 2019-07-13
 */
@Service
public class LogisticsTrackingInfoServiceImpl extends ServiceImpl<LogisticsTrackingInfoMapper, LogisticsTrackingInfo> implements ILogisticsTrackingInfoService {

	@Override
	public Page<LogisticsTrackingInfo> getList() {
		// TODO Auto-generated method stub
		return null;
	}

}
