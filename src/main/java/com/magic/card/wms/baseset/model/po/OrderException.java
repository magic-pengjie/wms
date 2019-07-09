package com.magic.card.wms.baseset.model.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.po
 * 订单导入异常记录
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/1 14:41
 * @since : 1.0.0
 */
@Data
@TableName("wms_order_exception")
@EqualsAndHashCode
public class OrderException extends BasePo implements Serializable {
    private static final long serialVersionUID = 5946484238131189378L;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 异常类型(''order_cancel_weigh_finished'': 订单取消但称重完成)'
     */
    private String exceptionType;
    /**
     * 流程状态(保存:save确认:confirm )
     */
    private String processState;
}
