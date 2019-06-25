package com.magic.card.wms.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.user.mapper.MenuInfoMapper;
import com.magic.card.wms.user.mapper.RoleInfoMapper;
import com.magic.card.wms.user.mapper.UserMapper;
import com.magic.card.wms.user.mapper.UserRoleMappingMapper;
import com.magic.card.wms.user.model.dto.UserDTO;
import com.magic.card.wms.user.model.dto.UserLoginDTO;
import com.magic.card.wms.user.model.dto.UserRoleMenuQueryDTO;
import com.magic.card.wms.user.model.dto.UserUpdateDTO;
import com.magic.card.wms.user.model.po.MenuInfo;
import com.magic.card.wms.user.model.po.RoleInfo;
import com.magic.card.wms.user.model.po.User;
import com.magic.card.wms.user.model.po.UserRoleMapping;
import com.magic.card.wms.user.service.IUserService;

import lombok.extern.slf4j.Slf4j;

/**
 *  用户管理服务实现类
 * @author pengjie
 * @since 2019-06-13
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	@Autowired
	private UserRoleMappingMapper userRoleMappingMapper;
	
	@Autowired
	private RoleInfoMapper RoleInfoMapper;
	
	@Autowired
	private MenuInfoMapper menuInfoMapper;
	
	@Override
	public List<User> getUserList() {
		Wrapper<User> w = new EntityWrapper<>();
		return this.selectList(w);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public void addUser(UserDTO dto) throws BusinessException {
		Wrapper<User> w = new EntityWrapper<>();
		w.eq("user_no", dto.getUserNo());
		User user = this.selectOne(w);
		if(StringUtils.isEmpty(user)) {
			user = new User(); 
			if(StringUtils.isEmpty(dto.getPassword())) {
				user.setPassword("wms888888");
			}
			user.setState(StateEnum.normal.getCode());
			BeanUtils.copyProperties(dto, user);
			//新增用户
			boolean insertFlag = this.insert(user);
			log.info("===inserUser.params:{},isSuccess:{}", user, insertFlag);
			if(insertFlag) {
				UserRoleMapping entity = new UserRoleMapping();
				entity.setUserKey(user.getId().longValue());
				entity.setRoleKey(dto.getRoleKey());
				entity.setCreateTime(new Date());
				entity.setCreateUser(user.getName());
				entity.setState(StateEnum.normal.getCode());
				//新增用户角色信息
				Integer insertMappingFlag = userRoleMappingMapper.insert(entity);
				log.info("===insertUserRoleMapping.params:{},change {} rows", entity,insertMappingFlag);
			}
		}else {
			throw new BusinessException(ResultEnum.add_user_exist.getCode(), ResultEnum.add_user_exist.getMsg());
		}
	}

	//修改/删除
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public void updateUserInfo(@Valid UserUpdateDTO dto) throws BusinessException {
		log.info("=== updateUserInfo params:{}", dto);
		User user = this.selectById(dto.getUserKey());
		if(!StringUtils.isEmpty(user)) {
			user = new User(); 
			BeanUtils.copyProperties(dto, user);
			user.setId(dto.getUserKey().intValue());
			user.setUpdateTime(new Date());
			user.setUpdateUser(user.getName()==null ?"SYSTEM" : user.getName());
			//根据用户主键ID修改用户信息
			boolean updateUserFlag = this.updateById(user);
			if(updateUserFlag) {
				UserRoleMapping entity = new UserRoleMapping();
				entity.setUserKey(user.getId().longValue());
				if(StringUtils.isEmpty(dto.getState())) {
					entity.setState(dto.getState());
				}
				entity.setRoleKey(dto.getRoleKey());
				entity.setUpdateTime(new Date());
				entity.setUpdateUser(user.getName());
				Wrapper<UserRoleMapping> userRoleMapping = new EntityWrapper<UserRoleMapping>();
				userRoleMapping.eq("user_key", dto.getUserKey());
				userRoleMapping.eq("role_key", dto.getRoleKey());
				//修改用户角色信息
				Integer updMappingFlag = userRoleMappingMapper.update(entity, userRoleMapping);
				log.info("===insertUserRoleMapping.params:{},change {} rows", entity,updMappingFlag);
			}
		}else {
			log.info("===>> update 用户不存在！userNo:{}", dto.getUserNo());
			throw new BusinessException(ResultEnum.user_name_not_exist.getCode(), ResultEnum.user_name_not_exist.getMsg());
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
			if(!dto.getPassword().equals(user.getPassword())) {
				log.info("===>> login 用户名密码错误！UserDto:{}", dto);
				throw new BusinessException(ResultEnum.user_pwd_error.getCode(), ResultEnum.user_pwd_error.getMsg());
			}
		}else {
			log.info("===>> login 用户不存在！userNo:{}", dto.getUserNo());
			throw new BusinessException(ResultEnum.user_name_not_exist.getCode(), ResultEnum.user_name_not_exist.getMsg());
		}
	}

	//查询用户角色及菜单信息
	@Override
	public UserRoleMenuQueryDTO queryUserRoleMenuInfoList(Integer userKey) throws BusinessException {
		UserRoleMenuQueryDTO userRoleMenuList = new UserRoleMenuQueryDTO();
		//查询用户信息
		User userInfo = this.selectById(userKey);
		if(StringUtils.isEmpty(userInfo)) {
			log.info("===>> login 用户不存在！userNo:{}", userKey);
			throw new BusinessException(ResultEnum.user_name_not_exist.getCode(), ResultEnum.user_name_not_exist.getMsg());
		}
		BeanUtils.copyProperties(userInfo, userRoleMenuList);
		//根据userKey查询用户角色
		List<RoleInfo> roleList = RoleInfoMapper.queryRoleByUserKey(userKey);
		if(CollectionUtils.isEmpty(roleList)) {
			log.info("===用户角色信息为空，请先添加用户角色！");
		}
		userRoleMenuList.setRoleList(roleList);
		//查询角色菜单信息
		List<Integer> roleKeyList = roleList.stream().map(RoleInfo::getId).collect(Collectors.toList());
		List<MenuInfo> menuList = menuInfoMapper.queryMenuByRoleKey(roleKeyList);
		if(CollectionUtils.isEmpty(menuList)) {
			log.info("===用户角色未配置菜单信息，请联系管理员配置角色菜单！");
			throw new BusinessException(00, "用户角色未配置菜单信息，请联系管理员配置角色菜单！");
		}
		userRoleMenuList.setMenuList(menuList);
		return userRoleMenuList;
	}

}
