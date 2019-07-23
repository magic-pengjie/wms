package com.magic.card.wms.baseset.model.dto;

import lombok.Data;

/**
 * 物流信息查询模型
 * @author PENGJIE
 * @date 2019年7月13日
 */
@Data
public class LogisticsTrackingInfoQueryDTO {
	/**
	 * 运单号
	 */
	private String traceNo;
	/**
	 * 执行结果
	 */
	private String responseState;
	
	
}
