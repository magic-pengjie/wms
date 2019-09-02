package com.magic.card.wms.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.magic.card.wms.baseset.mapper.CustomerBaseInfoMapper;
import com.magic.card.wms.baseset.model.po.CustomerBaseInfo;
import com.magic.card.wms.common.utils.BeanCopyUtil;
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
import com.magic.card.wms.common.model.enums.SessionKeyConstants;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.model.po.UserSessionUo;
import com.magic.card.wms.common.service.RedisService;
import com.magic.card.wms.common.utils.WebUtil;
import com.magic.card.wms.user.mapper.MenuInfoMapper;
import com.magic.card.wms.user.mapper.RoleInfoMapper;
import com.magic.card.wms.user.mapper.UserMapper;
import com.magic.card.wms.user.mapper.UserRoleMappingMapper;
import com.magic.card.wms.user.model.dto.UserDTO;
import com.magic.card.wms.user.model.dto.UserLoginDTO;
import com.magic.card.wms.user.model.dto.UserResponseDTO;
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
 * @author Zhouhao
 * @since 2019-06-13
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	@Resource
	private UserRoleMappingMapper userRoleMappingMapper;

	@Resource
	private RoleInfoMapper roleInfoMapper;

	@Resource
	private MenuInfoMapper menuInfoMapper;

	@Resource
	private CustomerBaseInfoMapper customerBaseInfoMapper;

	@Resource
	private RedisService redisService;

	@Resource
	private HttpServletRequest httpServletRequest;

	@Resource
	private WebUtil webUtil;
	
	@Override
	public List<User> getUserList(String userNo,String name) {
		Wrapper<User> w = new EntityWrapper<>();
		if(!StringUtils.isEmpty(userNo)){
			w.like("user_no",userNo);
		}
		if(!StringUtils.isEmpty(name)){
			w.like("name",name);
		}
		w.eq("state",StateEnum.normal.getCode());
		return this.selectList(w);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public void addUser(UserDTO dto) throws BusinessException {
		Wrapper<User> w = new EntityWrapper<>();
		w.eq("user_no", dto.getUserNo());
//		w.eq("customer_id", dto.getCustomerId());
		w.eq("state", StateEnum.normal.getCode());
		User user = this.selectOne(w);
		if(StringUtils.isEmpty(user)) {
			//获取用户session
			UserSessionUo userSession = webUtil.getUserSession();
			user = new User();
			BeanUtils.copyProperties(dto, user);
			Date nowDate = new Date();
			if(StringUtils.isEmpty(dto.getPassword())) {
				user.setPassword("wms888888");
			}
			user.setState(StateEnum.normal.getCode());
			user.setCreateTime(nowDate);
			user.setCreateUser(userSession.getName());
			//新增用户
			boolean insertFlag = this.insert(user);
			log.info("===inserUser.params:{},isSuccess:{}", user, insertFlag);
			//新增用户角色信息
			if(insertFlag) {
				for (Long roleId : dto.getRoleKey()) {
					UserRoleMapping entity = new UserRoleMapping();
					entity.setUserKey(user.getId());
					entity.setCreateTime(nowDate);
					entity.setRoleKey(roleId);
					entity.setCreateUser(userSession.getName());
					entity.setState(StateEnum.normal.getCode());
					Integer insertMappingFlag = userRoleMappingMapper.insert(entity);
					log.info("===insertUserRoleMapping.params:{},change {} rows", entity,insertMappingFlag);
				}
			}
		}else {
			throw new BusinessException(ResultEnum.add_user_exist.getCode(), ResultEnum.add_user_exist.getMsg());
		}
	}

	//修改/删除
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public void updateUserInfo(UserUpdateDTO dto) throws BusinessException {
		log.info("=== updateUserInfo params:{}", dto);
		//获取用户session
		UserSessionUo userSession = webUtil.getUserSession();
		User user = this.selectById(dto.getUserKey());
		if(!StringUtils.isEmpty(user) && StateEnum.delete.getCode() != user.getState()) {
			user = new User(); 
			BeanUtils.copyProperties(dto, user);
			user.setId(dto.getUserKey());
			user.setUpdateTime(new Date());
			user.setUpdateUser(userSession.getName());
			//根据用户主键ID修改用户信息
			boolean updateUserFlag = this.updateById(user);
			if(updateUserFlag) {
				UserRoleMapping entity = new UserRoleMapping();
				entity.setState(StateEnum.delete.getCode());
				entity.setUpdateTime(new Date());
				entity.setUpdateUser(userSession.getName());
				Wrapper<UserRoleMapping> userRoleWrapper = new EntityWrapper<UserRoleMapping>();
				userRoleWrapper.eq("user_key", dto.getUserKey());
				//删除原来的用户角色
				Integer update = userRoleMappingMapper.update(entity,userRoleWrapper);
				log.info("===>>deleted  UserRole {} rows ",update);
				List<Long> roleKeyList = dto.getRoleKeyList();
				if(StateEnum.delete.getCode() != dto.getState() && !CollectionUtils.isEmpty(roleKeyList)) {
					for (Long roleId : roleKeyList) {
						UserRoleMapping userRole = new UserRoleMapping();
						userRole.setUserKey(user.getId());
						userRole.setState(StateEnum.normal.getCode());
						userRole.setRoleKey(roleId);
						userRole.setUpdateTime(new Date());
						userRole.setUpdateUser(user.getName());
//						Wrapper<UserRoleMapping> userRoleMapping = new EntityWrapper<UserRoleMapping>();
//						userRoleMapping.eq("user_key", dto.getUserKey());
//						userRoleMapping.eq("role_key", roleId);
						//修改用户角色信息
						Integer updMappingFlag = userRoleMappingMapper.insert(userRole);
						log.info("===insertUserRoleMapping.params:{},change {} rows", userRole,updMappingFlag);
					}
				}
			}
		}else {
			log.info("===>> update 用户不存在！userNo:{}", dto.getUserNo());
			throw new BusinessException(ResultEnum.user_name_not_exist.getCode(), ResultEnum.user_name_not_exist.getMsg());
		}
		
	}
	

	//登录
	@Override
	public UserResponseDTO login(UserLoginDTO dto) throws BusinessException {
		UserResponseDTO userRes = new UserResponseDTO();
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
			//将用户信息放入session，前端放入cookie字段：Wms-Token,
			HttpSession session = setUserSessionUo(user);
			//返回前端Dto
			BeanUtils.copyProperties(user, userRes);
			userRes.setToken(session.getId());
			userRes.setId(user.getId());
			return userRes;
		}else {
			log.info("===>> login 用户不存在！userNo:{}", dto.getUserNo());
			throw new BusinessException(ResultEnum.user_name_not_exist.getCode(), ResultEnum.user_name_not_exist.getMsg());
		}
	}

	//查询用户角色及菜单信息
	@Override
	public UserRoleMenuQueryDTO queryUserRoleMenuInfoList(Integer userKey) throws BusinessException {

		UserSessionUo userSession = webUtil.getUserSession();
		log.info("===>> getUserSession:{}", userSession);
		UserRoleMenuQueryDTO userRoleMenuList = new UserRoleMenuQueryDTO();
		//查询用户信息
		User userInfo = this.selectById(userKey);
		if(StringUtils.isEmpty(userInfo)) {
			log.info("===>> login 用户不存在！userNo:{}", userKey);
			throw new BusinessException(ResultEnum.user_name_not_exist.getCode(), ResultEnum.user_name_not_exist.getMsg());
		}
//		CustomerBaseInfo customerBaseInfo = customerBaseInfoMapper.selectById(userInfo.getCustomerId());
//		BeanUtils.copyProperties(customerBaseInfo,userRoleMenuList);
		BeanUtils.copyProperties(userInfo, userRoleMenuList);
		userRoleMenuList.setUserKey(userInfo.getId());

		//根据userKey查询用户角色
		List<RoleInfo> roleList = roleInfoMapper.queryRoleByUserKey(userKey);
		if(!CollectionUtils.isEmpty(roleList)) {

			userRoleMenuList.setRoleList(roleList);
			//查询角色菜单信息
			List<Long> roleKeyList = roleList.stream().map(RoleInfo::getId).collect(Collectors.toList());
			List<MenuInfo> menuList = menuInfoMapper.queryMenuByRoleKey(roleKeyList);
			if(CollectionUtils.isEmpty(menuList)) {
				log.info("===用户角色未配置菜单信息，请联系管理员配置角色菜单！");
				throw new BusinessException(00, "用户角色未配置菜单信息，请联系管理员配置角色菜单！");
			}
			userRoleMenuList.setMenuList(menuList);
			//将用户角色及菜单 放入redis
			redisService.set(SessionKeyConstants.USER_ROLE_MENU_KEY+userKey, userRoleMenuList);
			log.info("===>> userRoleMenuSession.key:{},UserInfo:{} ",SessionKeyConstants.USER_ROLE_MENU_KEY+userKey, userRoleMenuList);
//			log.info("===用户角色信息为空，请先添加用户角色！");
//			throw new BusinessException(00, "用户未分配角色，请联系管理员先配置用户角色！");
		}
		return userRoleMenuList;
	}

	/**
	 * 设置用户登录Session及redis
	 * @param user
	 * @return
	 */
	private HttpSession setUserSessionUo(User user) {
		UserSessionUo userSession = new UserSessionUo();
		HttpSession session = httpServletRequest.getSession();
		CustomerBaseInfo baseInfo = customerBaseInfoMapper.selectById(user.getId());
		if (null != baseInfo) {
			userSession = BeanCopyUtil.copy(baseInfo,UserSessionUo.class);
			userSession.setId(user.getId());
			userSession.setUserNo(user.getUserNo());
			userSession.setName(user.getName());
			userSession.setCustomerId(user.getCustomerId());
		}
		session.setAttribute(SessionKeyConstants.USER_INFO, userSession);
		log.info("===>> setUserSession:{} ",userSession);
		//将用户信息放入redis
//		redisService.set(SessionKeyConstants.USER_SESSION_KEY+user.getId(), user);
//		log.info("===>> userSession.key:{},UserInfo:{} ",SessionKeyConstants.USER_SESSION_KEY+user.getId(), user);
		return session;
	}
}
