package com.magic.card.wms.config;

import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.exception.RequestCheckException;
import com.magic.card.wms.common.model.ResponseData;
import io.swagger.models.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sun.misc.Request;

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
     * 处理常规操作异常
     * @param operationException
     * @return
     */
    @ExceptionHandler(OperationException.class)
    public ResponseData handlerOperationException(OperationException operationException) {
        log.error("用户操作产生异常 \n code ： {} ---- msg : {}", operationException.getErrCode(), operationException.getErrMsg());
        return ResponseData.failed(operationException.getErrCode(), operationException.getErrMsg());
    }

    /**
     * 处理BusinessException
     * @param businessException
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseData handlerBusinessException(BusinessException businessException) {
        log.error("系统运行产生异常 \n code ： {} ---- msg : {}", businessException.getErrCode(), businessException.getErrMsg());
        return ResponseData.error(businessException.getErrCode(), businessException.getErrMsg());
    }

    /**
     * 处理RequestCheckException
     * @param requestCheckException
     * @return
     */
    @ExceptionHandler(RequestCheckException.class)
    public ResponseData handlerRequestCheckException(RequestCheckException requestCheckException) {
        log.error("请求检查产生异常 \n code ： {} ---- msg : {}", requestCheckException.getErrCode(), requestCheckException.getErrMsg());
        return ResponseData.error(requestCheckException.getErrCode(), requestCheckException.getErrMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseData handlerException(Exception e) {
        ResponseData error = ResponseData.error(500, "系统产生未知异常");
        log.error("系统产生未知异常 ： {}", e);
        error.setData(e.getMessage());
        return error;
    }

}
