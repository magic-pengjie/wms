package com.magic.card.wms.baseset.model.vo;

import com.magic.card.wms.common.utils.DateUtil;

import lombok.Data;

@Data
public class LogisticsReqHeader {

	/**
	 * 发送方标识 Y
	 */
	private String sendID="BINZHONGWMS";
	/**
	 * 数据生产的省公司代码
	 */
	private Integer proviceNo=99;
	/**
	 * 消息类别
	 */
	private String msgKind="BINZHONGWMS_JDPT_TRACE";
	/**
	 * 消息唯一序列号
	 */
	private String serialNo;
	/**
	 * 消息发送日期时间
	 */
	private String sendDate=DateUtil.getUserDate("yyyyMMddhhmmss");
	/**
	 * 代表接收方标识
	 */
	private String receiveID="JDPT";
	/**
	 * 批次号
	 */
	private String batchNo="999";
	/**
	 * 数据类型
	 */
	private Integer dataType=1;
	
}
