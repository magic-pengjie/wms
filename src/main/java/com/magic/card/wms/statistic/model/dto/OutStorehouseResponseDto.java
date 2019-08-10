package com.magic.card.wms.statistic.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OutStorehouseResponseDto implements Serializable {


    private static final long serialVersionUID = -1418860027642861917L;

    @ApiModelProperty(value = "商家ID")
    private String customerId;

    @ApiModelProperty(value = "商家名称")
    private String customerName;

    @ApiModelProperty(value = "商品ID")
    private String commodityId;

    @ApiModelProperty(value = "商品名称")
    private String commodityName;

    @ApiModelProperty(value = "商品条码")
    private String skuCode;

    @ApiModelProperty(value = "品牌")
    private String banner;

    @ApiModelProperty(value = "型号")
    private String modelNo;

    @ApiModelProperty(value = "规格")
    private String spec;

    @ApiModelProperty(value = "出库数量")
    private String outStorehouseNum;

    @ApiModelProperty(value = "效期分析")
    private String analysis;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OutStorehouseResponseDto{");
        sb.append("customerId='").append(customerId).append('\'');
        sb.append(", customerName='").append(customerName).append('\'');
        sb.append(", commodityId='").append(commodityId).append('\'');
        sb.append(", commodityName='").append(commodityName).append('\'');
        sb.append(", skuCode='").append(skuCode).append('\'');
        sb.append(", banner='").append(banner).append('\'');
        sb.append(", modelNo='").append(modelNo).append('\'');
        sb.append(", spec='").append(spec).append('\'');
        sb.append(", outStorehouseNum='").append(outStorehouseNum).append('\'');
        sb.append(", analysis='").append(analysis).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
