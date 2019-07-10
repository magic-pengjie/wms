package com.magic.card.wms.baseset.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.service.IWarningAgentInfoService;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.warehousing.model.dto.BillQueryDTO;
import com.magic.card.wms.warehousing.model.vo.PurchaseBillVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
	public ResponseData getList(@RequestBody BillQueryDTO dto,PageInfo pageInfo) {
		try {
			return ResponseData.ok();
		} catch (Exception e) {
			log.error("查询采购单据列表失败:{}",e);
			return ResponseData.error(ResultEnum.query_error);
		}
		
	}
}

