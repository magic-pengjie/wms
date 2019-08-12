package com.magic.card.wms.check.model.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class QueryCheckRecordDto implements Serializable {

	private static final long serialVersionUID = -1897317289989534796L;
	
	//商家ID
	private String customerId;

	//商家Code
	private String customerCode;
	
	//库位IDList
	private List<Long> storeIdList;
	
	//商品IDList
	private List<Integer> commodityIdList;

	@Override
	public String
	toString() {
		final StringBuffer sb = new StringBuffer("QueryCheckRecordDto{");
		sb.append("customerId='").append(customerId).append('\'');
		sb.append(", customerCode='").append(customerCode).append('\'');
		sb.append(", storeIdList=").append(storeIdList);
		sb.append(", commodityIdList=").append(commodityIdList);
		sb.append('}');
		return sb.toString();
	}
}
