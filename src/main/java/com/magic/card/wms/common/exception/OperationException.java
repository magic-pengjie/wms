package com.magic.card.wms.common.exception;

import com.magic.card.wms.common.model.enums.ResultEnum;
import lombok.*;

/**
 * com.magic.card.wms.common.exception
 * 数据操作异常（查询、修改、添加）
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 8:48
 * @since : 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationException extends RuntimeException {

    private Integer errCode;

    private String errMsg;

    /**
     * 查询异常
     */
    public static final OperationException DATA_OPERATION_QUERY = new OperationException(ResultEnum.query_failed);
    /**
     * 添加异常
     */
    public static final OperationException DATA_OPERATION_ADD = new OperationException(ResultEnum.data_add_failed);
    /**
     * 修改异常
     */
    public static final OperationException DATA_OPERATION_UPDATE = new OperationException(ResultEnum.data_update_failed);
    /**
     * 删除异常
     */
    public static final OperationException DATA_OPERATION_DELETE = new OperationException(ResultEnum.data_delete_failed);

    /**
     * ID 异常
     */
    public static final OperationException DATA_ID = new OperationException(ResultEnum.data_update_failed.getCode(), "未提供ID");

    public OperationException(ResultEnum failedResult) {
        super(failedResult.getMsg());
        this.errCode = failedResult.getCode();
        this.errMsg = failedResult.getMsg();
    }

    /**
     * 添加操作异常, 定制错误信息
     * @param errMsg
     * @return
     */
    public static OperationException addException(String errMsg) {
        OperationException exception = new OperationException(ResultEnum.data_add_failed);
        exception.setErrMsg(errMsg);
        return exception;
    }

    /**
     * 更新操作异常，定制错误信息
     * @param errMsg
     * @return
     */
    public static OperationException updateException(String errMsg) {
        OperationException exception = new OperationException(ResultEnum.data_update_failed);
        exception.setErrMsg(errMsg);
        return exception;
    }

    /**
     * 删除操作异常，定制错误信息
     * @param errMsg
     * @return
     */
    public static OperationException deleteException(String errMsg) {
        OperationException exception = new OperationException(ResultEnum.data_delete_failed);
        exception.setErrMsg(errMsg);
        return exception;
    }

    /**
     * 自定义异常处理
     * @param resultEnum
     * @param errMsg
     * @return
     */
     public static OperationException customException(ResultEnum resultEnum, String errMsg) {
         OperationException exception = customException(resultEnum);
         exception.setErrMsg(errMsg);
         return exception;
     }

    /**
     * 自定义异常处理
     * @param resultEnum
     * @return
     */
     public static OperationException customException(ResultEnum resultEnum) {
         return new OperationException(resultEnum);
     }
}
