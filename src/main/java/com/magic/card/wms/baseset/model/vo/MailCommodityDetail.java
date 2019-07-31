package com.magic.card.wms.baseset.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MailCommodityDetail {
	/**
     * 拣货单号
     */
	@ApiModelProperty("商品条码")
	private String barCode;
	
	/**
     * 商品名称
     */
	@ApiModelProperty("商品名称")
	private String commodityName;
	
	/**
     * 规格
     */
	@ApiModelProperty("规格")
	private String spec;
	
	/**
     * 商品型号
     */
	@ApiModelProperty("商品型号")
	private String modelNo;
	
	/**
     * 商品打包数量
     */
	@ApiModelProperty("商品打包数量")
	private long packageNums;
}
