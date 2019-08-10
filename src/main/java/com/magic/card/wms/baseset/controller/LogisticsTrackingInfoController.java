package com.magic.card.wms.baseset.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.dto.MailDTO;
import com.magic.card.wms.baseset.model.po.MailPicking;
import com.magic.card.wms.baseset.service.ILogisticsTrackingInfoService;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.ResultEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 包裹跟踪信息表 前端控制器
 * </p>
 *
 * @author pengjie
 * @since 2019-07-13
 */
@RestController
@RequestMapping("/tracking")
@Api(value = "包裹跟踪信息前端控制器", description = "包裹跟踪信息前端控制器")
@Slf4j
public class LogisticsTrackingInfoController {
	@Autowired
	private ILogisticsTrackingInfoService logisticsTrackingInfoService;
	
	@ApiOperation(value = "物流跟踪信息查询(查询邮政数据)", notes = "物流跟踪信息查询(查询邮政数据)")
	@GetMapping(value = "/getLogisticsInfo")
	public ResponseData getTrackingInfo(@RequestParam(required = true) List<MailPicking> list) {
		try {
			return logisticsTrackingInfoService.getTrackingInfo(list);
		} catch (Exception e) {
			log.error("预警代办列表查询失败:{}",e);
			return ResponseData.error(ResultEnum.query_error);
		}
		
	}
	@ApiOperation(value = "包裹信息列表查询", notes = "包裹信息列表查询")
	@PostMapping(value = "/getPickList")
	public ResponseData getPickList(@RequestBody MailDTO dto,PageInfo pageInfo) {
		try {
			Page page =	logisticsTrackingInfoService.selectPickInfoList(dto,pageInfo);
			return ResponseData.ok(page);
		} catch (Exception e) {
			log.error("物流信息列表查询失败:{}",e);
			return ResponseData.error(ResultEnum.query_error);
		}
		
	}
	
}

