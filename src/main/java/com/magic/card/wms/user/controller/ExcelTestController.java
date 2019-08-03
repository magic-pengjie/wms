package com.magic.card.wms.user.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.magic.card.wms.common.model.EasyExcelParams;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.utils.EasyExcelUtil;
import com.magic.card.wms.user.model.po.User;
import com.magic.card.wms.user.model.vo.UserVO;
import com.magic.card.wms.user.service.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户前端控制器
 * </p>
 *
 * @author pengjie
 * @since 2019-06-13
 */
@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelTestController {

	@Autowired
	private IUserService userService;


	/***
	 * 导出用户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exportUser", method = RequestMethod.GET)
	public ResponseData exportUser(HttpServletRequest request,HttpServletResponse response) {
		try {
			List<User> userList = userService.getUserList(null,null);
			List<UserVO> UserVOList = new ArrayList<>();
			userList.forEach(User -> {
				UserVO vo = new UserVO();
				BeanUtils.copyProperties(User, vo);
				UserVOList.add(vo);
            });
			EasyExcelParams easyExcelParams = new EasyExcelParams();
			easyExcelParams.setExcelNameWithoutExt("用户信息导出表");
			easyExcelParams.setData(UserVOList);
			easyExcelParams.setDataModelClazz(UserVO.class);
			easyExcelParams.setResponse(response);
			easyExcelParams.setRequest(request);
			easyExcelParams.setSheetName("用户信息");
			EasyExcelUtil.exportExcel(easyExcelParams, ExcelTypeEnum.XLSX);;
		} catch (Exception e) {
			log.error("导出用户失败:{}",e);
			return ResponseData.error(ResultEnum.query_user_failed.getMsg());
		}
		return null;
	}

}
