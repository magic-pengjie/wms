package com.magic.card.wms.user.service.impl;

import com.magic.card.wms.user.model.dto.UserDTO;
import com.magic.card.wms.user.model.dto.UserLoginDTO;
import com.magic.card.wms.user.model.dto.UserUpdateDTO;
import com.magic.card.wms.user.model.po.User;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.user.mapper.UserMapper;
import com.magic.card.wms.user.service.IUserService;

import lombok.extern.slf4j.Slf4j;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *  用户管理服务实现类
 * @author pengjie
 * @since 2019-06-13
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	@Override
	public List<User> getUserList() {
		Wrapper<User> w = new EntityWrapper<>();
		return this.selectList(w);
	}

	@Override
	public void addUser(UserDTO dto) throws BusinessException {
		Wrapper<User> w = new EntityWrapper<>();
		w.eq("user_no", dto.getUserNo());
		User user = this.selectOne(w);
		if(StringUtils.isEmpty(user)) {
			user = new User(); 
			if(StringUtils.isEmpty(dto.getPassword())) {
				dto.setPassword("wms888888");
			}
			BeanUtils.copyProperties(dto, user);
			this.insert(user);
		}else {
			throw new BusinessException(ResultEnum.add_user_exist.getCode(), ResultEnum.add_user_exist.getMsg());
		}
		
	}

	//登录
	@Override
	public void login(UserLoginDTO dto) throws BusinessException {
		Wrapper<User> wrapper = new EntityWrapper<User>();
		wrapper.eq("user_no", dto.getUserNo());
		User user = this.selectOne(wrapper);
		if(!StringUtils.isEmpty(user)) {
			if(StateEnum.delete.getCode() == user.getState()) {
				log.info("===>> login 账户状态错误！UserDto:{}", dto);
				throw new BusinessException(ResultEnum.user_state_error.getCode(), ResultEnum.user_state_error.getMsg());
			}
			if(dto.getPassword().equals(user.getPassword())) {
				log.info("===>> login 用户名密码错误！UserDto:{}", dto);
				throw new BusinessException(ResultEnum.user_pwd_error.getCode(), ResultEnum.user_pwd_error.getMsg());
			}
		}else {
			log.info("===>> login 用户不存在！userNo:{}", dto.getUserNo());
			throw new BusinessException(ResultEnum.user_name_not_exist.getCode(), ResultEnum.user_name_not_exist.getMsg());
		}
	}

	//修改/删除
	@Override
	public void updateUserInfo(@Valid UserUpdateDTO dto) throws BusinessException {
		log.info("=== updateUserInfo params:{}", dto);
		Wrapper<User> w = new EntityWrapper<>();
		w.eq("user_no", dto.getUserNo());
		User user = this.selectOne(w);
		if(!StringUtils.isEmpty(user)) {
			user = new User(); 
			BeanUtils.copyProperties(dto, user);
			user.setUpdateTime(new Date());
			user.setUpdateUser(user.getName());
			this.updateById(user);
		}else {
			log.info("===>> update 用户不存在！userNo:{}", dto.getUserNo());
			throw new BusinessException(ResultEnum.user_name_not_exist.getCode(), ResultEnum.user_name_not_exist.getMsg());
		}
		
	}

}
