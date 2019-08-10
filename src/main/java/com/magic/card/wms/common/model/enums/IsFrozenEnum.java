package com.magic.card.wms.common.model.enums;

import lombok.Getter;

/**
 *  库位是否冻结枚举类
 * @author zhouhao
 */
@Getter
public enum IsFrozenEnum {

	UNFROZEN(0,"未冻结"),
	FROZEN(1,"冻结");
	
	private int code;
	private String desc;
	
	private IsFrozenEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static String getDescByCode(int code) {
		for (IsFrozenEnum e : IsFrozenEnum.values()) {
			if(e.getCode() == code) {
				return e.getDesc();
			}
		}
		return "";
	}
}
