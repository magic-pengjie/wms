package com.magic.card.wms.common.utils;

import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.function.Consumer;

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
    public static void autoSettingSearch(Wrapper wrapper, Map<String, String> columns, Map<String, Object> search) {
        autoSettingSearch(wrapper, columns, search, null);
    }

    /**
     * 动态拼接 排序语句
     * @param wrapper
     * @param columns
     * @param order
     */
    public static void autoSettingOrder(Wrapper wrapper, Map<String, String> columns, Map<String, String> order) {
        autoSettingOrder(wrapper, columns, order, null);
    }

    /**
     * 自动设置排序字段
     * @param wrapper Wrapper
     * @param columns 列定义
     * @param order 排序信息
     * @param defaultSetOrder 设置默认的排序信息
     */
    public static void autoSettingOrder(Wrapper wrapper, Map<String, String> columns, Map<String, String> order, Consumer<Wrapper> defaultSetOrder) {
        if (MapUtils.isNotEmpty(order)) {
            order.forEach((key, value) -> {

                if (value != null) {

                    if (StringUtils.equalsAnyIgnoreCase(value, "asc", "desc"))
                        wrapper.orderBy(columns.get(key), value.equalsIgnoreCase("asc"));

                }

            });

        } else {

            if (defaultSetOrder != null) {
                defaultSetOrder.accept(wrapper);
            }

        }
    }

    /**
     * 自动设置搜索条件
     * @param wrapper
     * @param columns
     * @param search
     * @param defaultSetSearch
     */
    public static void autoSettingSearch(Wrapper wrapper, Map<String, String> columns, Map<String, Object> search, Consumer<Wrapper> defaultSetSearch) {
        wrapper.eq("1", 1);

        if (MapUtils.isNotEmpty(search)) {
            search.forEach((key, value) -> {

                if (value != null && value instanceof String) {

                    if (StringUtils.isNotBlank(columns.get(key))) {
                        wrapper.like(columns.get(key), value.toString());
                    }

                }

            });
        } else {

            if (defaultSetSearch != null) {
                defaultSetSearch.accept(wrapper);
            }

        }
    }
}
