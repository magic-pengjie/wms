package com.magic.card.wms.baseset.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.vo
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/2 15:28
 * @since : 1.0.0
 */
@Data
@EqualsAndHashCode
public class ExcelOrderCommodity extends BaseRowModel implements Serializable {
    private static final long serialVersionUID = 5105166655011083777L;

    /**
     * 订单号
     */
    @ExcelProperty(value = "订单号", index = 0)
    private String orderNo;
    /**
     * 商家编码
     */
    @ExcelProperty(value = "商家编码", index = 1)
    private String customerCode;
    /**
     * 商品条形码
     */
    @ExcelProperty(value = "商品条形码", index = 2)
    private String barCode;

    /**
     * 商品型号
     */
    @ExcelProperty(value = "商品型号", index = 3)
    private String modelNo;
    /**
     * 商品规格
     */
    @ExcelProperty(value = "商品规格", index = 4)
    private String spec;
    /**
     * 商品数量
     */
    @ExcelProperty(value = "商品数量", index = 5)
    private Integer numbers;
}
