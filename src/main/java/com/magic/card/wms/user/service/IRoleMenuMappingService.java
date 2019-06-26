package com.magic.card.wms.user.service;

import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.user.model.dto.RoleMenuAddDto;
import com.magic.card.wms.user.model.dto.RoleMenuUpdateDto;
import com.magic.card.wms.user.model.po.RoleMenuMapping;

import javax.validation.Valid;

import com.baomidou.mybatisplus.service.IService;

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

	public void updateRoleMenuMapping(@Valid RoleMenuUpdateDto dto) throws BusinessException;

}
