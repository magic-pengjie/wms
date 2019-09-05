package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.po.ExpressNumber;
import com.magic.card.wms.common.model.LoadGrid;

import java.util.List;

/**
 * com.magic.card.wms.baseset.service
 * 快递单号管理Service
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/9/4 9:52
 * @since : 1.0.0
 */
public interface IExpressNumberService extends IService<ExpressNumber> {
    /**
     * 快递单号分页数据
     * @param loadGrid 查询排序条件
     */
    void loadGrid(LoadGrid loadGrid);

    /**
     * 获取快递单号
     * @param expressKey 快递标识
     * @return
     */
    String getExpressNumber(String expressKey);

    /**
     * 批量添加快递单号
     * @param expressKey 快递标识
     * @param startNumber 开始单号
     * @param endNumber 结束单号
     */
    void batchAddExpressNumber(String expressKey, Long startNumber, Long endNumber);

    /**
     * 批量修改快递单使用次数
     * @param expressKey 快递标识
     * @param expressNumbers 多个快递单号
     */
    void batchEditUseTimes(String expressKey, List expressNumbers);

    /**
     * 快递单号可用量
     * @return
     */
    int expressNumberAvailable();
}
