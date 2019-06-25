package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.dto.OrderCommodityDTO;
import com.magic.card.wms.baseset.model.po.OrderCommodity;

/**
 * com.magic.card.wms.baseset.service
 * 订单商品明细服务接口
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 14:49
 * @since : 1.0.0
 */
public interface IOrderCommodityService extends IService<OrderCommodity> {

    /**
     * 导入订单商品
     * @param orderCommodityDTO
     * @param operator
     */
    void importOrderCommodity(OrderCommodityDTO orderCommodityDTO, String customerId, String operator);
}
