package com.magic.card.wms.common.model.enums;

import lombok.Getter;

/**
 * 数据状态枚举类
 * @author zhouhao
 *
 */
@Getter
public enum StateEnum {

	normal(0,"正常"),
	delete(1,"删除");
	
	private Integer code;
	private String desc;
	
	private StateEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static String getDescByCode(Integer code) {
		for (StateEnum e : StateEnum.values()) {
			if(e.getCode() == code) {
				return e.getDesc();
			}
		}
		return "";
	}
}
