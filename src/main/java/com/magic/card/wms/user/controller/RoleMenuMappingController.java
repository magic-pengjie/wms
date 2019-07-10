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
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.user.model.dto.RoleMenuAddDto;
import com.magic.card.wms.user.model.dto.RoleMenuUpdateDto;
import com.magic.card.wms.user.service.IMenuInfoService;
import com.magic.card.wms.user.service.IRoleMenuMappingService;
import com.magic.card.wms.user.service.impl.RoleMenuMappingServiceImpl;

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
	private IRoleMenuMappingService RoleMenuMappingService;

	/***
	 * 新增角色菜单信息
	 */
	@ApiOperation(value = "新增角色菜单信息", notes = "新增角色菜单信息")
	@RequestMapping(value="/addRoleMenu", method = RequestMethod.POST)
	public ResponseData addRoleMenu(@RequestBody @Valid RoleMenuAddDto dto,BindingResult bindingResult) {
		try {
			RoleMenuMappingService.addRoleMenuMapping(dto);
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
			RoleMenuMappingService.updateRoleMenuMapping(dto);
		}catch (BusinessException e) {
			log.error("===新增角色菜单异常:{}",e);
			return ResponseData.error(e.getErrCode(),e.getErrMsg());
		}catch (Exception e) {
			log.error("===新增角色菜单异常，请联系技术人员:{}",e);
			return ResponseData.error(0001, "新增角色菜单异常，请联系技术人员！");
		}
		return ResponseData.ok();
	}
	
}

