package com.magic.card.wms.warehousing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum BillStateEnum {

	save("save","保存"),
	recevieing("recevieing","待收货"),
	recevied("recevied","已收货"),
	warehousing("warehousing","待入库"),
	stored("stored","入库"),
	approved("approved","已审核"),
	approve_fail("approve_fail","审核失败"),
	cancel("cancel","作废");
	
	@Setter
	@Getter
	private String code;
	@Setter
	@Getter
	private String name;
	
}
