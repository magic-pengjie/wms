package com.magic.card.wms.common.model.enums;

import lombok.Getter;

/**
 *  库位是否冻结枚举类
 * @author zhouhao
 */
@Getter
public enum IsFrozenEnum {

	UNFROZEN("N","未冻结"),
	FROZEN("Y","正常");
	
	private String code;
	private String desc;
	
	private IsFrozenEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static String getDescByCode(String code) {
		for (IsFrozenEnum e : IsFrozenEnum.values()) {
			if(e.getCode() == code) {
				return e.getDesc();
			}
		}
		return "";
	}
}
