package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.mapper.OrderCommodityMapper;
import com.magic.card.wms.baseset.model.dto.OrderCommodityDTO;
import com.magic.card.wms.baseset.model.po.OrderCommodity;
import com.magic.card.wms.baseset.service.ICommodityInfoService;
import com.magic.card.wms.baseset.service.IOrderCommodityService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.utils.PoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * com.magic.card.wms.baseset.service.impl
 * 订单商品明细服务接口实现
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 14:50
 * @since : 1.0.0
 */
@Service
public class OrderCommodityServiceImpl extends ServiceImpl<OrderCommodityMapper, OrderCommodity> implements IOrderCommodityService {
    @Autowired
    private ICommodityInfoService commodityInfoService;

    /**
     * 导入订单商品
     * @param orderCommodityDTO
     * @param operator
     */
    @Override @Transactional
    public void importOrderCommodity(OrderCommodityDTO orderCommodityDTO, String customerId, String operator) {
        checkOrderCommodity(orderCommodityDTO, customerId);
        OrderCommodity commodity = new OrderCommodity();
        PoUtil.add(orderCommodityDTO, commodity, operator);

        if (this.baseMapper.insert(commodity) < 1) {
            throw OperationException.addException("订单商品导入失败");
        }

        // TODO 执行库存占用 customerCode commodityCode occupyNum

    }

    /**
     * 检测导入商品
     * @param orderCommodityDTO
     */
    private void checkOrderCommodity(OrderCommodityDTO orderCommodityDTO, String customerId) {
        //region 检测当前商家是否代理该产品
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("customer_id", customerId);
        wrapper.eq("commodity_code", orderCommodityDTO.getBarCode());

        if (this.commodityInfoService.selectCount(wrapper) < 1) {
            throw OperationException.addException("当前系统中该客户并未关联" + orderCommodityDTO.getBarCode() + "商品");
        }
        //endregion
    }
}
