package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.po.CommodityStock;

/**
 * com.magic.card.wms.baseset.service
 * 商品整体库存服务接口
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/27 18:16
 * @since : 1.0.0
 */
public interface ICommodityStockService extends IService<CommodityStock> {
    /**
     * 商品库存初始设置
     * @param customerId 客户ID
     * @param commodityCode 商品条形码
     */
    void initSetting(String customerId, String commodityCode);

    /**
     * 设置预警值
     * @param customerCode 客户code
     * @param commodityCode 商品条形码
     * @param deficiencyNum 预警值
     * @param operator 操作人
     */
    void setDeficiency(String customerCode, String commodityCode, Long deficiencyNum, String operator);

    /**
     * 占用库存操作
     */
    void occupyCommodityStock();

    /**
     * 释放库存操作
     */
    void releaseCommodityStock();
}
