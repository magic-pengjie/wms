package com.magic.card.wms.user.service;

import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.user.model.dto.UserDTO;
import com.magic.card.wms.user.model.po.User;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  用户管理服务类
 * </p>
 *
 * @author pengjie
 * @since 2019-06-13
 */
public interface IUserService extends IService<User> {
	
	/***
	 * 查询所有用户
	 * @return
	 */
	List<User> getUserList();
	/**
	 * 新增用户
	 * @param dto
	 */
	void addUser(UserDTO dto) throws BusinessException;
}
