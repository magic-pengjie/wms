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
    void timingGenerator(String operator, Integer executeSize);

    /**
     * 执行拣货单生成
     */
    void executorGenerator();

    /**
     * 拣货单 -> 生成配货单
     *
     * @param pickNos
     * @return
     */
    List generatorInvoice(List<String> pickNos);

    /**
     * 配货单检测
     * @param pickNo
     * @param commodityCode
     * @param operator
     * @return 放置位编号
     */
    Integer checkInvoice(String pickNo, String commodityCode);

    /**
     * 配货单检测结束
     * @param pickNo 拣货单号
     * @return
     */
    Object checkInvoiceClose(String pickNo);

    /**
     * 配货单检查完毕
     * @param pickNo
     */
    void checkInvoiceFinished(String pickNo, String pickOperator);

    /**
     * 配货单漏检
     * @param omitInfo
     * @param <T>
     */
    <T> void checkInvoiceOmit(T omitInfo);

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

    /**
     * 拣货单加解锁处理
     * @param pickNo 拣货单号
     * @param lock 是否锁定
     */
    void pickLockProcess(String pickNo, Boolean lock);

    /**
     * 批量作废
     * @param pickNos 拣货单号
     */
    void batchCancel(List<String> pickNos);

    /**
     * 拣货单作废处理
     * @param pickNo 拣货单号
     */
    void cancel(String pickNo);

    /**
     * 打印配货单
     * @param pickNos
     */
    void printInvoices(List<String> pickNos);
}
