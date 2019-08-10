package com.magic.card.wms.statistic.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StorehouseUsedResponseDto implements Serializable {

    private static final long serialVersionUID = 2759775426614722587L;

    @ApiModelProperty(value = "商家ID")
    private String customerId;

    @ApiModelProperty(value = "商家名称")
    private String customerName;

    @ApiModelProperty(value = "商品ID")
    private String commodityId;

    @ApiModelProperty(value = "存储库位")
    private String spec;

    @ApiModelProperty(value = "零拣库位")
    private String commodityName;

    @ApiModelProperty(value = "已用库位数")
    private String skuCode;

    @ApiModelProperty(value = "空闲库位数")
    private String modelNo;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StorehouseUsedResponseDto{");
        sb.append("customerId='").append(customerId).append('\'');
        sb.append(", customerName='").append(customerName).append('\'');
        sb.append(", commodityId='").append(commodityId).append('\'');
        sb.append(", spec='").append(spec).append('\'');
        sb.append(", commodityName='").append(commodityName).append('\'');
        sb.append(", skuCode='").append(skuCode).append('\'');
        sb.append(", modelNo='").append(modelNo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
