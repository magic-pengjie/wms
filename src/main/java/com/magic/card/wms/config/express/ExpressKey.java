package com.magic.card.wms.config.express;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * com.magic.card.wms.config.express
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/2 17:49
 * @since : 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
public enum ExpressKey {
    EMS("ems", "邮政"),
    SF("sf", "顺风")
     ;
    @Getter
    private String code;
    @Getter
    private String text;

    public static String convertCode(String text) {
        return null;
    }
}
