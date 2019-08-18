package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.OrderCommodityMapper;
import com.magic.card.wms.baseset.model.dto.OrderCommodityDTO;
import com.magic.card.wms.baseset.model.po.OrderCommodity;
import com.magic.card.wms.baseset.service.ICommodityInfoService;
import com.magic.card.wms.baseset.service.ICommodityStockService;
import com.magic.card.wms.baseset.service.IOrderCommodityService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.utils.PoUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    @Autowired
    private ICommodityStockService commodityStockService;

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
        commodityStockService.occupyCommodityStock(commodity.getCustomerCode(), commodity.getBarCode(), commodity.getNumbers().longValue(), operator);
    }

    /**
     * 获取订单所有商品
     *
     * @param orderNo
     * @return
     */
    @Override
    public List<Map> loadOrderCommodityGrid(String orderNo) {
        return baseMapper.loadOrderCommodityGrid(orderNo);
    }

    /**
     * 获取批量订单商品
     *
     * @param orderNos
     * @return
     */
    @Override
    public Map<String, List> loadBatchOrderCommodityGrid(List<String> orderNos) {
        Map<String, List> batchOrderCommodity = Maps.newHashMap();
        List<Map> orderCommodities = baseMapper.loadBatchOrderCommodityGrid(orderNos);

        if (CollectionUtils.isNotEmpty(orderCommodities)) {
            orderCommodities.stream().forEach(orderCommodity -> {
                String orderNo = MapUtils.getString(orderCommodity, "orderNo");

                if (StringUtils.isNotBlank(orderNo)) {
                    List commodities = batchOrderCommodity.get(orderNo);

                    if (CollectionUtils.isEmpty(commodities)) {
                        commodities = Lists.newLinkedList();
                        batchOrderCommodity.put(orderNo, commodities);
                    }

                    commodities.add(orderCommodity);
                }
            });
        }

        return batchOrderCommodity;
    }

    /**
     * 获取订单商品token
     *
     * @param orderNo      订单号
     * @param customerCode 商家CODE
     * @return
     */
    @Override
    public String ruleToken(String orderNo, String customerCode) {
        List<Map> orderCommodities = baseMapper.orderCommodities(StringUtils.split(orderNo, ","), customerCode);

        if (CollectionUtils.isEmpty(orderCommodities)) throw  OperationException.customException(ResultEnum.order_commodity_no_exist);
        TreeMap<String, Integer> commodityNumsMap = Maps.newTreeMap();
        orderCommodities.forEach(orderCommodity -> {
            final String commodityCode = MapUtils.getString(orderCommodity, "barCode");
            Integer bayNums = MapUtils.getInteger(orderCommodity, "numbers");

            if (commodityNumsMap.containsKey(commodityCode)) {
                bayNums += commodityNumsMap.get(commodityCode);
            }

            commodityNumsMap.put(commodityCode, bayNums);
        });
        final StringBuffer ruleToken = new StringBuffer();
        commodityNumsMap.forEach((key, value) -> {

            if (StringUtils.isNotBlank(ruleToken.toString())) {
                ruleToken.append(",");
            }

            ruleToken.append(StringUtils.joinWith("+", key, value));
        });


        return ruleToken.toString();
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
