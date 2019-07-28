package com.magic.card.wms.baseset.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.dto.RulesConfigDTO;
import com.magic.card.wms.baseset.model.dto.RulesConfigReqDTO;
import com.magic.card.wms.baseset.model.po.RulesConfig;
import com.magic.card.wms.baseset.service.IRulesConfigService;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.ResultEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 规则配置前端控制器
 * </p>
 *
 * @author pengjie
 * @since 2019-07-11
 */
@Slf4j
@Api(value = "规则配置前端控制器", description = "规则配置前端控制器")
@RestController
@RequestMapping("/rulesConfig")
public class RulesConfigController {
	@Autowired
	private IRulesConfigService rulesConfigService;
	
	@ApiOperation(value = "配置列表查询", notes = "配置列表查询")
	@PostMapping(value = "/getList")
	public ResponseData ruleList(@RequestBody RulesConfigReqDTO dto,PageInfo pageInfo) {
		try {
			Page<RulesConfig> page = rulesConfigService.getList(dto, pageInfo);
			return ResponseData.ok(page);
		} catch (Exception e) {
			log.error("配置列表查询失败:{}",e);
			return ResponseData.error(ResultEnum.query_error);
		}
		
	}
	@ApiOperation(value = "新增配置", notes = "新增配置")
	@PostMapping(value = "/add")
	public ResponseData ruleAdd(@RequestBody RulesConfigDTO dto) {
		try {
			rulesConfigService.add(dto);;
			return ResponseData.ok();
		} catch (Exception e) {
			log.error("配置列表查询失败:{}",e);
			return ResponseData.error(ResultEnum.add_error);
		}
		
	}
	@ApiOperation(value = "修改配置", notes = "修改配置")
	@PostMapping(value = "/update")
	public ResponseData ruleUdate(@RequestBody RulesConfigDTO dto) {
		try {
			 rulesConfigService.update(dto);
			return ResponseData.ok();
		} catch (Exception e) {
			log.error("修改配置失败:{}",e);
			return ResponseData.error(ResultEnum.update_error);
		}
		
	}

	@ApiOperation(value = "删除规则", notes = "删除规则")
	@GetMapping(value = "/deal")
	public ResponseData ruleDeal(long id) {
		try {
			 rulesConfigService.detele(id);
			return ResponseData.ok();
		} catch (Exception e) {
			log.error("删除配置失败:{}",e);
			return ResponseData.error(ResultEnum.delete_error);
		}
		
	}
	
}

