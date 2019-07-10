package com.magic.card.wms.foreign.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OutRequestData<T> {

	/**
	 * 商家编码
	 */
	@NotBlank(message = "商家编码必输")
	private String customerCode;
	/**
	 * 商家名称
	 */
	@NotBlank(message = "商家名称必输")
	private String customerName;
	/**
	 * 请求时间
	 */
	@NotBlank(message = "请求时间必输")
	private String reqTime;
	/**
	 * 数据签名
	 */
	@NotBlank(message = "数据签名必输")
	private String signature;
	/**
	 * 业务对象数据
	 */
	@ApiModelProperty("业务对象数据")
	@NotNull(message = "业务对象数据不能为空")
	private T data;
}
