package com.magic.card.wms.config;

import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * com.magic.card.wms.config
 * Wms 前端控制器异常同一处理
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/20/020 9:48
 * @since : 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class WmsControllerAdvice {

    /**
     * 处理BusinessException
     * @param businessException
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseData handlerBusinessException(BusinessException businessException) {
        log.error("系统运行产生异常 \n code ： {} ---- msg : {}", businessException.getErrCode(), businessException.getErrMsg());
        return ResponseData.error(businessException.getErrCode(), businessException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseData handlerException(Exception e) {
        ResponseData error = ResponseData.error(500, "系统产生未知异常");
        log.error("系统产生未知异常 ： {}", e);
        error.setData(e.getMessage());
        return error;
    }

}
