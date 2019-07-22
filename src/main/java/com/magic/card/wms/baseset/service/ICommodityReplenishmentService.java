package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.po.CommodityReplenishment;

/**
 * com.magic.card.wms.baseset.service
 * 商品补货 Service 接口
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/12 15:47
 * @since : 1.0.0
 */
public interface ICommodityReplenishmentService extends IService<CommodityReplenishment> {
    /**
     * 处理相关商品零拣区库存不足
     * @param storeCode 零拣库位编号
     * @param stockoutNums 商品缺少量
     */
    void addReplenishmentNotice(String storeCode, Integer stockoutNums);
}
