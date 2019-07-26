package com.magic.card.wms.baseset.model.vo;

import java.util.List;

import com.magic.card.wms.baseset.model.po.LogisticsTrackingInfo;

import lombok.Data;
@Data
public class LogisticsRepHeader {
	/**
	 * 接收方标识
	 */
	private String receiveID;
	/**
	 * 调用接口的执行结果
	 */
	private boolean responseState;
	/**
	 * 错误描述信息
	 */
	private String errorDesc;
	/**
	 * 返回的执行结果
	 */
	private List<LogisticsTrackingInfo> responseItems;
	
	public boolean getResponseState() {
		return responseState;
	}
	public void setResponseState(boolean responseState) {
		this.responseState = responseState;
	}
	

}
