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
import com.magic.card.wms.user.model.dto.UserDTO;
import com.magic.card.wms.user.model.dto.UserUpdateDTO;
import com.magic.card.wms.user.service.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户 维护Controlller
 *
 * @author pengjie
 * @since 2019-06-13
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api("用户 维护Controlller")
public class UserController {

	@Autowired
	private IUserService userService;
	
	
	/**
	 *       登录接口
	 * @param dto
	 * @return
	 */
	public ResponseData userLogin(@RequestBody @Valid UserDTO dto, BindingResult bindingResult) {
		
		return null;
	}

	/***
	 * 获取用户信息
	 * @return
	 */
	@ApiOperation(value = "获取用户列表", notes = "获取用户列表")
	@RequestMapping(value = "/getUserList", method = RequestMethod.GET)
	public ResponseData getUserList() {
		List result = null;
		try {
			result = userService.getUserList();
		} catch (Exception e) {
			log.error("查询用户失败:{}",e);
			return ResponseData.error(ResultEnum.query_user_failed.getMsg());
		}
		return ResponseData.ok(result);
	}

	
	/***
	 * 新增用户信息
	 * @return
	 */
	@ApiOperation(value = "新增用户", notes = "新增用户")
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ResponseData addUser(@RequestBody @Valid UserDTO dto, BindingResult bindingResult) {
		try {
			userService.addUser(dto);
		} catch (BusinessException e) {
			log.error("新增用户失败:{}",e);
			return ResponseData.error(e.getErrCode(),e.getErrMsg());
		} catch (Exception e) {
			log.error("新增用户失败:{}",e);
			return ResponseData.error(ResultEnum.add_user_failed.getMsg());
		}
		return ResponseData.ok();
	}
	
	
	/**
	 * 修改用户信息
	 * @param dto
	 * @param bindingResult
	 * @return
	 */
	public ResponseData updateUser(@RequestBody @Valid UserUpdateDTO dto, BindingResult bindingResult) {
		
		return null;
	}

}
