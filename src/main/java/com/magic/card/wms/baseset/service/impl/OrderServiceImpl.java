package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.mapper.OrderInfoMapper;
import com.magic.card.wms.baseset.model.dto.OrderInfoDTO;
import com.magic.card.wms.baseset.model.po.CustomerBaseInfo;
import com.magic.card.wms.baseset.model.po.Order;
import com.magic.card.wms.baseset.service.ICustomerBaseInfoService;
import com.magic.card.wms.baseset.service.IOrderCommodityService;
import com.magic.card.wms.baseset.service.IOrderService;
import com.magic.card.wms.baseset.service.IPickingBillService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.model.po.PoUtils;
import com.magic.card.wms.common.utils.CommodityUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * com.magic.card.wms.baseset.service.impl
 * 订单信息服务接口具体实现
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 14:47
 * @since : 1.0.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderInfoMapper, Order> implements IOrderService {
    @Autowired
    private ICustomerBaseInfoService customerService;
    @Autowired
    private IOrderCommodityService orderCommodityService;
    @Autowired
    private IPickingBillService pickingBillService;


    /**
     * 系统订单查询
     *
     * @param loadGrid
     * @return
     */
    @Override
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        return null;
    }

    /**
     * 导入其他系统订单
     *
     * @param orderInfoDTO
     * @param operator
     */
    @Override @Transactional
    public void importOrder(OrderInfoDTO orderInfoDTO, String operator) {
        CustomerBaseInfo customerBaseInfo = checkOrder(orderInfoDTO);
        Order order = new Order();
        PoUtils.add(orderInfoDTO, order, operator);

        // 保存单号
        if (this.baseMapper.insert(order) < 1) {
            throw OperationException.addException("订单导入失败");
        }

        // 再次确认商品个数
        if (CollectionUtils.isEmpty(orderInfoDTO.getCommodities())) {
            throw OperationException.addException("订单不存在商品，请确认后再推送");
        }

        // 订单商品保存
        orderInfoDTO.getCommodities().stream().forEach(orderCommodityDTO -> {
            orderCommodityDTO.setOrderNo(orderInfoDTO.getOrderNo());
            orderCommodityDTO.setCustomerCode(orderInfoDTO.getCustomerCode());
            this.orderCommodityService.importOrderCommodity(orderCommodityDTO, "" + customerBaseInfo.getId(), operator);
        });

        // 触发生成拣货单
        new Thread(() -> {
            pickingBillService.triggerGenerator(customerBaseInfo.getCustomerCode());
        }).start();
    }

    /**
     * 获取满足要求的所有订单
     *
     * @param customerCode
     * @return
     */
    @Override
    public List<Order> obtainOrderList(String customerCode, Integer executeSize) {

        if (StringUtils.isBlank(customerCode)) return null;

        // 判定是否有满足 20 条数据
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("customer_code", customerCode);
        wrapper.eq("state", StateEnum.normal.getCode());
        wrapper.orderBy("create_time");

        if (this.selectCount(wrapper) >= executeSize) {
            return this.selectPage(new Page<>(1, 20), wrapper).getRecords();
        }

        return null;
    }

    /**
     * 获取订单所有商品的总重量 kg
     *
     * @param orderNo
     * @param customerCode
     * @return
     */
    @Override
    public BigDecimal orderCommodityWeight(String orderNo, String customerCode) {
        //统计订单商品重量（包括耗材重量）
        List<Map> maps = this.baseMapper.orderCommodityWeightMap(orderNo, customerCode);
        //商品对应所有的耗材 (已g计算重量)
        AtomicReference<BigDecimal> weightTotal = new AtomicReference<>(BigDecimal.valueOf(0.00));
        maps.stream().forEach( map -> {
            // 购买数量
            int bayNums = MapUtils.getIntValue(map, "bayNums");
            int leftVale = MapUtils.getIntValue(map, "leftVale");
            int rightVale = MapUtils.getIntValue(map, "rightVale");
            // 范围消耗品数量
            int useNums = MapUtils.getIntValue(map, "useNums");

            if (rightVale >= leftVale) {
                int allUseNums = bayNums/rightVale*useNums;

                if (bayNums%rightVale >= leftVale) {
                    allUseNums += useNums;
                }


                BigDecimal singleWeigh = (BigDecimal) map.get("singleWeigh");
                singleWeigh = CommodityUtil.unitConversion_G(singleWeigh, MapUtils.getString(map, "singleWeighUnit"));
                BigDecimal useSingleWeigh = (BigDecimal) map.get("useSingleWeigh");
                useSingleWeigh = CommodityUtil.unitConversion_G(useSingleWeigh, MapUtils.getString(map, "useSingleWeighUnit"));
                weightTotal.set(
                        weightTotal.get().add(
                                singleWeigh.multiply(BigDecimal.valueOf(bayNums))
                                .add(
                                    useSingleWeigh.multiply(BigDecimal.valueOf(allUseNums))
                                )
                        )
                );
            }

        });
        return weightTotal.get().divide(new BigDecimal("1000"));
    }

    /**
     * 检测订单
     * @param orderInfoDTO
     */
    private CustomerBaseInfo checkOrder(OrderInfoDTO orderInfoDTO) {
        // 检测客户是否存在系统
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("state", Constants.ACTIVITY_STATE);
        wrapper.eq("customer_code", orderInfoDTO.getCustomerCode());

        CustomerBaseInfo customerBaseInfo = this.customerService.selectOne(wrapper);

        if (customerBaseInfo == null) {
            throw OperationException.addException("未知商家， 请提供明确的CODE");
        }

        wrapper.eq("order_no", orderInfoDTO.getOrderNo());

        if (this.selectCount(wrapper) > 0) {
            throw OperationException.addException("您的订单号已经导入系统，请勿重复导入");
        }

        return customerBaseInfo;
    }
}
