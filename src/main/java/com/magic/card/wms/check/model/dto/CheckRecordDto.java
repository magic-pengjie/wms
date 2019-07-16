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
    
    @ApiModelProperty(value = "初盘数量")
    private Integer firstCheckNums;

    @ApiModelProperty(value = "复盘数量")
    private Integer secondCheckNums;

    @ApiModelProperty(value = "终盘数量")
    private Integer thirdCheckNums;
    
    @ApiModelProperty(value = "盘点日期")
    private Date checkDate;
    
    @ApiModelProperty(value = "差异数量")
    private Integer diffNums;

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("CheckRecordDto{");
		sb.append("id=").append(id);
		sb.append(", customerCode='").append(customerCode).append('\'');
		sb.append(", skuId='").append(skuId).append('\'');
		sb.append(", storehouseType='").append(storehouseType).append('\'');
		sb.append(", storehouseCode='").append(storehouseCode).append('\'');
		sb.append(", storeNums=").append(storeNums);
		sb.append(", firstCheckNums=").append(firstCheckNums);
		sb.append(", secondCheckNums=").append(secondCheckNums);
		sb.append(", thirdCheckNums=").append(thirdCheckNums);
		sb.append(", checkDate=").append(checkDate);
		sb.append(", diffNums=").append(diffNums);
		sb.append('}');
		return sb.toString();
	}
}
