package com.magic.card.wms.common.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;

/**
 * com.magic.card.wms.common.utils
 * 自动生成Code
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/26 14:02
 * @since : 1.0.0
 */
public class GeneratorCodeUtil extends RandomStringUtils {

    /**
     * 生成时间戳格式的 yyyyMMddHHmmss
     * @return
     */
    public static String dateTime() {
        return DateTime.now().toString("yyyyMMddHHmmss");
    }

    /**
     * 生成时间戳格式 yyyyMMddHHmmss + 加上随机数
     * @param randomSize 随机数的长度
     * @return
     */
    public static String dataTime(int randomSize) {
        return dateTime() + randomNumeric(randomSize);
    }
}
