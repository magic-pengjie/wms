package com.magic.card.wms.baseset.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "单量统计模型")
public class OrderStatisticsVO {

	/**
     * 商家编码
     */
	@ApiModelProperty("商家编码")
	private String customerCode;
	
	/**
     * 商家名称
     */
	@ApiModelProperty("商家名称")
	private String customerName;
	/**
     * 总订单量
     */
	@ApiModelProperty("总订单量")
	private Integer orderNums;
	/**
     * 已分拣数量
     */
	@ApiModelProperty("已分拣数量")
	private Integer pickNums;
	/**
     * 已出库数量
     */
	@ApiModelProperty("已出库数量")
	private Integer outNums;
	
}
