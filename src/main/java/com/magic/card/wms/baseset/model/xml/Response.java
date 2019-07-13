package com.magic.card.wms.baseset.model.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

@XStreamAlias("response")
@Data
public class Response {

	/**
	 * 订单号
	 */
	private String txLogisticID;
	/**
	 * 成功标识
	 */
	private boolean success;
	/**
	 * 失败原因
	 */
	private String reason;
	
	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
