package com.magic.card.wms.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.magic.card.wms.common.model.po.UserSessionUo;
import com.magic.card.wms.common.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.user.mapper.MenuInfoMapper;
import com.magic.card.wms.user.mapper.RoleMenuMappingMapper;
import com.magic.card.wms.user.model.dto.RoleMenuAddDto;
import com.magic.card.wms.user.model.dto.RoleMenuQueryDto;
import com.magic.card.wms.user.model.dto.RoleMenuQueryResponseDto;
import com.magic.card.wms.user.model.dto.RoleMenuUpdateDto;
import com.magic.card.wms.user.model.po.MenuInfo;
import com.magic.card.wms.user.model.po.RoleMenuMapping;
import com.magic.card.wms.user.service.IRoleMenuMappingService;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-18
 */
@Slf4j
@Service
public class RoleMenuMappingServiceImpl extends ServiceImpl<RoleMenuMappingMapper, RoleMenuMapping> implements IRoleMenuMappingService {

	@Resource
	private MenuInfoMapper menuInfoMapper;

	@Autowired
	private WebUtil webUtil;
	
	//新增角色菜单
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public void addRoleMenuMapping(RoleMenuAddDto dto) throws BusinessException {
		
		List<RoleMenuMapping> roleMenuMappingList = new ArrayList<RoleMenuMapping>();
		Wrapper<RoleMenuMapping> wrapper = new EntityWrapper<RoleMenuMapping>();
		this.selectCount(wrapper );
		UserSessionUo userSession = webUtil.getUserSession();
		try {
			Date date = new Date();
			for (Long menuKey : dto.getMenuKeyList()) {
				buildRoleMenuList(roleMenuMappingList, date, menuKey, dto.getRoleKey(),userSession.getName());
			}
			log.info("===新增角色菜单参数:{}",roleMenuMappingList);
			if(!CollectionUtils.isEmpty(roleMenuMappingList)) {
				boolean insertBatchFlag = this.insertBatch(roleMenuMappingList);
				log.info("===insert RoleMenuMapping isSuccess:{}",insertBatchFlag);
			}
		} catch (Exception e) {
			log.info("=== 新增角色菜单异常:{}", e);
			throw new BusinessException(000, "新增角色菜单信息异常，请稍后再试！");
		}
		
		
	}

	/**
	 * 创建角色菜单信息List
	 * @param roleMenuMappingList
	 * @param date
	 * @param menuKey
	 * @param roleKey
	 */
	private void buildRoleMenuList(List<RoleMenuMapping> roleMenuMappingList, Date date, Long menuKey, Long roleKey,String userName) {
		RoleMenuMapping roleMenu = new RoleMenuMapping();
		roleMenu.setRoleKey(roleKey);
		roleMenu.setMenuKey(menuKey);
		roleMenu.setState(StateEnum.normal.getCode());
		roleMenu.setCreateTime(date);
		roleMenu.setCreateUser(userName);
		roleMenuMappingList.add(roleMenu);
	}

	//修改/删除角色菜单
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public void updateRoleMenuMapping(RoleMenuUpdateDto dto) throws BusinessException {
		
		List<RoleMenuMapping> roleMenuMappingList = new ArrayList<RoleMenuMapping>();
		UserSessionUo userSession = webUtil.getUserSession();
		try {
			Date date = new Date();
			if(!CollectionUtils.isEmpty(dto.getAddMenuKeyList())) {
				for (Long menuKey : dto.getAddMenuKeyList()) {
					buildRoleMenuList(roleMenuMappingList, date, menuKey, dto.getRoleKey(),userSession.getName());
				}
				log.info("===update: 新增角色菜单参数:{}",roleMenuMappingList);
				if(!CollectionUtils.isEmpty(roleMenuMappingList)) {
					boolean insertBatchFlag = this.insertBatch(roleMenuMappingList);
					log.info("===update: insert RoleMenuMapping isSuccess:{}",insertBatchFlag);
				}
			}
			if(!CollectionUtils.isEmpty(dto.getDelMenuKeyList())) {
				for (Long delMenuKey : dto.getDelMenuKeyList()) {
					Wrapper<RoleMenuMapping> roleMenuWarWrapper = new EntityWrapper<RoleMenuMapping>();
					roleMenuWarWrapper.eq("role_key", dto.getRoleKey());
					roleMenuWarWrapper.eq("menu_key", delMenuKey);
					roleMenuWarWrapper.eq("state", StateEnum.normal.getCode());
					RoleMenuMapping entity = new RoleMenuMapping();
					entity.setState(StateEnum.delete.getCode());//逻辑删除,state=1
					entity.setUpdateTime(date);
					entity.setUpdateUser(userSession.getName());
					this.update(entity , roleMenuWarWrapper);
				}
			}
		} catch (Exception e) {
			log.info("=== 新增角色菜单异常:{}", e);
			throw new BusinessException(000, "新增角色菜单信息异常，请稍后再试！");
		}
	}

	//根据用户角色Key查询目录信息
	@Override
	public RoleMenuQueryResponseDto getRoleMenuInfo(RoleMenuQueryDto dto) throws BusinessException {
		RoleMenuQueryResponseDto rmResDto = new RoleMenuQueryResponseDto();
		Wrapper<RoleMenuMapping> rmWrapper = new EntityWrapper<RoleMenuMapping>();
		rmWrapper.eq("role_key", dto.getRoleKey());
		rmWrapper.eq("state", StateEnum.normal.getCode());
		List<RoleMenuMapping> rmList = this.selectList(rmWrapper);
		if(!CollectionUtils.isEmpty(rmList)) {
			List<Long> menuKeyList = rmList.stream().map(RoleMenuMapping::getMenuKey).collect(Collectors.toList());
			List<MenuInfo> menuInfoList = menuInfoMapper.selectBatchIds(menuKeyList);
			rmResDto.setRoleKey(dto.getRoleKey());
			rmResDto.setMenuInfoList(menuInfoList);
		}
		return rmResDto;
	}

}
