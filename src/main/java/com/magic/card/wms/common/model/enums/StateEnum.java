package com.magic.card.wms.common.model.enums;

import lombok.Getter;

/**
 * 数据状态枚举类
 * @author zhouhao
 *
 */
@Getter
public enum StateEnum {

	delete(0,"删除"),
	normal(1,"正常"),
	order_pick(3, "订单已生成拣货单"),
	storehouse_stop(3, "庫位停用");
	
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
