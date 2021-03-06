package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.po.PickingBill;
import com.magic.card.wms.common.model.LoadGrid;

import java.util.List;
import java.util.Map;

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

    /**
     * 拣货单 -> 生成配货单
     *
     * @param operator 操作人
     * @param allowSize 允许操作次数
     * @param pickNos
     * @return
     */
    List generatorInvoice(String operator, Integer allowSize, String... pickNos);

    /**
     * 配货单检测
     * @param pickNo
     * @param commodityCode
     * @param operator
     * @return 放置位编号
     */
    Integer checkInvoice(String pickNo, String commodityCode, String operator);

    /**
     * 配货单检测结束
     * @param pickNo
     * @param defaultUser
     * @return
     */
    Object checkInvoiceClose(String pickNo, String operator);

    /**
     * 拣货单列表
     * @param loadGrid
     * @return
     */
    LoadGrid loadGrid(LoadGrid loadGrid);

    /**
     * 加载拣货单复检数据
     * @param pickNo
     * @return
     */
    List<Map> pickBillLoadGrid(String pickNo);
}
