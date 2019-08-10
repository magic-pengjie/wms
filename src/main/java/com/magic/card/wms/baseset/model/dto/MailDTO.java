package com.magic.card.wms.baseset.model.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MailDTO {
	/**
     * 查询类型
     */
	@ApiModelProperty("查询类型1:包裹信息 2:当天无物流信息3:历史无物流信息")
	private int type;
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
	 * 查询开始时间
	 */
	@ApiModelProperty("查询开始时间")
	private Date startDate;
	/**
	 * 查询结束时间
	 */
	@ApiModelProperty("查询结束时间")
	private Date endDate;
	
	/**
	 * 收件人姓名
	 */
	@ApiModelProperty("收件人姓名")
	private String reciptName;
	/**
	 * 收件人电话
	 */
	@ApiModelProperty("收件人电话")
	private String reciptPhone;
}
