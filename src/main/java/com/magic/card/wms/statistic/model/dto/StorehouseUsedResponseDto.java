package com.magic.card.wms.statistic.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StorehouseUsedResponseDto implements Serializable {

    private static final long serialVersionUID = 2759775426614722587L;

    @ApiModelProperty(value = "商家Code")
    private String customerCode;

    @ApiModelProperty(value = "商家名称")
    private String customerName;

    @ApiModelProperty(value = "库位类型Code")
    private String houseCode;

    @ApiModelProperty(value = "库位类型名称")
    private String storeTypeName;

    @ApiModelProperty(value = "总库位数量")
    private String totalStoreNum;

    @ApiModelProperty(value = "已用库位数量")
    private String usedStoreNum;

    @ApiModelProperty(value = "剩余库位数量")
    private String residuleStoreNum;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StorehouseUsedResponseDto{");
        sb.append("customerCode='").append(customerCode).append('\'');
        sb.append(", customerName='").append(customerName).append('\'');
        sb.append(", houseCode='").append(houseCode).append('\'');
        sb.append(", totalStoreNum='").append(totalStoreNum).append('\'');
        sb.append(", usedStoreNum='").append(usedStoreNum).append('\'');
        sb.append(", residuleStoreNum='").append(residuleStoreNum).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
