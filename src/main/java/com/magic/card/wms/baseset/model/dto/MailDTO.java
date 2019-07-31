package com.magic.card.wms.baseset.model.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MailDTO {
	/**
     * 快递单号
     */
	@ApiModelProperty("快递单号")
	private String mailNo;
	/**
     * 订单号
     */
	@ApiModelProperty("订单号")
	private String orderNo;
	/**
     * 订单日期
     */
	@ApiModelProperty("订单日期")
	private String orderDate;
	/**
     * 快递单生成日期
     */
	@ApiModelProperty("快递单生成日期")
	private String mailDate;
	
	/**
     * 是否拣货完成
     */
	@ApiModelProperty("是否拣货完成")
	private Integer isFinish;

	/**
     * 物流状态
     */
	@ApiModelProperty("物流状态")
	private Integer logisticsState;
	/**
     * 是否历史纪录
     */
	@ApiModelProperty("是否历史纪录")
	private Integer isHistory;
	/**
	 * 查询开始时间
	 */
	@ApiModelProperty("查询开始时间")
	private Date startDate;
	/**
	 * 查询结束时间
	 */
	@ApiModelProperty("查询结束时间")
	private Date endDate;
}
