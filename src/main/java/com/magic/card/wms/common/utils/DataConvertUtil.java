package com.magic.card.wms.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * com.magic.card.wms.common.utils
 * 数据装换
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/2 17:19
 * @since : 1.0.0
 */
public class DataConvertUtil {
    /**
     * 是否值
     * @param isValue
     * @return
     */
    public static Integer isValue(String isValue) {

        if (StringUtils.isNotBlank(isValue) &&
                StringUtils.equalsAnyIgnoreCase(isValue, "是", "Y", "true")) {
            return  1;
        }

        return 0;
    }

    public static String isValue(int isValue) {

        if (isValue == 1) return "是";

        return "否";
    }
}
