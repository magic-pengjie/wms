package com.magic.card.wms.common.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * com.magic.card.wms.common.model.enums
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/31 16:40
 * @since : 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
public enum  CommodityType {
    CARTON("carton", "纸箱"),
    FROTH("froth", "泡沫"),
    PACKING_BAG("packing_bag", "快递袋")
    ;

    /**
     * 编码
     */
    @Getter
    private String code;
    /**
     * 描述
     */
    @Getter
    private String description;

    /**
     * 获取中文
     * @return
     */
    public static String enToch(String en) {
        CommodityType commodityType = null;
        switch (en) {
            case "carton": commodityType = CARTON; break;
            case "froth": commodityType = FROTH; break;
            case "packing_bag":
            default: commodityType = PACKING_BAG;
        }
        return commodityType.getDescription();
    }
}
