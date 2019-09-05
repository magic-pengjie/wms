package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.dto.order.OrderCommodityDTO;
import com.magic.card.wms.baseset.model.po.OrderCommodity;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取订单所有商品
     * @param orderNo
     * @return
     */
    List<Map> loadOrderCommodityGrid(String orderNo);

    /**
     * 获取批量订单商品
     * @param orderNos
     * @return
     */
    Map<String, List> loadBatchOrderCommodityGrid(List<String> orderNos);

    /**
     * 获取订单商品token
     * @param orderNo 订单号
     * @param customerCode 商家CODE
     * @return
     */
    String ruleToken(String orderNo, String customerCode);
}
