package com.magic.card.wms.warehousing.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 退货查询模型
 * @author PENGJIE
 * @date 2019年8月6日
 */
@Data
@ApiModel("退货查询模型")
public class ReturnGoodsDTO {

	/**
     * 订单号
     */
	@ApiModelProperty("订单号")
	private String orderNo;
	/**
     * 快递单号
     */
	@ApiModelProperty("快递单号")
	private String mailNo;
	/**
     * 系统订单号
     */
	@ApiModelProperty("系统订单号")
	private String systemOrderNo;
	/**
     * 商家名称
     */
	@ApiModelProperty("商家名称")
	private String customerName;
	/**
     * 商品编码
     */
	@ApiModelProperty("商品编码")
	private String commodityCode;
	/**
     * 商品名称
     */
	@ApiModelProperty("商品名称")
	private String commodityName;
	/**
     * 收货人名称
     */
	@ApiModelProperty("收货人名称")
	private String reciptName;
	/**
     * 收货人电话
     */
	@ApiModelProperty("收货人电话")
	private String reciptPhone;
	
}
