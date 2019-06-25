package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.dto.OrderInfoDTO;
import com.magic.card.wms.baseset.model.po.Order;
import com.magic.card.wms.common.model.LoadGrid;

import java.math.BigDecimal;
import java.util.List;

/**
 * com.magic.card.wms.baseset.service
 * 订单信息服务接口
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 14:46
 * @since : 1.0.0
 */
public interface IOrderService extends IService<Order> {
    /**
     * 系统订单查询
     * @param loadGrid
     * @return
     */
    LoadGrid loadGrid(LoadGrid loadGrid);

    /**
     * 导入其他系统订单
     * @param orderInfoDTO
     * @param operator
     */
    void importOrder(OrderInfoDTO orderInfoDTO, String operator);

    /**
     * 获取满足要求的所有订单
     * @param customerCode
     * @return
     */
    List<Order> obtainOrderList(String customerCode, Integer executeSize);

    /**
     * 获取订单所有商品的总重量
     * @param orderNo
     * @param customerCode
     * @return
     */
    BigDecimal orderCommodityWeight(String orderNo, String customerCode);
}
