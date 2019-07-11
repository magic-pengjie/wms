package com.magic.card.wms.baseset.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.mapper.WarningAgentInfoMapper;
import com.magic.card.wms.baseset.model.dto.WarningAgentQueryDTO;
import com.magic.card.wms.baseset.model.po.WarningAgentInfo;
import com.magic.card.wms.baseset.service.IWarningAgentInfoService;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.utils.PoUtil;

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
	public Page<WarningAgentInfo> getList(WarningAgentQueryDTO dto, PageInfo pageInfo) {
		Page<WarningAgentInfo> page = new Page<>(pageInfo.getCurrent(), pageInfo.getPageSize());
		Wrapper<WarningAgentInfo> w = new EntityWrapper<>();
		w.eq("state",Constants.STATE_1);
		if(ObjectUtils.isEmpty(dto.getAgentCode())) {
			w.eq("agent_code", dto.getAgentCode());
		}else if(ObjectUtils.isEmpty(dto.getModel())) {
			w.eq("model", dto.getModel());
		}else if(ObjectUtils.isEmpty(dto.getTypeCode())) {
			w.eq("type_code", dto.getTypeCode());
		}else if(ObjectUtils.isEmpty(dto.getAgentState())) {
			w.eq("agent_state", dto.getAgentState());
		}else if(ObjectUtils.isEmpty(dto.getAgentName())) {
			w.like("agent_name", dto.getAgentName());
		}
		List<String> orderParam = new ArrayList<>();
		orderParam.add("create_time");
		orderParam.add("update_time");
		w.orderDesc(orderParam);
		page = this.selectPage(page, w);
		return page;
	}

	@Override
	public void deal(String id, String fid) {
		if(ObjectUtils.isEmpty(id) && ObjectUtils.isEmpty(id)) {
			throw new IllegalArgumentException("参数为空");
		}
		Wrapper<WarningAgentInfo> w = new EntityWrapper<>();
		w.eq("state",Constants.STATE_1);
		if(ObjectUtils.isEmpty(id)) {
			w.eq("id", id);
		}else if(ObjectUtils.isEmpty(fid)) {
			w.eq("fid", fid);
		}
		WarningAgentInfo agent = new WarningAgentInfo();
		agent.setAgentState(Constants.STATE_1);
		agent.setUpdateUser(Constants.DEFAULT_USER);
		agent.setUpdateTime(new Date());
		this.update(agent, w);
	}

	@Override
	public void delete(String id) {
		this.deleteById(id);
	}

	@Override
	public void add(WarningAgentInfo agentInfo) {
		PoUtil.add(agentInfo, Constants.DEFAULT_USER);
		this.insert(agentInfo);
	}

}
