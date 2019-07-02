package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.po.PickingBillException;
import com.magic.card.wms.common.model.enums.BillState;

/**
 * com.magic.card.wms.baseset.service
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/27 15:36
 * @since : 1.0.0
 */
public interface IPickingBillExceptionService extends IService<PickingBillException> {

    /**
     * 检验配货单时异常处理
     * @param pickNo 拣货单号/拣货单号&&订单号
     * @param commodityCode 商品条形码/商品条形码&&数量
     * @param type 拣货异常类型
     * @param operator 操作人
     */
    void handleException(String pickNo, String commodityCode, BillState type, String operator);
}
