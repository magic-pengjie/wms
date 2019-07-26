package com.magic.card.wms.baseset.model.vo;

import com.magic.card.wms.common.utils.DateUtil;

import lombok.Data;

@Data
public class LogisticsReqHeader {

	/**
	 * 发送方标识 Y
	 */
	private String sendID;
	/**
	 * 数据生产的省公司代码
	 */
	private Integer proviceNo=99;
	/**
	 * 消息类别
	 */
	private String msgKind="JDPT_XXX_TRACE";
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
	private String receiveID="滨中存储系统";
	/**
	 * 批次号
	 */
	private String batchNo;
	/**
	 * 数据类型
	 */
	private Integer dataType=1;
	
}
