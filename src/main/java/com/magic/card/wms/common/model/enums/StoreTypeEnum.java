package com.magic.card.wms.common.model.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum StoreTypeEnum {

	KDZCQ("CK-GN-KDZCQ","快递暂存区"),
	DRCQ("CK-GN-DRCQ","待入仓区"),
	CCQ("CK-GN-CCQ","存储区"),
	JHQ("CK-GN-JHQ","拣货区"),
	HCQ("CK-GN-HCQ","缓存区"),
	BZQ("CK-GN-BZQ","包装区"),
	THQ("CK-GN-THQ","退货区"),
	CPQ("CK-GN-CPQ","次品区");
	
	private String code;
	private String desc;
	
	private StoreTypeEnum(String code, String desc) {
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
