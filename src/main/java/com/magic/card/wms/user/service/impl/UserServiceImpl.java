package com.magic.card.wms.user.service.impl;

import com.magic.card.wms.user.model.dto.UserDTO;
import com.magic.card.wms.user.model.po.User;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.user.mapper.UserMapper;
import com.magic.card.wms.user.service.IUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  用户管理服务实现类
 * </p>
 *
 * @author pengjie
 * @since 2019-06-13
 */
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

}
