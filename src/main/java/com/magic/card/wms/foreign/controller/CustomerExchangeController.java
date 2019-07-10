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
import com.magic.card.wms.baseset.model.dto.OrderInfoDTO;
import com.magic.card.wms.baseset.service.IOrderService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.utils.Digest;
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
@Api(value = "对接客户系统" ,description = "对接客户系统")

@Slf4j
public class CustomerExchangeController {

	@Autowired
	private IPurchaseBillService purchaseBillService;
	@Autowired
	private IOrderService orderService;
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
		} catch (OperationException o) {
			log.error("新增采购单据失败:{}", o);
			return ResponseData.error(o.getErrCode(), o.getErrMsg());
		} catch (Exception e) {
			log.error("新增采购单据失败:{}", e);
			return ResponseData.error(ResultEnum.data_error);
		}

	}

	@ApiOperation(value = "订单新增", notes = "订单新增")
	@RequestMapping(value = "/order/add", method = RequestMethod.POST)
	public ResponseData orderaAdd(@RequestBody @Valid OutRequestData request, BindingResult bindingResult) {
		try {
			// 数据验签
			if(checkSignature(request)) {
				ResponseData.error(ResultEnum.check_signature_error);
			}
			OrderInfoDTO dto = (OrderInfoDTO) request.getData();
			dto.setCustomerCode(request.getCustomerCode());
			dto.setCustomerName(request.getCustomerName());
			
			orderService.importOrder(dto, Constants.DEFAULT_USER);
			return ResponseData.ok();
		} catch (OperationException o) {
			log.error("保存订单失败:{}", o);
			return ResponseData.error(o.getErrCode(), o.getErrMsg());
		} catch (Exception e) {
			log.error("保存订单失败:{}", e);
			return ResponseData.error(ResultEnum.data_error);
		}
	}

	/**
	 * 验签
	 * @param request
	 * @return
	 */
	boolean checkSignature(OutRequestData request) {
		String signature = request.getSignature();
		StringBuffer checkStr = new StringBuffer();
		checkStr.append(request.getCustomerCode())
				.append(request.getCustomerName())
				.append(request.getReqTime())
				.append(JSONObject.toJSONString(request.getData()));
		if(ObjectUtils.equals(signature, Digest.Md5Base64(checkStr.toString()))) {
			return true;
		}
		return false;
	}

}
