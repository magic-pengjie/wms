package com.magic.card.wms.baseset.service.impl;

import com.magic.card.wms.baseset.model.dto.RulesConfigDTO;
import com.magic.card.wms.baseset.model.dto.RulesConfigReqDTO;
import com.magic.card.wms.baseset.model.po.RulesConfig;
import com.magic.card.wms.baseset.model.po.WarningAgentInfo;
import com.magic.card.wms.baseset.mapper.RulesConfigMapper;
import com.magic.card.wms.baseset.service.IRulesConfigService;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.utils.PoUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 规则配置 服务实现类
 * </p>
 *
 * @author pengjie
 * @since 2019-07-11
 */
@Service
public class RulesConfigServiceImpl extends ServiceImpl<RulesConfigMapper, RulesConfig> implements IRulesConfigService {

	@Override
	public void add(RulesConfigDTO dto) {
		RulesConfig rulesConfig = new RulesConfig();
		BeanUtils.copyProperties(dto, rulesConfig);
		PoUtil.add(rulesConfig, Constants.DEFAULT_USER);
		this.insert(rulesConfig);
	}

	@Override
	public Page<RulesConfig> select(RulesConfigReqDTO dto, PageInfo pageInfo) {
		Page<RulesConfig> page = new Page<>(pageInfo.getCurrent(), pageInfo.getPageSize());
		Wrapper<RulesConfig> w = new EntityWrapper<>();
		w.eq("state",Constants.STATE_1);
		if(ObjectUtils.isEmpty(dto.getRuleType())) {
			w.eq("rule_type", dto.getRuleType());
		}else if(ObjectUtils.isEmpty(dto.getRuleKey())) {
			w.eq("rule_key", dto.getRuleKey());
		}else if(ObjectUtils.isEmpty(dto.getRuleName())) {
			w.like("rule_name", dto.getRuleName());
		}
		List<String> orderParam = new ArrayList<>();
		orderParam.add("create_time");
		orderParam.add("update_time");
		w.orderDesc(orderParam);
		page = this.selectPage(page, w);
		return page;
		
	}

	@Override
	public void update(RulesConfigDTO dto) {
		RulesConfig rulesConfig = new RulesConfig();
		BeanUtils.copyProperties(dto, rulesConfig);
		PoUtil.update(rulesConfig, Constants.DEFAULT_USER);
		this.updateById(rulesConfig);
	}

	@Override
	public void detele(long id) {
		RulesConfig config = new RulesConfig();
		config.setState(Constants.STATE_0);
		config.setId(id);
		PoUtil.update(config, Constants.DEFAULT_USER);
		this.updateById(config);
		
	}

}
