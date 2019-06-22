package com.magic.card.wms.common.utils;

import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * com.magic.card.wms.common.utils
 *  WrapperUtil 动态组装 搜索 以及 排序
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/21/021 18:20
 * @since : 1.0.0
 */
public class WrapperUtil {

    /**
     * 动态添加 搜索条件
     * @param wrapper
     * @param columns
     * @param search
     */
    public static void searchSet(Wrapper wrapper, Map<String, String> columns, Map<String, Object> search) {
        wrapper.eq("1", 1);

        if (MapUtils.isNotEmpty(search)) {
            search.forEach((key, value) -> {

                if (value != null && value instanceof String) {

                    if (StringUtils.isNotBlank(columns.get(key)))
                        wrapper.like(columns.get(key), value.toString());

                }

            });

        }

    }

    /**
     * 动态拼接 排序语句
     * @param wrapper
     * @param columns
     * @param order
     */
    public static void orderSet(Wrapper wrapper, Map<String, String> columns, Map<String, String> order) {

        if (MapUtils.isNotEmpty(order)) {
            order.forEach((key, value) -> {

                if (value != null) {

                    if (StringUtils.equalsAnyIgnoreCase(value, "asc", "desc"))
                        wrapper.orderBy(columns.get(key), value.equalsIgnoreCase("asc"));

                }

            });

        }

    }
}
