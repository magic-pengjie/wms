package com.magic.card.wms.user.controller;


import java.util.List;

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
import com.magic.card.wms.user.model.dto.MenuQueryDto;
import com.magic.card.wms.user.model.dto.RoleMenuAddDto;
import com.magic.card.wms.user.model.po.MenuInfo;
import com.magic.card.wms.user.service.IMenuInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 *  菜单信息维护controller
 * @author zhouhao
 * @since 2019-06-18
 */
@Slf4j
@RestController
@RequestMapping("/menu")
@Api(value="菜单信息维护 Controller")
public class MenuInfoController {

	@Autowired
	private IMenuInfoService menuInfoService;
	
	/***
	 * 	获取菜单列表
	 * @return
	 */
	@ApiOperation(value = "获取菜单列表", notes = "获取菜单列表")
	@RequestMapping(value = "/getMenuList", method = RequestMethod.POST)
	public ResponseData queryMenuList(@RequestBody @Valid MenuQueryDto dto, BindingResult bindingResult) {
		List<MenuInfo> menuList = null;
		try {
			menuList = menuInfoService.queryMenuList(dto);
		} catch (BusinessException e) {
			log.error("===查询菜单失败：{}", e);
			return ResponseData.error(e.getErrCode(), e.getErrMsg());
		} catch (Exception e) {
			log.error("===查询菜单异常：{}", e);
			return ResponseData.error("查询菜单目录异常！请稍后再试！");
		}
		return ResponseData.ok(menuList);
	}
	
}

