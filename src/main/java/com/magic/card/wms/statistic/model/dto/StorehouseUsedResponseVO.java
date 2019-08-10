package com.magic.card.wms.statistic.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StorehouseUsedResponseVO extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 2885760326292994811L;

    @ExcelProperty(value = "商家Code", index = 0)
    private String customerCode;

    @ExcelProperty(value = "商家名称", index = 0)
    private String customerName;

    @ExcelProperty(value = "库位类型", index = 0)
    private String houseCode;

    @ApiModelProperty(value = "")
    @ExcelProperty(value = "总库位数量", index = 0)
    private String totalStoreNum;

    @ExcelProperty(value = "已用库位数量", index = 0)
    private String usedStoreNum;

    @ExcelProperty(value = "剩余库位数量", index = 0)
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
