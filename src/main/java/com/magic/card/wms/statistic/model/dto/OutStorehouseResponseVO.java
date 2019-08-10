package com.magic.card.wms.statistic.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OutStorehouseResponseVO extends BaseRowModel implements Serializable {


    private static final long serialVersionUID = -1418860027642861917L;

    @ExcelProperty(value = "商家Code", index = 0)
    private String customerCode;

    @ExcelProperty(value = "商家名称", index = 0)
    private String customerName;

    private String commodityId;

    @ExcelProperty(value = "商品名称", index = 0)
    private String commodityName;

    @ExcelProperty(value = "商品条码", index = 0)
    private String skuCode;

    @ExcelProperty(value = "品牌", index = 0)
    private String banner;

    @ExcelProperty(value = "型号", index = 0)
    private String modelNo;

    @ExcelProperty(value = "规格", index = 0)
    private String spec;

    @ExcelProperty(value = "出库数量", index = 0)
    private String outStorehouseNum;

    private String analysis;//效期分析

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OutStorehouseResponseDto{");
        sb.append("customerCode='").append(customerCode).append('\'');
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
