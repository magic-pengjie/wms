package com.magic.card.wms.common.model.enums;

import lombok.Getter;

/**
 * com.magic.card.wms.common.model.enums
 * 单据状态
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/27 14:44
 * @since : 1.0.0
 */
public enum BillState {
    pick_save("save", "拣货单创建"),
    pick_ing("picking", "拣货单拣货中"),
    pick_finish("finish", "拣货单拣货完成"),
    pick_exception("exception", "拣货单异常"),
    pick_cancel("cancel", "拣货单取消"),
    pick_process_lock("lock", "拣货单已锁定"),
    pick_process_unlock("unlock", "拣货单已解除锁订"),
    pick_process_check_close("checkClose", "复检结束"),
    pick_exception_error("error", "错拣货物"),
    pick_exception_omit("omit", "漏拣货物"),
    pick_exception_overflow("overflow", "多拣货物"),

    order_save("save", "订单导入创建"),
    order_confirm("confirm", "订单确认"),
    order_cancel("cancel", "订单取消或退订"),
    
    //盘点单据状态
    checker_save("save", "初始化"),
    checker_approving("approving", "审批中"),
    checker_approved("approved", "审批完成"),
    checker_approve_fail("approve_fail", "审批失败"),
    checker_cancel("cancel", "作废"),
    ;
    @Getter
    private String code;
    @Getter
    private String desc;

    BillState(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
