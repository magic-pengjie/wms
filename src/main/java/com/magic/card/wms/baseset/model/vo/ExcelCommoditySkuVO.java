package com.magic.card.wms.baseset.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.vo
 * Excel导入商品信息
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/24 13:30
 * @since : 1.0.0
 */
@Data
public class ExcelCommoditySkuVO extends BaseRowModel implements Serializable {
    private static final long serialVersionUID = 3854373109888454012L;
    @ExcelProperty(value = "品牌", index = 0)
    private String banner;
    @ExcelProperty(value = "商品名称", index = 1)
    private String skuName;
    private String skuCode;
    @ExcelProperty(value = "商品条码", index = 3)
    private String barCode;
    @ExcelProperty(value = "单位", index = 4)
    private String singleUnit;
    @ExcelProperty(value = "规格", index = 5)
    private String spec;
    @ExcelProperty(value = "商品型号", index = 7)
    private String modelNo;

    public void setBarCode(String barCode) {
        this.barCode = barCode;
        this.skuCode = barCode;
    }

    public String getSkuCode() {
        return barCode;
    }
}
