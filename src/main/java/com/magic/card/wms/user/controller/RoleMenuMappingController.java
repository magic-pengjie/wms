package com.magic.card.wms.user.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.user.model.dto.RoleMenuAddDto;
import com.magic.card.wms.user.model.dto.RoleMenuQueryDto;
import com.magic.card.wms.user.model.dto.RoleMenuQueryResponseDto;
import com.magic.card.wms.user.model.dto.RoleMenuUpdateDto;
import com.magic.card.wms.user.service.IRoleMenuMappingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 *  权限及菜单关系维护Controller
 * @author zhouhao
 * @since 2019-06-18
 */
@Slf4j
@RestController
@RequestMapping("/roleMenu")
@Api(value="权限及菜单关系维护Controller")
public class RoleMenuMappingController {
	
	@Autowired
	private IRoleMenuMappingService roleMenuMappingService;

	/***
	 * 新增角色菜单信息
	 */
	@ApiOperation(value = "新增角色菜单信息", notes = "新增角色菜单信息")
	@RequestMapping(value="/addRoleMenu", method = RequestMethod.POST)
	public ResponseData addRoleMenu(@RequestBody @Valid RoleMenuAddDto dto,BindingResult bindingResult) {
		try {
			roleMenuMappingService.addRoleMenuMapping(dto);
		}catch (BusinessException e) {
			log.error("===新增角色菜单异常:{}",e);
			return ResponseData.error(e.getErrCode(),e.getErrMsg());
		}catch (Exception e) {
			log.error("===新增角色菜单异常，请联系技术人员:{}",e);
			return ResponseData.error(0001, "新增角色菜单异常，请联系技术人员！");
		}
		return ResponseData.ok();
	}
	
	/***
	 * 修改角色信息
	 */
	@ApiOperation(value = "修改角色菜单信息", notes = "修改角色菜单信息")
	@RequestMapping(value="/updRoleMenu", method = RequestMethod.POST)
	public ResponseData updateRoleMenu(@RequestBody @Valid RoleMenuUpdateDto dto,BindingResult bindingResult) {
		try {
			roleMenuMappingService.updateRoleMenuMapping(dto);
		}catch (BusinessException e) {
			log.error("===修改角色菜单异常:{}",e);
			return ResponseData.error(e.getErrCode(),e.getErrMsg());
		}catch (Exception e) {
			log.error("===修改角色菜单异常，请联系技术人员:{}",e);
			return ResponseData.error(0001, "修改角色菜单异常，请联系技术人员！");
		}
		return ResponseData.ok();
	}
	
	/***
	 * 查询角色目录菜单
	 */
	@ApiOperation(value = "查询角色菜单", notes = "查询角色菜单信息")
	@RequestMapping(value="/getRoleMenuInfo", method = RequestMethod.POST)
	public ResponseData getRoleMenuInfo(@RequestBody @Valid RoleMenuQueryDto dto,BindingResult bindingResult) {
		RoleMenuQueryResponseDto roleMenuInfo = null;
		try {
			roleMenuInfo = roleMenuMappingService.getRoleMenuInfo(dto);
		}catch (BusinessException e) {
			log.error("===查询角色菜单失败:{}",e);
			return ResponseData.error(e.getErrCode(),e.getErrMsg());
		}
		catch (Exception e) {
			log.error("===查询角色菜单异常，请联系技术人员:{}",e);
			return ResponseData.error(0001, "查询角色菜单异常，请联系技术人员！");
		}
		return ResponseData.ok(roleMenuInfo);
	}
}

