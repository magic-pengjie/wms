package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.dto.WarningAgentQueryDTO;
import com.magic.card.wms.baseset.model.po.WarningAgentInfo;
import com.magic.card.wms.common.model.PageInfo;

/**
 * <p>
 * 预警代办表 服务类
 * </p>
 *
 * @author pengjie
 * @since 2019-07-10
 */
public interface IWarningAgentInfoService extends IService<WarningAgentInfo> {

	/**
	 * 预警代办列表
	 * @param dto 请求参数
	 * @param page 分页对象
	 * @return
	 */
	Page<WarningAgentInfo> getList(WarningAgentQueryDTO dto,PageInfo page);
	/**
	 * 关闭代办任务
	 * @param id
	 * @param fid
	 */
	void deal(String id,String fid);
	/**
	 * 作废代办任务
	 * @param id
	 */
	void delete(String id);
	/**
	 * 预警任务新增
	 * @param agentInfo
	 */
	void add(WarningAgentInfo agentInfo);
}
