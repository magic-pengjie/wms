package com.magic.card.wms.common.model.enums;

import lombok.Getter;

/**
 * 物流状态枚举类
 * @author PENGJIE
 * @date 2019年8月13日
 */
@Getter
public enum LogisticsEnum {
	nothing(0,"无物流信息"),
	exist(1,"有物流信息"),
	confirm(8, "已人工处理"),
	finish(9, "完成");
	
	private Integer code;
	private String desc;
	
	private LogisticsEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static String getDescByCode(Integer code) {
		for (LogisticsEnum e : LogisticsEnum.values()) {
			if(e.getCode() == code) {
				return e.getDesc();
			}
		}
		return "";
	}
}
