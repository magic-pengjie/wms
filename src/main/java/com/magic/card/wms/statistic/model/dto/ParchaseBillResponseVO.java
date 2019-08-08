package com.magic.card.wms.statistic.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ParchaseBillResponseVO extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = -2178647092498673122L;

    @ExcelProperty(value = "商家编码", index = 0)
    private String customerCode;

    @ExcelProperty(value = "商家名称", index = 1)
    private String customerName;

    @ExcelProperty(value = "商品ID", index = 2)
    private String commodityId;

    @ExcelProperty(value = "商品名称", index = 3)
    private String commodityName;

    @ExcelProperty(value = "商品条码", index = 4)
    private String skuCode;

    @ExcelProperty(value = "型号", index = 5)
    private String modelNo;

    @ExcelProperty(value = "规格", index = 6)
    private String spec;

    @ExcelProperty(value = "入库数量", index = 7)
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
