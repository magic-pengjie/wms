package com.magic.card.wms.common.annotation;

import java.lang.annotation.*;

/**
 * com.magic.card.wms.common.annotation
 * 请求json参数 @RequestBody
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/25 11:26
 * @since : 1.0.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestJsonParam {
    /**
     * 是否必须出现的参数
     */
    boolean required() default true;

    /**
     * 当value的值或者参数名不匹配时，是否允许解析最外层属性到该对象
     */
    boolean parseAllFields() default true;

    /**
     * 解析时用到的JSON的key
     */
    String value() default "";
}
