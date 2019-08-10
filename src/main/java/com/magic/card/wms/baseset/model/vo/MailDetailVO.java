package com.magic.card.wms.baseset.model.vo;

import java.util.Date;
import java.util.List;

import com.magic.card.wms.baseset.model.po.LogisticsTrackingInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MailDetailVO {
	
	/**
     * 拣货单号
     */
	@ApiModelProperty("拣货单号")
	private String pickNo;
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
     * 系统订单号
     */
	@ApiModelProperty("系统订单号")
	private String systemOrderNo;
	
	/**
     * 发送状态
     */
	@ApiModelProperty("发送状态")
	private String sendState;
	
	/**
     * 发送失败原因
     */
	@ApiModelProperty("发送失败原因")
	private String failReason;
	/**
	 * 是否捡货完成
	 */
	private int isFinish;
	/**
	 * 物流状态
	 */
	private int logisticsState;
	
	/**
     * 订单日期
     */
	@ApiModelProperty("订单日期")
	private Date orderDate;
	/**
     * 快递单生成日期
     */
	@ApiModelProperty("快递单生成日期")
	private Date mailDate;
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
	/**
	 * 商品信息
	 */
	List<MailCommodityDetail> detailList;
	
	/**
	 * 物流信息
	 */
	private LogisticsTrackingInfo logisticsInfo;
	
}
