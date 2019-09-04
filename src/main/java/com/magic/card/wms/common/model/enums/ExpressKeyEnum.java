package com.magic.card.wms.common.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * com.magic.card.wms.common.model.enums
 * 快递标识
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/9/4 10:45
 * @since : 1.0.0
 */
@AllArgsConstructor
public enum ExpressKeyEnum {
    YZPK("YZPK", "邮政普通标准快递");
    /**
     * 编码
     */
    @Getter
    private final String code;
    /**
     * 描述
     */
    @Getter
    private final String description;

}
