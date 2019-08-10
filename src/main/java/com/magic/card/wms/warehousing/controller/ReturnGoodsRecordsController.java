package com.magic.card.wms.warehousing.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.warehousing.model.dto.ReturnGoodsDTO;
import com.magic.card.wms.warehousing.model.dto.ReturnGoodsRecordsDTO;
import com.magic.card.wms.warehousing.service.IReturnGoodsRecordsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 退货记录表 前端控制器
 * </p>
 *
 * @author pengjie
 * @since 2019-08-06
 */
@RestController
@RequestMapping("/warehousing/returnGoods")
@Api(value = "退货管理", description = "退货管理")
@Slf4j
public class ReturnGoodsRecordsController {
	@Autowired
	private IReturnGoodsRecordsService returnGoodsRecordsService;

	@ApiOperation(value = "退货记录信息查询", notes = "退货记录信息查询")
	@RequestMapping(value = "/selectReturnList", method = RequestMethod.POST)
	public ResponseData selectReturnList(@RequestBody ReturnGoodsDTO dto, PageInfo pageInfo) {
		try {
			return ResponseData.ok(returnGoodsRecordsService.selectReturnList(pageInfo, dto));
		} catch (Exception e) {
			log.error("退货记录信息查询列表失败:{}", e);
			return ResponseData.error(ResultEnum.query_error);
		}

	}
	
	@ApiOperation(value = "退货及上架", notes = "退货及上架")
	@RequestMapping(value = "/returnAndGrounding", method = RequestMethod.POST)
	public ResponseData returnAndGrounding(@RequestBody @Valid ReturnGoodsRecordsDTO dto,BindingResult bindingResult) {
		try {
			returnGoodsRecordsService.returnAndGrounding(dto);
			return ResponseData.ok();
		} catch (Exception e) {
			log.error("退货失败:{}", e);
			return ResponseData.error(ResultEnum.add_error);
		}

	}
	
}
