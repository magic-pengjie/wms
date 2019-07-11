package com.magic.card.wms.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.user.model.dto.RoleMenuAddDto;
import com.magic.card.wms.user.model.dto.RoleMenuQueryDto;
import com.magic.card.wms.user.model.dto.RoleMenuQueryResponseDto;
import com.magic.card.wms.user.model.dto.RoleMenuUpdateDto;
import com.magic.card.wms.user.model.po.RoleMenuMapping;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-18
 */
public interface IRoleMenuMappingService extends IService<RoleMenuMapping> {

	public void addRoleMenuMapping(RoleMenuAddDto dto) throws BusinessException;

	public void updateRoleMenuMapping(RoleMenuUpdateDto dto) throws BusinessException;

	/**
	 * 根据角色Key查询目录List
	 */
	public RoleMenuQueryResponseDto getRoleMenuInfo(RoleMenuQueryDto dto)throws BusinessException;
	
}
