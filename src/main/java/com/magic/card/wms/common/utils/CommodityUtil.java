package com.magic.card.wms.common.utils;

import java.math.BigDecimal;

/**
 * com.magic.card.wms.common.utils
 * 物品处理常用方法
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/25/025 16:01
 * @since : 1.0.0
 */
public class CommodityUtil {

    /**
     * 统一将单位换算成克
     * @return
     */
    public static BigDecimal unitConversion_G(BigDecimal weight, String unit) {
        BigDecimal conversionCardinal = null;
        switch (unit.toLowerCase()) {
            case "t" :
                conversionCardinal = new BigDecimal("1000000");
                break;
            case "kg":
                conversionCardinal = new BigDecimal("1000");
                break;
            default:
                conversionCardinal = new BigDecimal("1");
        }
        return weight.multiply(conversionCardinal);
    }
}
