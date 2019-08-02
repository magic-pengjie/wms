package com.magic.card.wms.check.model.dto;

import com.magic.card.wms.check.model.po.CheckRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CheckRecordInfoResponse extends CheckRecord implements Serializable{

	private static final long serialVersionUID = -351363950824137125L;

	@ApiModelProperty(value = "商家ID")
	private String customerId;//商家ID
	
	@ApiModelProperty(value = "商家名称")
	private String customerName;//商家名称
	
	@ApiModelProperty(value = "商品ID")
	private String commodityId;//商品ID
	
	@ApiModelProperty(value = "商品名称")
	private String commodityName;//商品名称
	
	@ApiModelProperty(value = "商品条码")
	private String skuCode;//商品条码

	@ApiModelProperty(value = "型号")
	private String modelNo;//型号
	
	@ApiModelProperty(value = "规格")
	private String spec;//规格
	
	@ApiModelProperty(value = "库位ID")
	private String storehouseId;//库位ID

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("CheckRecordInfoResponse{");
		sb.append("customerId='").append(customerId).append('\'');
		sb.append(", customerName='").append(customerName).append('\'');
		sb.append(", commodityId='").append(commodityId).append('\'');
		sb.append(", commodityName='").append(commodityName).append('\'');
		sb.append(", skuCode='").append(skuCode).append('\'');
		sb.append(", modelNo='").append(modelNo).append('\'');
		sb.append(", spec='").append(spec).append('\'');
		sb.append(", storehouseId='").append(storehouseId).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
