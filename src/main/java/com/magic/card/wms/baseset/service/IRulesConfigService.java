package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.dto.RulesConfigDTO;
import com.magic.card.wms.baseset.model.dto.RulesConfigReqDTO;
import com.magic.card.wms.baseset.model.po.RulesConfig;
import com.magic.card.wms.common.model.PageInfo;

/**
 * <p>
 * 规则配置服务类
 * </p>
 *
 * @author pengjie
 * @since 2019-07-11
 */
public interface IRulesConfigService extends IService<RulesConfig> {
	
	/**
	 * 新增规则配置
	 * @param dto
	 */
	void add(RulesConfigDTO dto);
	
	/**
	 * 列表查询
	 * @param dto
	 * @param pageInfo
	 */
	Page<RulesConfig> getList(RulesConfigReqDTO dto, PageInfo pageInfo);
	/**
	 * 修改
	 * @param dto
	 */
	void update(RulesConfigDTO dto);
	/**
	 * 删除
	 * @param id
	 */
	void detele(long id);
	
	
	/**
	 * 根据规则类型等精确查询对象
	 * @param dto
	 * @return
	 */
	RulesConfig selectObjectByType(RulesConfigReqDTO dto);

}
