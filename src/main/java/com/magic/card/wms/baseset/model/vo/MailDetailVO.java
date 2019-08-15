package com.magic.card.wms.baseset.model.vo;

import java.util.Date;
import java.util.List;

import com.magic.card.wms.baseset.model.po.LogisticsTrackingInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "包裹信息模型")
public class MailDetailVO {
	
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
     * 拣货单号
     */
	@ApiModelProperty("拣货单号")
	private String pickNo;
	/**
	 * 包裹id
	 */
	private long id;
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
     * 发送状态(未发送:0;已发送:1;发送失败:2;终止发送3)
     */
	@ApiModelProperty("发送状态(未发送:0;已发送:1;发送失败:2;终止发送3)")
	private String sendState;
	
	/**
     * 发送失败原因
     */
	@ApiModelProperty("发送失败原因")
	private String failReason;
	/**
	 * 是否捡货完成1:是 0:否
	 */
	private int isFinish;
	/**
	 * 是否称重完成1:是 0:否
	 */
	private int isWeight;
	/**
	 * 物流状态态0:无 1:有 8:已人工处理 9完成
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
	 * 收件人地址
	 */
	@ApiModelProperty("收件人地址")
	private String reciptAddr;
	/**
	 * 商品信息
	 */
	List<MailCommodityDetail> detailList;
	
	/**
	 * 物流信息
	 */
	private LogisticsTrackingInfo logisticsInfo;
	
}
