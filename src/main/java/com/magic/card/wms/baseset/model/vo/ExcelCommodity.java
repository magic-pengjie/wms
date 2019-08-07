package com.magic.card.wms.baseset.model.vo;

import lombok.Data;

@Data
public class ExcelCommodity {
    private String barCode;
    /**
     * 商品型号
     */
    private String modelNo;
    /**
     * 商品规格
     */
    private String spec;
    /**
     * 商品数量
     */
    private Integer numbers;
}
