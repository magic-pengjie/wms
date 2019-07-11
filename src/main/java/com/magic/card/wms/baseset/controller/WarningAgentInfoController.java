package com.magic.card.wms.baseset.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.dto.WarningAgentQueryDTO;
import com.magic.card.wms.baseset.model.po.WarningAgentInfo;
import com.magic.card.wms.baseset.service.IWarningAgentInfoService;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.ResultEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 预警代办表 前端控制器
 * </p>
 *
 * @author pengjie
 * @since 2019-07-10
 */
@RestController
@RequestMapping("/warningAgent")
@Api(value = "预警代办前端控制器", description = "预警代办前端控制器")
@Slf4j
public class WarningAgentInfoController {
	@Autowired
	private IWarningAgentInfoService warningAgentInfoService;

	@ApiOperation(value = "预警代办列表查询", notes = "预警代办列表查询")
	@PostMapping(value = "/getList")
	public ResponseData getList(@RequestBody WarningAgentQueryDTO dto,PageInfo pageInfo) {
		try {
			Page<WarningAgentInfo> page = warningAgentInfoService.getList(dto, pageInfo);
			return ResponseData.ok(page);
		} catch (Exception e) {
			log.error("预警代办列表查询失败:{}",e);
			return ResponseData.error(ResultEnum.query_error);
		}
		
	}
	
	@ApiOperation(value = "预警代办列表处理", notes = "预警代办列表处理")
	@GetMapping(value = "/deal")
	public ResponseData deal(@RequestParam(required = true)String id,String fid) {
		try {
			warningAgentInfoService.deal(id, null);
			return ResponseData.ok();
		} catch (Exception e) {
			log.error("预警代办列表处理失败:{}",e);
			return ResponseData.error(ResultEnum.update_error);
		}
		
	}
	
	@ApiOperation(value = "预警代办列表删除", notes = "预警代办列表删除")
	@GetMapping(value = "/delete")
	public ResponseData delete(@RequestParam(required = true)String id) {
		try {
			warningAgentInfoService.delete(id);
			return ResponseData.ok();
		} catch (Exception e) {
			log.error("预警代办列表处理删除:{}",e);
			return ResponseData.error(ResultEnum.delete_error);
		}
		
	}
}

