package com.magic.card.wms.statistic.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StorehouseCountResponseVO extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 1454890930546889153L;

    @ExcelProperty(value = "商家ID", index = 0)
    private String customerId;

    @ExcelProperty(value = "商家名称", index = 1)
    private String customerName;

//    @ExcelProperty(value = "商品ID", index = 2)
    private String commodityId;

    @ExcelProperty(value = "品牌", index = 2)
    private String banner;

    @ExcelProperty(value = "商品名称", index = 3)
    private String commodityName;

    @ExcelProperty(value = "商品条码", index = 4)
    private String skuCode;

    @ExcelProperty(value = "型号", index = 5)
    private String modelNo;

    @ExcelProperty(value = "规格", index = 6)
    private String spec;

    @ExcelProperty(value = "商品库存", index = 7)
    private String availableNums;

    @ExcelProperty(value = "可用库存", index = 8)
    private String emptyAvailableNums;

    @ExcelProperty(value = "占用库存", index = 9)
    private String usedAvailableNums;

    @ExcelProperty(value = "总库存量", index = 10)
    private String totalStoreNums;

//    @ExcelProperty(value = "不可用库存", index = 12)
    private String notUsedStoreNums;

//    @ExcelProperty(value = "出库数量", index = 13)
    private String outStorehouseNum;

//    @ExcelProperty(value = "库位可用天数", index = 14)
    private String availableDay;

//    @ExcelProperty(value = "滞销品提示", index = 15)
    private String unsalableMark;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StorehouseCountResponseDto{");
        sb.append("customerId='").append(customerId).append('\'');
        sb.append(", customerName='").append(customerName).append('\'');
        sb.append(", commodityId='").append(commodityId).append('\'');
        sb.append(", banner='").append(banner).append('\'');
        sb.append(", commodityName='").append(commodityName).append('\'');
        sb.append(", skuCode='").append(skuCode).append('\'');
        sb.append(", modelNo='").append(modelNo).append('\'');
        sb.append(", spec='").append(spec).append('\'');
        sb.append(", availableNums='").append(availableNums).append('\'');
        sb.append(", emptyAvailableNums='").append(emptyAvailableNums).append('\'');
        sb.append(", usedAvailableNums='").append(usedAvailableNums).append('\'');
        sb.append(", totalStoreNums='").append(totalStoreNums).append('\'');
        sb.append(", notUsedStoreNums='").append(notUsedStoreNums).append('\'');
        sb.append(", outStorehouseNum='").append(outStorehouseNum).append('\'');
        sb.append(", availableDay='").append(availableDay).append('\'');
        sb.append(", unsalableMark='").append(unsalableMark).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
