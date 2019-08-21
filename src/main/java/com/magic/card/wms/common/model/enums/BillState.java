package com.magic.card.wms.common.model.enums;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * com.magic.card.wms.common.model.enums
 * 单据状态
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/27 14:44
 * @since : 1.0.0
 */
public enum BillState {
    //region 拣货单相关状态
    pick_save("save", "拣货单创建"),
    pick_ing("picking", "拣货单拣货中"),
    pick_finish("finish", "拣货单拣货完成"),
    pick_exception("exception", "拣货单异常"),
    pick_cancel("cancel", "拣货单作废"),
    pick_process_new("new_pick", "新建拣货单"),
    pick_process_lock("lock", "拣货单已锁定"),
    pick_process_unlock("unlock", "拣货单已解除锁订"),
    pick_process_check_close("checkClose", "复检结束"),
    pick_exception_error("error", "错拣货物"),
    pick_exception_omit("omit", "漏拣货物"),
    pick_exception_overflow("overflow", "多拣货物"),
    //endregion
    //region 订单相关状态
    order_save("save", "确认"),
    order_picking("picking", "拣货中"),
    order_packing("packing", "打包中"),
    order_go_out("go_out", "出库"),
    order_finished("finished", "完成"),
    order_cancel("cancel", "取消"),
    //endregion
    //region 包裹处理状态
    package_picking("picking", "拣货中"),
    package_packing("packing", "打包中"),
    package_go_out("go_out", "出库"),
    //endregion
    //region 补货单相关状态
    replenishment_process_create("save", "创建补货单"),
    replenishment_processing("ing", "正在补货"),
    replenishment_process_finished("finished", "补货完成"),
    //endregion
    
    //盘点单据状态
    checker_save("save", "初始化"),
    checker_approving("approving", "审批中"),
    checker_approved("approved", "审批完成"),
    checker_approve_fail("approve_fail", "审批失败"),
    checker_cancel("cancel", "作废"),
    ;
    @Getter
    private final String code;
    @Getter
    private final String desc;

    BillState(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 转换订单状态码
     * @return
     */
    public static String orderCode(String desc) {
        ArrayList<BillState> orderStates = Lists.newArrayList(BillState.order_save, BillState.order_picking, BillState.order_packing, BillState.order_go_out, BillState.order_finished, BillState.order_cancel);
        AtomicReference<String> orderStateCode = new AtomicReference<>("");
        orderStates.forEach(orderState -> {
            if (StringUtils.containsIgnoreCase(desc, orderState.getDesc())) {
                orderStateCode.set(orderState.getCode());
            }
        });
        return orderStateCode.get();
    }

    /**
     * 转换订单状态描述
     * @return
     */
    public static String orderDesc(String code) {
        ArrayList<BillState> orderStates = Lists.newArrayList(BillState.order_save, BillState.order_picking, BillState.order_packing, BillState.order_go_out, BillState.order_finished, BillState.order_cancel);
        AtomicReference<String> orderStateDesc = new AtomicReference<>("");
        orderStates.forEach(orderState -> {
            if (StringUtils.containsIgnoreCase(code, orderState.getCode())) {
                orderStateDesc.set(orderState.getDesc());
            }
        });
        return orderStateDesc.get();
    }
}
