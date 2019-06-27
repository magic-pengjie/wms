package com.magic.card.wms.user.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.user.model.dto.RoleAddDto;
import com.magic.card.wms.user.model.dto.RoleQueryDto;
import com.magic.card.wms.user.model.dto.RoleUpdateDto;
import com.magic.card.wms.user.model.po.RoleInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-18
 */
public interface IRoleInfoService extends IService<RoleInfo> {

	public List<RoleInfo> getRoleList(RoleQueryDto dto);
	
	public void addRoleInfo(RoleAddDto dto) throws BusinessException;

	public void updateRoleInfo(RoleUpdateDto dto) throws BusinessException;
	
}
