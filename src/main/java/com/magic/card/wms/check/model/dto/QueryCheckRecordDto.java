package com.magic.card.wms.check.model.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class QueryCheckRecordDto implements Serializable {

	private static final long serialVersionUID = -1897317289989534796L;
	
	//商家ID
	private String customerId;
	
	//库位IDList
	private List<Long> storeIdList;
	
	//商品IDList
	private List<Integer> commodityIdList;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryCheckRecordDto [");
		if (customerId != null)
			builder.append("customerId=").append(customerId).append(", ");
		if (storeIdList != null)
			builder.append("storeIdList=").append(storeIdList).append(", ");
		if (commodityIdList != null)
			builder.append("commodityIdList=").append(commodityIdList);
		builder.append("]");
		return builder.toString();
	}
	
}
