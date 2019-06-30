package com.magic.card.wms.user.service.impl;

import com.magic.card.wms.user.model.dto.RoleMenuAddDto;
import com.magic.card.wms.user.model.dto.RoleMenuUpdateDto;
import com.magic.card.wms.user.model.po.RoleMenuMapping;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.user.mapper.RoleMenuMappingMapper;
import com.magic.card.wms.user.service.IRoleMenuMappingService;

import lombok.extern.slf4j.Slf4j;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

	//新增角色菜单
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public void addRoleMenuMapping(RoleMenuAddDto dto) throws BusinessException {
		
		List<RoleMenuMapping> roleMenuMappingList = new ArrayList<RoleMenuMapping>();
		Wrapper<RoleMenuMapping> wrapper = new EntityWrapper<RoleMenuMapping>();
		this.selectCount(wrapper );
		try {
			Date date = new Date();
			for (Long menuKey : dto.getMenuKeyList()) {
				RoleMenuMapping roleMenu = new RoleMenuMapping();
				roleMenu.setRoleKey(dto.getRoleKey());
				roleMenu.setMenuKey(menuKey);
				roleMenu.setState(StateEnum.normal.getCode());
				roleMenu.setCreateTime(date);
				roleMenu.setCreateUser("SYSTEM");
				roleMenuMappingList.add(roleMenu);
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

	//修改/删除角色菜单
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
	public void updateRoleMenuMapping(@Valid RoleMenuUpdateDto dto) throws BusinessException {
		
		List<RoleMenuMapping> roleMenuMappingList = new ArrayList<RoleMenuMapping>();
		try {
			Date date = new Date();
			if(!CollectionUtils.isEmpty(dto.getAddMenuKeyList())) {
				for (Long menuKey : dto.getAddMenuKeyList()) {
					RoleMenuMapping roleMenu = new RoleMenuMapping();
					roleMenu.setRoleKey(dto.getRoleKey());
					roleMenu.setMenuKey(menuKey);
					roleMenu.setState(StateEnum.normal.getCode());
					roleMenu.setCreateTime(date);
					roleMenu.setCreateUser("SYSTEM");
					roleMenuMappingList.add(roleMenu);
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
					entity.setUpdateUser("SYSTEM");
					this.update(entity , roleMenuWarWrapper);
				}
			}
		} catch (Exception e) {
			log.info("=== 新增角色菜单异常:{}", e);
			throw new BusinessException(000, "新增角色菜单信息异常，请稍后再试！");
		}
		
	}

}
