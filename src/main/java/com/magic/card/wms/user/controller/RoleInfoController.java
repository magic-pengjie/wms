package com.magic.card.wms.user.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.user.model.dto.RoleAddDto;
import com.magic.card.wms.user.model.dto.RoleQueryDto;
import com.magic.card.wms.user.model.dto.RoleUpdateDto;
import com.magic.card.wms.user.model.po.RoleInfo;
import com.magic.card.wms.user.service.IRoleInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 *  用户权限维护Controller
 * @author zhouhao
 * @since 2019-06-18
 */
@Slf4j
@RestController
@RequestMapping("/roleInfo")
@Api("用户权限维护Controller")
public class RoleInfoController {

	
	@Autowired
	private IRoleInfoService roleInfoService;
	
	/***
	 * 获取角色列表
	 * @return
	 */
	@ApiOperation(value = "获取角色列表", notes = "获取角色列表")
	@RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
	public ResponseData queryRoleList(@RequestBody @Valid RoleQueryDto dto ,BindingResult bResult) {
		List<RoleInfo> roleList = null;
		try {
			 roleList = roleInfoService.getRoleList(dto);
		} catch (Exception e) {
			log.error("===查询角色失败:{}",e);
			return ResponseData.error(ResultEnum.query_role_failed.getCode(), ResultEnum.query_role_failed.getMsg());
		}
		return ResponseData.ok(roleList);
	}
	
	/***
	 * 新增角色信息
	 */
	@ApiOperation(value = "新增角色信息", notes = "新增角色信息")
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public ResponseData addRoleInfo(@RequestBody @Valid RoleAddDto dto ,BindingResult bResult) {
		
		try {
			roleInfoService.addRoleInfo(dto);
		} catch (Exception e) {
			log.error("===新增角色信息失败:{}",e);
			return ResponseData.error(ResultEnum.add_role_failed.getCode(), ResultEnum.add_role_failed.getMsg());
		}
		return ResponseData.ok();
	}
	
	
	/***
	 * 修改角色信息
	 */
	@ApiOperation(value = "修改角色信息", notes = "修改角色信息")
	@RequestMapping(value = "/updRole", method = RequestMethod.POST)
	public ResponseData updateRoleInfo(@RequestBody @Valid RoleUpdateDto dto ,BindingResult bResult) {
		
		try {
			roleInfoService.updateRoleInfo(dto);
		} catch (Exception e) {
			log.error("===新增角色信息失败:{}",e);
			return ResponseData.error(ResultEnum.add_role_failed.getCode(), ResultEnum.add_role_failed.getMsg());
		}
		return ResponseData.ok();
	}
	
	
}

