package com.magic.card.wms.statistic.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ParchaseBillResponseDto implements Serializable {

    private static final long serialVersionUID = -2178647092498673122L;

    @ApiModelProperty(value = "商家Code")
    private String customerCode;

    @ApiModelProperty(value = "商家名称")
    private String customerName;

    @ApiModelProperty(value = "商品ID")
    private String commodityId;

    @ApiModelProperty(value = "商品名称")
    private String commodityName;

    @ApiModelProperty(value = "商品条码")
    private String skuCode;

    @ApiModelProperty(value = "型号")
    private String modelNo;

    @ApiModelProperty(value = "规格")
    private String spec;

    @ApiModelProperty(value = "入库数量")
    private String inStorehouseNum;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ParchaseBillResponseDto{");
        sb.append("customerCode='").append(customerCode).append('\'');
        sb.append(", customerName='").append(customerName).append('\'');
        sb.append(", commodityId='").append(commodityId).append('\'');
        sb.append(", commodityName='").append(commodityName).append('\'');
        sb.append(", skuCode='").append(skuCode).append('\'');
        sb.append(", modelNo='").append(modelNo).append('\'');
        sb.append(", spec='").append(spec).append('\'');
        sb.append(", inStorehouseNum='").append(inStorehouseNum).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
