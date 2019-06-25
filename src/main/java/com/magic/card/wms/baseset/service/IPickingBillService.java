package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.po.PickingBill;

/**
 * com.magic.card.wms.baseset.service
 * 拣货单服务接口
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 19:04
 * @since : 1.0.0
 */
public interface IPickingBillService extends IService<PickingBill> {

    /**
     * 触发生成
     * @param customerCode
     */
    void triggerGenerator(String customerCode, Integer executeSize);

    /**
     * 定时任务生成
     */
    void timingGenerator();
}
