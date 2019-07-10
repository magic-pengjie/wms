package com.magic.card.wms.baseset.service.impl;

import com.magic.card.wms.baseset.model.po.WarningAgentInfo;
import com.magic.card.wms.baseset.mapper.WarningAgentInfoMapper;
import com.magic.card.wms.baseset.service.IWarningAgentInfoService;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.warehousing.model.dto.BillQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 预警代办表 服务实现类
 * </p>
 *
 * @author pengjie
 * @since 2019-07-10
 */
@Service
public class WarningAgentInfoServiceImpl extends ServiceImpl<WarningAgentInfoMapper, WarningAgentInfo> implements IWarningAgentInfoService {

	@Override
	public Page<WarningAgentInfo> getList(BillQueryDTO dto, PageInfo page) {
		// TODO Auto-generated method stub
		return null;
	}

}
