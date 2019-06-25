package com.magic.card.wms.warehousing.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.warehousing.model.po.PurchaseBillDetail;
import com.magic.card.wms.warehousing.service.IPurchaseBillDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 采购单明细表 前端控制器
 * </p>
 *
 * @author pengjie
 * @since 2019-06-22
 */
@RestController
@RequestMapping("/warehousing/purchaseBillDetail")
@Api("采购单商品详情")
@Slf4j
public class PurchaseBillDetailController {
	
	@Autowired
	private IPurchaseBillDetailService purchaseBillDetailService;

	@ApiOperation(value = "采购单据商品详情查询", notes = "采购单据商品详情查询")
	@RequestMapping(value = "/selectDetail/{purchaseId}", method = RequestMethod.GET)
	public ResponseData selectPurchaseBillDetail(@PathVariable(required = true)long purchaseId,PageInfo pageInfo) {
		try {
			List<PurchaseBillDetail>  list = purchaseBillDetailService.selectPurchaseBillDetail(purchaseId);
			return ResponseData.ok(list);
		} catch (Exception e) {
			log.error("查询采购单据详情失败:{}",e);
			return ResponseData.error(ResultEnum.query_error);
		}
	}
}

