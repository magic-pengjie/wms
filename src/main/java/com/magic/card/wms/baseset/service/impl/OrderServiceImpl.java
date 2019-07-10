package com.magic.card.wms.baseset.service.impl;

import com.alibaba.excel.util.ObjectUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.OrderInfoMapper;
import com.magic.card.wms.baseset.model.dto.OrderInfoDTO;
import com.magic.card.wms.baseset.model.po.*;
import com.magic.card.wms.baseset.service.*;
import com.magic.card.wms.baseset.service.order.OrderExceptionService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.BillState;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.utils.PoUtil;
import com.magic.card.wms.common.utils.CommodityUtil;
import com.magic.card.wms.common.utils.WrapperUtil;
import lombok.Synchronized;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    @Autowired
    private IMailPickingService mailPickingService;
    @Autowired
    private OrderExceptionService orderExceptionService;
    @Autowired
    private ICommodityStockService commodityStockService;
    /**
     *
     */
    private static Map<String, String> defaultColumns = Maps.newConcurrentMap();

    static {
        defaultColumns.put("id", "woi.id");
        defaultColumns.put("orderNo", "woi.order_no");
        defaultColumns.put("customerCode", "customerCode");
        defaultColumns.put("customerName", "wcbi.customer_name");
        defaultColumns.put("reciptName", "woi.recipt_name");
        defaultColumns.put("reciptPhone", "woi.recipt_phone");
        defaultColumns.put("reciptAddr", "woi.recipt_addr");
        defaultColumns.put("expressKey", "woi.express_key");
        defaultColumns.put("isB2b", "woi.is_b2b");
        defaultColumns.put("billState", "woi.bill_state");
        defaultColumns.put("createTime", "woi.create_time");
    }

    /**
     * 系统订单查询
     *
     * @param loadGrid
     * @return
     */
    @Override
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        Page page = loadGrid.page();
        EntityWrapper wrapper = new EntityWrapper();
        WrapperUtil.searchSet(wrapper, defaultColumns, loadGrid.getSearch());

        if (loadGrid.getOrder() == null || loadGrid.getOrder().isEmpty()) {
            wrapper.orderBy("woi.create_time", false);
        } else {
            WrapperUtil.orderSet(wrapper, defaultColumns, loadGrid.getOrder());
        }
        List<Map> grid = baseMapper.loadGrid(page, wrapper);
        Map<String, List> orderCommodities = Maps.newLinkedHashMap();
        // TODO 优化待完善
        if (CollectionUtils.isNotEmpty(grid)) {
            List<String> orderNos = Lists.newLinkedList();
            grid.stream().filter(map -> {
                orderNos.add(map.get("orderNo").toString());
                return true;
            }).collect(Collectors.toList()).stream().forEach(map -> {

                if (CollectionUtils.sizeIsEmpty(orderCommodities)) {
                    orderCommodities.putAll(orderCommodityService.loadBatchOrderCommodityGrid(orderNos));
                }

                if (!CollectionUtils.sizeIsEmpty(orderCommodities)) {
                    map.put(
                            "orderCommodities",
                            orderCommodities.get(map.get("orderNo").toString())
                    );
                }

            });
        }

        loadGrid.finallyResult(page, grid);
        return loadGrid;
    }

    /**
     * 获取订单商品
     *
     * @param orderNo
     */
    @Override
    public List<Map> loadOrderCommodityGrid(String orderNo) {
        return orderCommodityService.loadOrderCommodityGrid(orderNo);
    }

    /**
     * 导入其他系统订单
     *
     * @param orderInfoDTO
     * @param operator
     */
    @Override @Transactional
    public void importOrder(OrderInfoDTO orderInfoDTO, String operator) {
        // 订单取消
        if (StringUtils.equalsIgnoreCase(BillState.order_cancel.getCode(), orderInfoDTO.getBillState())) {
            cancelOrder(orderInfoDTO.getCustomerCode(), orderInfoDTO.getOrderNo(), operator);
        }

        CustomerBaseInfo customerBaseInfo = checkOrder(orderInfoDTO);
        Order order = new Order();
        PoUtil.add(orderInfoDTO, order, operator);

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
        new Thread(() ->
            pickingBillService.triggerGenerator(customerBaseInfo.getCustomerCode(), 20)
        ).start();
    }

    /**
     * 获取满足要求的所有订单
     * 添加同步锁 预防触发、定时并发请求
     * @param customerCode
     * @return
     */
    @Override @Synchronized
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
        BigDecimal weightTotal = BigDecimal.valueOf(0.00);

        for (Map map : maps) {
            // 购买数量
            int bayNums = MapUtils.getIntValue(map, "bayNums");
            BigDecimal singleWeigh = (BigDecimal) map.get("singleWeight");
            singleWeigh = CommodityUtil.unitConversion_G(singleWeigh, MapUtils.getString(map, "singleWeightUnit"));
            weightTotal = weightTotal.add(singleWeigh.multiply(BigDecimal.valueOf(bayNums)));

            // 判断当前商品是否存在 消耗品
            if (ObjectUtils.isEmpty(map.get("useBarCode"))) continue;

            int leftValue = MapUtils.getIntValue(map, "leftValue");
            int rightValue = MapUtils.getIntValue(map, "rightValue");
            // 范围消耗品数量
            int useNums = MapUtils.getIntValue(map, "useNums");

            if (rightValue >= leftValue) {
                int allUseNums = bayNums/rightValue*useNums;

                if (bayNums%rightValue >= leftValue) {
                    allUseNums += useNums;
                }


                BigDecimal useSingleWeigh = (BigDecimal) map.get("useSingleWeight");
                useSingleWeigh = CommodityUtil.unitConversion_G(useSingleWeigh, MapUtils.getString(map, "useSingleWeightUnit"));

                weightTotal = weightTotal.add( useSingleWeigh.multiply(BigDecimal.valueOf(allUseNums)));
            }
        }
//        maps.forEach( map -> {
//
//
//
//            // 购买数量
//            int bayNums = MapUtils.getIntValue(map, "bayNums");
//            int leftValue = MapUtils.getIntValue(map, "leftValue");
//            int rightValue = MapUtils.getIntValue(map, "rightValue");
//            // 范围消耗品数量
//            int useNums = MapUtils.getIntValue(map, "useNums");
//
//            if (rightValue >= leftValue) {
//                int allUseNums = bayNums/rightValue*useNums;
//
//                if (bayNums%rightValue >= leftValue) {
//                    allUseNums += useNums;
//                }
//
//
//                BigDecimal singleWeigh = (BigDecimal) map.get("singleWeight");
//                singleWeigh = CommodityUtil.unitConversion_G(singleWeigh, MapUtils.getString(map, "singleWeightUnit"));
//                BigDecimal useSingleWeigh = (BigDecimal) map.get("useSingleWeight");
//                useSingleWeigh = CommodityUtil.unitConversion_G(useSingleWeigh, MapUtils.getString(map, "useSingleWeightUnit"));
//                weightTotal.set(
//                        weightTotal.get().add(
//                                singleWeigh.multiply(BigDecimal.valueOf(bayNums))
//                                .add(
//                                    useSingleWeigh.multiply(BigDecimal.valueOf(allUseNums))
//                                )
//                        )
//                );
//            }
//
//        });
        return weightTotal.divide(new BigDecimal("1000"));
    }

    /**
     * 订单称重对比
     *
     * @param orderNo
     * @param realWight
     * @param operator
     * @return
     */
    @Override
    public void orderWeighContrast(String orderNo, BigDecimal realWight, String operator) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("order_no", orderNo).
                eq("state", StateEnum.normal.getCode());
        Order order = selectOne(wrapper);

        if (order == null) {
            throw OperationException.customException(ResultEnum.order_not_exit);
        }

        if (StringUtils.equalsIgnoreCase(BillState.order_cancel.getCode(), order.getBillState())) {
            throw OperationException.customException(ResultEnum.order_cancel);
        }

        MailPicking mailPicking = mailPickingService.selectOne(wrapper);

        // 千克单位比较重量
        BigDecimal differenceValue = realWight.subtract(mailPicking.getPresetWeight()).abs();
        BigDecimal maxDifferenceValue = mailPicking.getPresetWeight().multiply(new BigDecimal("0.10"));
        mailPicking.setRealWeight(realWight);

        if (maxDifferenceValue.compareTo(differenceValue) < 0) {
            throw OperationException.customException(ResultEnum.order_weight_warning);
        }

        PoUtil.update(mailPicking, operator);
        mailPickingService.updateById(mailPicking);
        // 释放库存
        releaseStock(order, operator);
        // TODO 推送订单信息到邮政
    }

    /**
     * 订单打包推荐耗材
     *
     * @param orderNO
     */
    @Override
    public List<Map> orderPackage(String orderNO) {
        checkOrder(orderNO);
        List<Map> orderPackage = baseMapper.orderPackage(orderNO);

        if (CollectionUtils.isEmpty(orderPackage)) {
            throw OperationException.customException(ResultEnum.order_package_no_hc);
        }

        return orderPackage;
    }

    /**
     * 称重订单数据加载
     *
     * @param loadGrid
     * @return
     */
    @Override
    public LoadGrid orderWeighLoadGrid(LoadGrid loadGrid) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("state", StateEnum.normal.getCode()).isNotNull("real_weight");
        Page page = loadGrid.page();
        loadGrid.finallyResult(page, mailPickingService.selectMapsPage(page, wrapper).getRecords());

        return loadGrid;
    }

    /**
     * 取消订单操作
     * @param customerCode
     * @param orderNo
     * @param operator
     */
    public void cancelOrder(String customerCode, String orderNo, String operator) {
        // 判断系统中是否存在订单
        Order order = this.checkOrder(customerCode, orderNo);
        // 取消订单
        order.setBillState(BillState.order_cancel.getCode());
        PoUtil.update(order, operator);

        if (this.baseMapper.updateById(order) < 1){
            throw OperationException.addException("订单导入失败");
        }

        // 取消订单是否需要判断当前订单是否已经称重完毕
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("preset_weight", null).
                eq("state", StateEnum.normal.getCode());
        MailPicking mailPicking = mailPickingService.selectOne(wrapper);
        if (selectCount(wrapper) > 0) {
            // 记录订单异常
            OrderException orderException = new OrderException();
            orderException.setOrderNo(orderNo);
            PoUtil.add(orderException, operator);
            orderExceptionService.insert(orderException);
        } else {
            processCancelOrder(orderNo, operator);
        }

    }

    /**
     * 处理取消订单 操作库存
     * @param orderNo
     * @param operator
     */
    private void processCancelOrder(String orderNo, String operator) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("order_no", orderNo).
                eq("state", StateEnum.normal.getCode());
        List<OrderCommodity> list = orderCommodityService.selectList(wrapper);

        if (CollectionUtils.isNotEmpty(list)){
            list.stream().forEach(orderCommodity ->
                    commodityStockService.repealOccupyCommodityStock(
                            orderCommodity.getCustomerCode(),
                            orderCommodity.getBarCode(),
                            orderCommodity.getNumbers().longValue(),
                            operator)
            );
        }
    }

    /**
     * 导入确认订单检测
     * @param orderInfoDTO
     */
    private CustomerBaseInfo checkOrder(OrderInfoDTO orderInfoDTO) {
        // 检测客户是否存在系统
        CustomerBaseInfo customerBaseInfo = this.customerService.checkCustomer(orderInfoDTO.getCustomerCode());

        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("state", Constants.ACTIVITY_STATE);
        wrapper.eq("customer_code", orderInfoDTO.getCustomerCode());
        wrapper.eq("order_no", orderInfoDTO.getOrderNo());

        if (this.selectCount(wrapper) > 0) {
            throw OperationException.addException("您的订单号已经导入系统，请勿重复导入");
        }

        return customerBaseInfo;
    }

    /**
     * 导入取消订单时检测
     * @param customerCode
     * @param orderNo
     */
    private Order checkOrder(String customerCode, String orderNo) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.ne("state", StateEnum.delete.getCode()).
                eq("order_no", orderNo).
                eq("customer_code", customerCode);

        Order order = this.selectOne(wrapper);

        if (order == null) {
            throw OperationException.addException("您客户的订单（取消），并不存在请核实后再重新导入");
        }

        if (StringUtils.equalsAnyIgnoreCase(BillState.order_cancel.getCode(), order.getBillState())) {
            throw OperationException.addException("您客户的订单已近被取消请勿重复导入，如有疑问请核实后再重新导入");
        }

        return order;
    }

    /**
     * 订单是否存在或是已取消
     * @param orderNo
     */
    private void checkOrder(String orderNo) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.ne("state", StateEnum.delete.getCode()).eq("order_no", orderNo);
        Order order = selectOne(wrapper);

        if (order == null) {
            throw OperationException.customException(ResultEnum.order_not_exit);
        }

        if (StringUtils.equalsIgnoreCase(order.getBillState(), BillState.order_cancel.getCode())) {
            throw OperationException.customException(ResultEnum.order_cancel);
        }
    }

    /**
     * 称重完成释放库存
     * @param order
     * @param operator
     */
    private void releaseStock(Order order, String operator){
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("order_no", order.getOrderNo()).
                eq("customer_no", order.getCustomerCode()).
                eq("state", StateEnum.normal.getCode());
        List<OrderCommodity> list = orderCommodityService.selectList(wrapper);

        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(orderCommodity ->
                    commodityStockService.releaseCommodityStock(
                            orderCommodity.getCustomerCode(),
                            orderCommodity.getBarCode(),
                            orderCommodity.getNumbers().longValue(),
                            operator)
            );
        }

    }
}
