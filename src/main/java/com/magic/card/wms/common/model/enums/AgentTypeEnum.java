package com.magic.card.wms.common.model.enums;

import lombok.Getter;

@Getter
public enum AgentTypeEnum {

	food("food","食品类预警"),
	unsalable_goods("unsalableGoods","滞销品预警"),
	Logistics("logistics","物流信息预警"),
	replenishment("replenishment","补货预警"),
	inventory("inventory","库存预警"),
	split("split","商品拆分");
	
	private String code;
	private String name;
	
	private AgentTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static String getNameByCode(String code) {
		for (AgentTypeEnum e : AgentTypeEnum.values()) {
			if(e.getCode() == code) {
				return e.getName();
			}
		}
		return "";
	}
	
}
