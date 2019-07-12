package com.magic.card.wms.check.model.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *	 盘点记录表
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-26
 */
@Data
public class CheckRecordDto implements Serializable {

	private static final long serialVersionUID = -3107161252979052762L;

	@ApiModelProperty(value = "盘点表主键ID")
    private Long id;
    
    @ApiModelProperty(value = "商家编码")
    private String customerCode;
    
    @ApiModelProperty(value = "SKUId")
    private String skuId;
    
    @ApiModelProperty(value = "库位类型")
    private String storehouseType;
    
    @ApiModelProperty(value = "库位编码")
    private String storehouseCode;
    
    @ApiModelProperty(value = "库存数量")
    private Integer storeNums;
    
    @ApiModelProperty(value = "盘点数量")
    private Integer checkNums;
    
    @ApiModelProperty(value = "盘点日期")
    private Date checkDate;
    
    @ApiModelProperty(value = "差异数量")
    private Integer diffNums;
    
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CheckRecordDto [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (customerCode != null)
			builder.append("customerCode=").append(customerCode).append(", ");
		if (skuId != null)
			builder.append("skuId=").append(skuId).append(", ");
		if (storehouseType != null)
			builder.append("storehouseType=").append(storehouseType).append(", ");
		if (storehouseCode != null)
			builder.append("storehouseCode=").append(storehouseCode).append(", ");
		if (storeNums != null)
			builder.append("storeNums=").append(storeNums).append(", ");
		if (checkNums != null)
			builder.append("checkNums=").append(checkNums).append(", ");
		if (checkDate != null)
			builder.append("checkDate=").append(checkDate).append(", ");
		if (diffNums != null)
			builder.append("diffNums=").append(diffNums);
		builder.append("]");
		return builder.toString();
	}
    
}
