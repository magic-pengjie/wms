package com.magic.card.wms.check.model.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckRecordInfoDto implements Serializable{

	private static final long serialVersionUID = -351363950824137125L;

	@ApiModelProperty(value = "商家ID")
	private String customerId;//商家ID
	
	@ApiModelProperty(value = "商家编码")
	private String customerCode;//商家编码
	
	@ApiModelProperty(value = "商家名称")
	private String customerName;//商家名称
	
	@ApiModelProperty(value = "商品ID")
	private String commodityId;//商品ID
	
	@ApiModelProperty(value = "商品名称")
	private String commodityName;//商品名称
	
	@ApiModelProperty(value = "SKUId")
	private String skuId;//SKUid
	
	@ApiModelProperty(value = "商品条码")
	private String skuCode;//商品条码
	
	@ApiModelProperty(value = "型号")
	private String modelNo;//型号
	
	@ApiModelProperty(value = "规格")
	private String spec;//规格
	
	@ApiModelProperty(value = "库位类型")
	private String storeType;//库位类型
	
	@ApiModelProperty(value = "库位ID")
	private String storehouseId;//库位ID
	
	@ApiModelProperty(value = "仓库编码")
	private String houseCode;//仓库编码
	
	@ApiModelProperty(value = "库位编码")
	private String storeCode;//库位编码
	
	@ApiModelProperty(value = "仓库类型")
	private String areaCode;//仓库类型
	
	@ApiModelProperty(value = "库存数量")
	private Integer storeNums;//库存
	
	@ApiModelProperty(value = "盘点数量")
	private Integer checkNums;//盘点数量
	
	@ApiModelProperty(value = "差异")
	private Integer diffNums;//差异
	
	@ApiModelProperty(value = "操作人")
	private String userName;//操作人
	
	@ApiModelProperty(value = "是否冻结")
	private String isFrozen;//是否冻结
	
	@ApiModelProperty(value = "数据状态")
	private Integer state;//数据状态
	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CheckRecordResponseDto [");
		if (customerId != null)
			builder.append("customerId=").append(customerId).append(", ");
		if (customerCode != null)
			builder.append("customerCode=").append(customerCode).append(", ");
		if (customerName != null)
			builder.append("customerName=").append(customerName).append(", ");
		if (commodityId != null)
			builder.append("commodityId=").append(commodityId).append(", ");
		if (commodityName != null)
			builder.append("commodityName=").append(commodityName).append(", ");
		if (skuId != null)
			builder.append("skuId=").append(skuId).append(", ");
		if (skuCode != null)
			builder.append("skuCode=").append(skuCode).append(", ");
		if (modelNo != null)
			builder.append("modelNo=").append(modelNo).append(", ");
		if (spec != null)
			builder.append("spec=").append(spec).append(", ");
		if (storeType != null)
			builder.append("storeType=").append(storeType).append(", ");
		if (storehouseId != null)
			builder.append("storehouseId=").append(storehouseId).append(", ");
		if (houseCode != null)
			builder.append("houseCode=").append(houseCode).append(", ");
		if (storeCode != null)
			builder.append("storeCode=").append(storeCode).append(", ");
		if (areaCode != null)
			builder.append("areaCode=").append(areaCode).append(", ");
		if (storeNums != null)
			builder.append("storeNums=").append(storeNums).append(", ");
		if (userName != null)
			builder.append("userName=").append(userName).append(", ");
		if (isFrozen != null)
			builder.append("isFrozen=").append(isFrozen).append(", ");
		if (state != null)
			builder.append("state=").append(state);
		builder.append("]");
		return builder.toString();
	}
	
}
