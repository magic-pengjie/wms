package com.magic.card.wms.foreign.controller;

import javax.validation.Valid;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.foreign.model.OutRequestData;
import com.magic.card.wms.warehousing.model.dto.PurchaseBillDTO;
import com.magic.card.wms.warehousing.service.IPurchaseBillService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 对接客户系统控制器
 * </p>
 *
 * @author pengjie
 * @since 2019-07-08
 */
@RestController
@RequestMapping("/customer/data")
@Api("对接客户系统")
@Slf4j
public class CustomerExchangeController {

	@Autowired
	private IPurchaseBillService purchaseBillService;
	@javax.annotation.Resource
	private ResourceLoader resourceLoader;

	@ApiOperation(value = "采购单据新增", notes = "采购单据新增")
	@RequestMapping(value = "/purchase/add", method = RequestMethod.POST)
	public ResponseData add(@RequestBody @Valid OutRequestData request, BindingResult bindingResult) {
		try {
			// 数据验签
			if(checkSignature(request)) {
				ResponseData.error(ResultEnum.check_signature_error);
			}
			PurchaseBillDTO dto = (PurchaseBillDTO) request.getData();
			dto.setCustomerCode(request.getCustomerCode());
			dto.setCustomerName(request.getCustomerName());
			purchaseBillService.add(dto);
			return ResponseData.ok();
		} catch (BusinessException b) {
			log.error("新增采购单据失败:{}", b);
			return ResponseData.error(b.getErrCode(), b.getErrMsg());
		} catch (Exception e) {
			log.error("新增采购单据失败:{}", e);
			return ResponseData.error(ResultEnum.data_error);
		}

	}

	@ApiOperation(value = "订单新增", notes = "订单新增")
	@RequestMapping(value = "/order/add", method = RequestMethod.POST)
	public ResponseData orderaAdd(@RequestBody @Valid PurchaseBillDTO dto, BindingResult bindingResult) {
		try {
			purchaseBillService.add(dto);
			return ResponseData.ok();
		} catch (BusinessException b) {
			log.error("新增采购单据失败:{}", b);
			return ResponseData.error(b.getErrCode(), b.getErrMsg());
		} catch (Exception e) {
			log.error("新增采购单据失败:{}", e);
			return ResponseData.error(ResultEnum.data_error);
		}

	}

	boolean checkSignature(OutRequestData request) {
		String signature = request.getSignature();
		StringBuffer checkStr = new StringBuffer();
		checkStr.append(request.getCustomerCode())
				.append(request.getCustomerName())
				.append(request.getReqTime())
				.append(JSONObject.toJSONString(request.getData()));
		if(ObjectUtils.equals(signature, checkStr)) {
			return true;
		}
		return false;
	}

}
