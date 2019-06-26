package com.magic.card.wms.user.service;

import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.user.model.dto.UserDTO;
import com.magic.card.wms.user.model.dto.UserLoginDTO;
import com.magic.card.wms.user.model.dto.UserRoleMenuQueryDTO;
import com.magic.card.wms.user.model.dto.UserUpdateDTO;
import com.magic.card.wms.user.model.po.User;

import java.util.List;

import javax.validation.Valid;

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
	
	/**
	 * 用户登录
	 * @param dto
	 */
	void login(UserLoginDTO dto) throws BusinessException;
	
	/**
	 * 修改用户信息
	 * @param dto
	 * @throws BusinessException
	 */
	void updateUserInfo(@Valid UserUpdateDTO dto) throws BusinessException;
	
	UserRoleMenuQueryDTO queryUserRoleMenuInfoList(Integer userKey)  throws BusinessException;
	
}
