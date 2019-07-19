package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.OrderInfoMapper;
import com.magic.card.wms.baseset.model.dto.OrderCommodityDTO;
import com.magic.card.wms.baseset.model.dto.OrderInfoDTO;
import com.magic.card.wms.baseset.model.dto.OrderUpdateDTO;
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
import com.magic.card.wms.common.utils.WebUtil;
import com.magic.card.wms.common.utils.WrapperUtil;
import com.magic.card.wms.report.service.ExpressFeeConfigService;
import lombok.Synchronized;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
    @Autowired
    private ExpressFeeConfigService expressFeeConfigService;
    @Autowired
    private IMailPickingDetailService mailPickingDetailService;
    @Autowired
    private WebUtil webUtil;
    /**
     *
     */
    private static Map<String, String> defaultColumns = Maps.newConcurrentMap();

    static {
        defaultColumns.put("id", "woi.id");
        defaultColumns.put("systemOrderNo", "woi.system_order_no");
        defaultColumns.put("orderNo", "woi.order_no");
        defaultColumns.put("customerCode", "customerCode");
        defaultColumns.put("customerName", "wcbi.customer_name");
        defaultColumns.put("reciptName", "woi.recipt_name");
        defaultColumns.put("reciptPhone", "woi.recipt_phone");
        defaultColumns.put("reciptAddr", "woi.recipt_addr");
        defaultColumns.put("expressKey", "woi.express_key");
        defaultColumns.put("prov", "woi.prov");
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
        Page page = loadGrid.generatorPage();
        EntityWrapper wrapper = new EntityWrapper();
        WrapperUtil.autoSettingSearch(wrapper, defaultColumns, loadGrid.getSearch());
        WrapperUtil.autoSettingOrder(wrapper, defaultColumns, loadGrid.getOrder(), defaultSettingOrder -> defaultSettingOrder.orderBy("woi.create_time", false));

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
     */
    @Override @Transactional
    public void importOrder(OrderInfoDTO orderInfoDTO) {
//        String operator = webUtil.operator();
        String operator = Constants.DEFAULT_USER;

        // 订单取消
        if (StringUtils.equalsIgnoreCase(BillState.order_cancel.getCode(), orderInfoDTO.getBillState())) {
            cancelOrder(orderInfoDTO.getCustomerCode(), orderInfoDTO.getOrderNo(), operator);
        }

        CustomerBaseInfo customerBaseInfo = checkOrder(orderInfoDTO);
        Order order = new Order();
        order.setSystemOrderNo(StringUtils.join(orderInfoDTO.getOrderNo(), orderInfoDTO.getCustomerCode()));
        PoUtil.add(orderInfoDTO, order, operator);

        // 保存单号
        if (this.baseMapper.insert(order) < 1) {
            throw OperationException.addException("订单导入失败");
        }

        // 再次确认商品个数
        if (CollectionUtils.isEmpty(orderInfoDTO.getCommodities())) {
            throw OperationException.addException("订单不存在商品，请确认后再推送");
        }
        processOrderCommodities(orderInfoDTO.getCommodities(), order, customerBaseInfo.getId());
        // 触发生成拣货单 同检验拣货区商品是否充足
        new Thread(() ->
            pickingBillService.triggerGenerator(customerBaseInfo.getCustomerCode(), 1)
        ).start();
    }

    /**
     * 修改订单
     *
     * @param orderUpdateDTO 订单基本信息
     */
    @Override
    public void updateOrder(OrderUpdateDTO orderUpdateDTO) {
        Order order = checkoutOrder(orderUpdateDTO.getSystemOrderNo());
        String operator = Constants.DEFAULT_USER;

        // 取消订单
        if (StringUtils.equalsIgnoreCase(BillState.order_cancel.getCode(), orderUpdateDTO.getBillState())) {
            cancelOrder(order.getCustomerCode(), order.getOrderNo(), operator);
            return;
        }

        if(order.getCreateTime().before(DateTime.now().minusMinutes(15).toDate())) {
            throw OperationException.customException(ResultEnum.order_lock);
        }

        PoUtil.update(orderUpdateDTO, order, operator);
        // 订单基本信息更新
        updateById(order);

        // 商品信息是否更新
        if (CollectionUtils.isNotEmpty(orderUpdateDTO.getCommodities())) {
            // 删除原有的订单商品信息重新添加
            EntityWrapper wrapper = new EntityWrapper();
            wrapper.eq("order_no", orderUpdateDTO.getSystemOrderNo());
            orderCommodityService.delete(wrapper);
            mailPickingDetailService.delete(wrapper);
            // 检出商家信息
            CustomerBaseInfo customerBaseInfo = customerService.checkoutCustomer(order.getCustomerCode());
            processOrderCommodities(orderUpdateDTO.getCommodities(), order, customerBaseInfo.getId());
        }

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

        // 判定是否有满足 20 条数据 且订单已经锁定 15 分钟
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("customer_code", customerCode);
        wrapper.eq("state", StateEnum.normal.getCode());
        // 订单是否已经锁定 15
        wrapper.lt("create_time", DateTime.now().minusMinutes(15).toDate());
        wrapper.orderBy("create_time");

        if (this.selectCount(wrapper) >= executeSize) {
            return this.selectPage(new Page<>(1, 20), wrapper).getRecords();
        }

        return null;
    }

//    /**
//     * 获取订单所有商品的总重量 kg
//     *
//     * @param orderNo
//     * @param customerCode
//     * @return
//     */
//    @Override
//    public BigDecimal orderCommodityWeight(String orderNo, String customerCode) {
//        //统计订单商品重量（包括耗材重量）
//        List<Map> maps = this.baseMapper.orderCommodityWeightMap(orderNo, customerCode);
//        //商品对应所有的耗材 (已g计算重量)
//        BigDecimal weightTotal = BigDecimal.valueOf(0.00);
//
//        for (Map map : maps) {
//            // 购买数量
//            int bayNums = MapUtils.getIntValue(map, "bayNums");
//            BigDecimal singleWeigh = (BigDecimal) map.get("singleWeight");
//            singleWeigh = CommodityUtil.unitConversion_G(singleWeigh, MapUtils.getString(map, "singleWeightUnit"));
//            weightTotal = weightTotal.add(singleWeigh.multiply(BigDecimal.valueOf(bayNums)));
//
//            // 判断当前商品是否存在 消耗品
//            if (ObjectUtils.isEmpty(map.get("useBarCode"))) continue;
//
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
//                BigDecimal useSingleWeigh = (BigDecimal) map.get("useSingleWeight");
//                useSingleWeigh = CommodityUtil.unitConversion_G(useSingleWeigh, MapUtils.getString(map, "useSingleWeightUnit"));
//
//                weightTotal = weightTotal.add( useSingleWeigh.multiply(BigDecimal.valueOf(allUseNums)));
//            }
//        }
//
//        return weightTotal.divide(new BigDecimal("1000"));
//    }

    /**
     * 订单称重对比
     *
     * @param orderNo
     * @param realWight
     * @param operator
     * @return
     */
    @Override
    public void orderWeighContrast(String orderNo, BigDecimal realWight, Boolean ignor, String operator) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("order_no", orderNo).
                eq("state", StateEnum.normal.getCode());
        Order order = selectOne(wrapper);

        if (order == null) {
            throw OperationException.customException(ResultEnum.order_not_exist);
        }

        if (StringUtils.equalsIgnoreCase(BillState.order_cancel.getCode(), order.getBillState())) {
            throw OperationException.customException(ResultEnum.order_cancel);
        }

        MailPicking mailPicking = mailPickingService.selectOne(wrapper);

        // 千克单位比较重量
        BigDecimal differenceValue = realWight.subtract(mailPicking.getPresetWeight()).abs();
        BigDecimal maxDifferenceValue = mailPicking.getPresetWeight().multiply(new BigDecimal("0.10"));
        mailPicking.setRealWeight(realWight);

        if (maxDifferenceValue.compareTo(differenceValue) < 0 && !ignor) {
            throw OperationException.customException(ResultEnum.order_weight_warning);
        }

        PoUtil.update(mailPicking, operator);

        BigDecimal orderExpressFree = expressFeeConfigService.orderExpressFree(orderNo, realWight, mailPicking.getWeightUnit());

        if (orderExpressFree != null) {
            mailPicking.setExpressFee(orderExpressFree);
        }

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
        checkoutOrder(orderNO);
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
        Page page = loadGrid.generatorPage();
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
                eq("order_no", orderNo+customerCode).
                eq("state", StateEnum.normal.getCode());

        if (mailPickingService.selectCount(wrapper) > 0) {
            // 记录订单异常
            OrderException orderException = new OrderException();
            orderException.setOrderNo(orderNo + customerCode);
            PoUtil.add(orderException, operator);
            orderExceptionService.insert(orderException);
        } else {
            processCancelOrder(orderNo + customerCode, operator);
        }

    }

    /**
     * 处理取消订单 操作库存
     * @param orderNo 系统订单号 订单号 + 商家code
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
     * 订单商品处理
     * @param orderCommodityDTOS 订单商品信息
     * @param order              订单信息
     * @param customerId         商家ID
     */
    private void processOrderCommodities(List<OrderCommodityDTO> orderCommodityDTOS, Order order, Long customerId) {
        String operator = Constants.DEFAULT_USER;
        // 订单商品保存
        orderCommodityDTOS.stream().forEach(orderCommodityDTO -> {
            orderCommodityDTO.setOrderNo(order.getSystemOrderNo());
            orderCommodityDTO.setCustomerCode(order.getCustomerCode());
            this.orderCommodityService.importOrderCommodity(orderCommodityDTO, "" + customerId, operator);
        });

        // TODO 根据拆单规则生成包裹快递篮
        // 当前默认是按照一个订单一个包裹
        String mailNo = UUID.randomUUID().toString();
        orderCommodityDTOS.stream().forEach(orderCommodityDTO -> {
            MailPickingDetail mailPickingDetail = new MailPickingDetail();
            mailPickingDetail.setOrderNo(order.getSystemOrderNo());
            mailPickingDetail.setMailNo(mailNo);
            mailPickingDetail.setCommodityCode(orderCommodityDTO.getBarCode());
            mailPickingDetail.setPackageNums(orderCommodityDTO.getNumbers());
            mailPickingDetailService.add(mailPickingDetail);
        });
    }

    /**
     * 导入确认订单检测
     * @param orderInfoDTO
     */
    private CustomerBaseInfo checkOrder(OrderInfoDTO orderInfoDTO) {
        // 检测客户是否存在系统
        CustomerBaseInfo customerBaseInfo = this.customerService.checkoutCustomer(orderInfoDTO.getCustomerCode());

        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("state", StateEnum.normal.getCode());
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
     * 检出系统订单是否存在或是已取消
     * @param orderNo 系统订单 + 商家code
     */
    public Order checkoutOrder(String orderNo) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.ne("state", StateEnum.delete.getCode()).eq("system_order_no", orderNo);
        Order order = selectOne(wrapper);

        if (order == null) {
            throw OperationException.customException(ResultEnum.order_not_exist);
        }

        if (StringUtils.equalsIgnoreCase(order.getBillState(), BillState.order_cancel.getCode())) {
            throw OperationException.customException(ResultEnum.order_cancel);
        }

        return order;
    }


    /**
     * 称重完成释放库存
     * @param order
     * @param operator
     */
    private void releaseStock(Order order, String operator){
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("order_no", order.getSystemOrderNo()).
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
