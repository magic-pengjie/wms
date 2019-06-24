package com.magic.card.wms.user.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.user.mapper.RoleInfoMapper;
import com.magic.card.wms.user.model.dto.RoleAddDto;
import com.magic.card.wms.user.model.dto.RoleQueryDto;
import com.magic.card.wms.user.model.dto.RoleUpdateDto;
import com.magic.card.wms.user.model.po.RoleInfo;
import com.magic.card.wms.user.service.IRoleInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 *  服务实现类
 * @author zhouhao
 * @since 2019-06-18
 */
@Slf4j
@Service
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo> implements IRoleInfoService {

	/**
	 * 查询角色列表
	 */
	@Override
	public List<RoleInfo> getRoleList(RoleQueryDto dto) {
		//查询所以正常状态的角色
		Wrapper<RoleInfo> wrapper= new EntityWrapper<RoleInfo>();
		wrapper.eq("state", 0);
		if(!StringUtils.isEmpty(dto.getRoleCode())) {
			wrapper.eq("role_code", dto.getRoleCode());
		}
		if(!StringUtils.isEmpty(dto.getRoleName())) {
			wrapper.like("role_name", dto.getRoleName());
		}
		if(!StringUtils.isEmpty(dto.getRoleDesc())) {
			wrapper.like("role_desc", dto.getRoleDesc());
		}
		if(!StringUtils.isEmpty(dto.getRoleType())) {
			wrapper.eq("role_type", dto.getRoleType());
		}
		if(!StringUtils.isEmpty(dto.getState())) {
			wrapper.eq("state", dto.getState());
		}
		wrapper.eq("display_flag", 1);
		return this.selectList(wrapper);
	}

	/**
	 * 新增角色信息
	 */
	@Override
	public void addRoleInfo(RoleAddDto dto) throws BusinessException {
		Wrapper<RoleInfo> wrapper = new EntityWrapper<RoleInfo>();
		wrapper.eq("role_code", dto.getRoleCode());
		wrapper.eq("state", 0);
		RoleInfo roleInfo = this.selectOne(wrapper);
		if(StringUtils.isEmpty(roleInfo)) {
			roleInfo = new RoleInfo();
			BeanUtils.copyProperties(dto, roleInfo);
			roleInfo.setCreateTime(new Date());
			roleInfo.setCreateUser("SYSTEM");
			roleInfo.setState(0);
			roleInfo.setDisplayFlag(1);
			log.info("===inserRoleInfo.params:{}",roleInfo);
			this.insert(roleInfo);
		}else {
			log.info("===角色信息已存在！req:{}", dto);
			throw new BusinessException(00, "角色信息已存在,请更换角色编码或名称！");
		}
	}
	

	/**
	 * 修改/删除角色信息
	 */
	@Override
	public void updateRoleInfo(RoleUpdateDto dto) throws BusinessException {
		Wrapper<RoleInfo> wrapper = new EntityWrapper<RoleInfo>();
		wrapper.eq("role_code", dto.getRoleCode());
		wrapper.eq("state", 0);
		wrapper.ne("id", dto.getRoleKey());
		RoleInfo roleInfo = this.selectOne(wrapper);
		//判断roleCode是否重复
		if(!StringUtils.isEmpty(roleInfo)) {
			throw new BusinessException(00, "角色Code已存在，请确认后，重新输入角色信息！");
		}
		roleInfo = new RoleInfo();
		BeanUtils.copyProperties(dto, roleInfo);
		roleInfo.setUpdateTime(new Date());
		roleInfo.setUpdateUser("SYSTEM");
		roleInfo.setId(dto.getRoleKey());
		this.updateById(roleInfo);
	}

}
