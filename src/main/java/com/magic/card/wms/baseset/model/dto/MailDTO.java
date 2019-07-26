package com.magic.card.wms.baseset.model.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MailDTO {

	/**
     * 物流状态
     */
	@ApiModelProperty("物流状态")
    @NotNull(message = "物流状态不可为空")
	private Integer logisticsState;
	/**
     * 快递单号
     */
	@ApiModelProperty("快递单号")
    @NotNull(message = "快递单号")
	private String mailNo;
	/**
     * 订单号
     */
	@ApiModelProperty("订单号")
    @NotNull(message = "订单号")
	private String orderNo;
	/**
     * 订单日期
     */
	@ApiModelProperty("订单日期")
    @NotNull(message = "订单日期")
	private String orderDate;
	/**
     * 快递单生成日期
     */
	@ApiModelProperty("快递单生成")
    @NotNull(message = "快递单生成")
	private String mailDate;
	
	/**
     * 是否拣货完成
     */
	@ApiModelProperty("是否拣货完成")
    @NotNull(message = "是否拣货完成")
	private Integer isFinish;
}
