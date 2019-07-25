package com.magic.card.wms.common.exception;

import com.magic.card.wms.common.model.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.magic.card.wms.common.exception
 * 请求检查错误： 请求参数解析等异常
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/25 13:22
 * @since : 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestCheckException extends RuntimeException {
    private Integer errCode;
    private String errMsg;

    /**
     * RequestCheckException Instance
     * @param resultEnum ResultEnum
     * @return
     */
    public static RequestCheckException instance(ResultEnum resultEnum) {
        return new RequestCheckException(resultEnum.getCode(), resultEnum.getMsg());
    }

    /**
     * RequestCheckException Instance
     * @param resultEnum ResultEnum
     * @param msg 提示信息
     * @return
     */
    public static RequestCheckException instance(ResultEnum resultEnum, String msg) {
        return new RequestCheckException(resultEnum.getCode(), msg);
    }

    /**
     * RequestCheckException Instance
     * @param formatResultEnum ResultEnum msg是 StringFormat
     * @param args string format 中的参数值
     * @return
     */
    public static RequestCheckException instanceFormat(ResultEnum formatResultEnum, Object... args) {
        return new RequestCheckException(formatResultEnum.getCode(), String.format(formatResultEnum.getMsg(), args));
    }
}
