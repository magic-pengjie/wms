package com.magic.card.wms.baseset.service.impl;

import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.magic.card.wms.baseset.mapper.OrderInfoMapper;
import com.magic.card.wms.baseset.model.dto.OrderCommodityDTO;
import com.magic.card.wms.baseset.model.dto.OrderInfoDTO;
import com.magic.card.wms.baseset.model.dto.OrderUpdateDTO;
import com.magic.card.wms.baseset.model.po.*;
import com.magic.card.wms.baseset.model.vo.ExcelCommodity;
import com.magic.card.wms.baseset.model.vo.ExcelOrderCommodity;
import com.magic.card.wms.baseset.model.vo.ExcelOrderImport;
import com.magic.card.wms.baseset.service.*;
import com.magic.card.wms.baseset.service.order.OrderExceptionService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.BillState;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.utils.*;
import com.magic.card.wms.report.service.ExpressFeeConfigService;
import lombok.Synchronized;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
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
    private ISplitPackageRuleService splitPackageRuleService;
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
        defaultColumns.put("customerCode", "woi.customer_code");
        defaultColumns.put("customerName", "wcbi.customer_name");
        defaultColumns.put("reciptName", "woi.recipt_name");
        defaultColumns.put("reciptPhone", "woi.recipt_phone");
        defaultColumns.put("reciptAddr", "woi.recipt_addr");
        defaultColumns.put("expressKey", "woi.express_key");
        defaultColumns.put("isLock", "woi.is_lock");
        defaultColumns.put("isMatched", "woi.is_matched");
        defaultColumns.put("prov", "woi.prov");
        defaultColumns.put("isB2b", "woi.is_b2b");
        defaultColumns.put("billState", "woi.bill_state");
        defaultColumns.put("createTime", "woi.create_time");
    }

    /**
     * 自动锁定订单
     */
    @Override @Transactional
    public void autoLockOrder() {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("is_lock", 0).
                ne("state", StateEnum.delete.getCode()).
                lt("create_time", DateTime.now().toDate());
        updateForSet("is_lock = 1", wrapper);
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

        if (CollectionUtils.isNotEmpty(grid)) {
            List<String> orderNos = Lists.newLinkedList();
            grid.stream().filter(map -> {
                orderNos.add(map.get("systemOrderNo").toString());
                return true;
            }).collect(Collectors.toList()).stream().forEach(map -> {

                if (CollectionUtils.sizeIsEmpty(orderCommodities)) {
                    orderCommodities.putAll(orderCommodityService.loadBatchOrderCommodityGrid(orderNos));
                }

                if (!CollectionUtils.sizeIsEmpty(orderCommodities)) {
                    map.put(
                            "orderCommodities",
                            orderCommodities.get(map.get("systemOrderNo").toString())
                    );
                }

            });
        }

        loadGrid.finallyResult(page, grid);
        return loadGrid;
    }


    /**
     * 获取订单详情（商品 以及 对应的包裹信息）
     * @param orderNo 系统订单号
     * @param customerCode 商家Code
     * @param systemOrderNo 系统订单号
     * @return
     */
    @Override
    public Map loadDetails(String orderNo, String customerCode, String systemOrderNo) {
        HashMap<String, Object> orderDetailsMap = Maps.newHashMap();
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("system_order_no", systemOrderNo);
        orderDetailsMap.put("orderInfo", checkOrder(customerCode, orderNo));

        orderDetailsMap.put("orderDetails", baseMapper.loadCommodity(StringUtils.split(orderNo, ","), customerCode));
        List<Map> orderMails = mailPickingService.loadOrderPackage(systemOrderNo);

        if (CollectionUtils.isNotEmpty(orderMails)) {
            List<Map> orderMailDetails = baseMapper.loadMailDetails(systemOrderNo);
            HashMap<String, List<Map>> mailDetailMap = Maps.newHashMap();
            orderMailDetails.forEach(mailDetails -> {
                final String mailNo = MapUtils.getString(mailDetails, "mailNo");

                if (mailDetailMap.containsKey(mailNo)) {
                    mailDetailMap.get(mailNo).add(mailDetails);
                } else {
                    ArrayList<Map> mailCommodities = Lists.newArrayList();
                    mailCommodities.add(mailDetails);
                    mailDetailMap.put(mailNo, mailCommodities);

                }
            });
            orderMails.forEach( orderMail -> {
                final String mailNo = MapUtils.getString(orderMail, "mailNo");
                orderMail.put("mailCommodities", mailDetailMap.get(mailNo));

            });
            orderDetailsMap.put("orderMails", orderMails);

        }

        return orderDetailsMap;
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
     * @param orderInfoDTO 订单信息
     */
    @Override @Transactional
    public void importOrder(OrderInfoDTO orderInfoDTO) {
        String operator = webUtil.operator();

        // 订单取消
        if (StringUtils.equalsIgnoreCase(BillState.order_cancel.getCode(), orderInfoDTO.getBillState())) {
            cancelOrder(orderInfoDTO.getCustomerCode(), orderInfoDTO.getOrderNo(), operator);
            return;
        }

        CustomerBaseInfo customerBaseInfo = checkOrder(orderInfoDTO);
        orderHandler(orderInfoDTO.getOrderNo() + orderInfoDTO.getCustomerCode(), orderInfoDTO, customerBaseInfo, false, operator);

        // 触发生成拣货单 同检验拣货区商品是否充足
        ThreadPool.excutor(() -> pickingBillService.triggerGenerator(customerBaseInfo.getCustomerCode(), 15));
    }

    /**
     * 修改订单
     *
     * @param orderUpdateDTO 订单基本信息
     */
    @Override
    public void updateOrder(OrderUpdateDTO orderUpdateDTO) {
        Order order = checkoutOrder(orderUpdateDTO.getSystemOrderNo());
        String operator = webUtil.operator();

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
            processOrderCommodities(orderUpdateDTO.getCommodities(), order, customerBaseInfo.getId(), false, operator);
        }

    }

    /**
     * 订单数据处理
     * @param systemSystemCode 系统订单号
     * @param orderDTO 订单数据
     * @param customer 客户信息
     * @param supportMerge 是否支持合单操作
     * @param operator 操作人
     */
    private void orderHandler(String systemSystemCode, OrderInfoDTO orderDTO, CustomerBaseInfo customer, Boolean supportMerge, final String operator) {
        Order order = new Order();
        order.setSystemOrderNo(systemSystemCode);
        PoUtil.add(orderDTO, order, operator);
        // 保存单号
        if (this.baseMapper.insert(order) < 1) {
            throw OperationException.addException("订单导入失败");
        }

        // 再次确认商品个数
        if (CollectionUtils.isEmpty(orderDTO.getCommodities())) {
            throw OperationException.addException("订单不存在商品，请确认后再推送");
        }

        processOrderCommodities(orderDTO.getCommodities(), order, customer.getId(), supportMerge, operator);
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
        wrapper.eq("wmpi.state", StateEnum.normal.getCode()).ne("woi.bill_state", BillState.order_cancel.getCode());
        Page page = loadGrid.generatorPage();
//        mailPickingService.loadGrid(page, wrapper);
//        loadGrid.finallyResult(page, mailPickingService.selectMapsPage(page, wrapper).getRecords());
        loadGrid.finallyResult(page, baseMapper.loadPackageGrid(page, wrapper));

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
                eq("order_no", order.getSystemOrderNo()).
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
     * @param supportMerge 是否支持合单
     * @param operator 操作人
     */
    private void processOrderCommodities(List<OrderCommodityDTO> orderCommodityDTOS, Order order, Long customerId, Boolean supportMerge, final String operator) {
        // 订单商品保存
        orderCommodityDTOS.stream().forEach(orderCommodityDTO -> {
            orderCommodityDTO.setOrderNo(order.getOrderNo());
            orderCommodityDTO.setCustomerCode(order.getCustomerCode());
            this.orderCommodityService.importOrderCommodity(orderCommodityDTO, "" + customerId, operator);
        });

        //TODO 目前不会处理/批量订单以及/B2B订单
        if (order.getIsB2b() == 1 || order.getIsBatch() == 1) return;

        if (supportMerge) return;

        List<List<OrderCommodityDTO>> orderSplitPackages = splitPackageRuleService.orderSplitPackages(orderCommodityDTOS);

        if (CollectionUtils.isNotEmpty(orderSplitPackages)) {
            order.setIsMatched(1);
            orderSplitPackages.forEach(packageCommodities-> {
                String mailNo = UUID.randomUUID().toString();
                packageCommodities.stream().forEach(orderCommodityDTO -> {
                    MailPickingDetail mailPickingDetail = new MailPickingDetail();
                    mailPickingDetail.setOrderNo(order.getSystemOrderNo());
                    mailPickingDetail.setMailNo(mailNo);
                    mailPickingDetail.setCommodityCode(orderCommodityDTO.getBarCode());
                    mailPickingDetail.setPackageNums(orderCommodityDTO.getNumbers());
                    mailPickingDetailService.add(mailPickingDetail);
                });
            });
            updateById(order);
        }

    }

    /**
     * 导入确认订单检测
     * @param orderInfoDTO
     */
    private CustomerBaseInfo checkOrder(OrderInfoDTO orderInfoDTO) {
        // 检测客户是否存在系统
        CustomerBaseInfo customerBaseInfo = this.customerService.checkoutCustomer(orderInfoDTO.getCustomerCode());

        EntityWrapper wrapper = new EntityWrapper();
        wrapper.ne("state", StateEnum.delete);
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
     * 获取订单 key -> value Map
     *
     * @param systemOrderNo
     * @return
     */
    @Override
    public Map<String, Order> ordersMap(List<String> systemOrderNo) {
        EntityWrapper<Order> orderEntityWrapper = new EntityWrapper<>();
        orderEntityWrapper.in("system_order_no", systemOrderNo).ne("state", StateEnum.delete.getCode());
        List<Order> orders = selectList(orderEntityWrapper);
        Map<String, Order> ordersMap = Maps.newHashMap();

        if (CollectionUtils.isNotEmpty(orders)) {
            orders.forEach(order -> ordersMap.put(order.getSystemOrderNo(), order));
        }

        return ordersMap;
    }

    /**
     * EXCEL 导入订单
     *
     * @param excelOrderFile
     */
    @Override @Transactional
    public void excelNewImport(MultipartFile excelOrderFile) throws IOException {
        if (excelOrderFile == null) return;
        final String operator = webUtil.operator();

        // 验证文件后缀
        EasyExcelUtil.validatorFileSuffix(excelOrderFile.getOriginalFilename());
        // 解析EXCEL文件,获取订单数据
        List<ExcelOrderImport> excelOrders = EasyExcelUtil.readExcel(excelOrderFile.getInputStream(), ExcelOrderImport.class, 1, 1);

        if (CollectionUtils.isEmpty(excelOrders)) return;

        LinkedList<OrderInfoDTO> orders = Lists.newLinkedList();
        // 不同商品不同条码
        Map<String, Map<String, ExcelCommodity>> excelOrderCommodityMap = Maps.newHashMap();
        HashSet<String> orderNos = Sets.newHashSet();

        for (int i = 0; i < excelOrders.size(); i++) {
            final ExcelOrderImport excelOrder = excelOrders.get(i);
            final String orderNo = excelOrder.getOrderNo();
            final String commodityCode = excelOrder.getBarCode();
            orderNos.add(excelOrder.getOrderNo());

            if (StringUtils.isAnyBlank(orderNo, excelOrder.getCustomerCode(), commodityCode)) continue;

            Map<String, ExcelCommodity> excelCommodityMaps = null;

            if (excelOrderCommodityMap.containsKey(orderNo)) {
                excelCommodityMaps = excelOrderCommodityMap.get(orderNo);
            } else {
                excelCommodityMaps = Maps.newHashMap();
                OrderInfoDTO orderInfo = new OrderInfoDTO();
                BeanUtils.copyProperties(excelOrder, orderInfo);
                orders.add(orderInfo);
                excelOrderCommodityMap.put(orderNo, excelCommodityMaps);
            }

            ExcelCommodity excelCommodity = null;

            if (excelCommodityMaps.containsKey(commodityCode)) {
                excelCommodity = excelCommodityMaps.get(commodityCode);
                excelCommodity.setNumbers(
                        excelOrder.getNumbers() + excelCommodity.getNumbers()
                );
            } else {
                excelCommodity = new ExcelCommodity();
                BeanUtils.copyProperties(excelOrder, excelCommodity);
                excelCommodityMaps.put(commodityCode, excelCommodity);
            }

        }

        if (orders.stream().map(OrderInfoDTO::getOrderNo).distinct().count() < orders.size()) {
            throw  OperationException.customException(ResultEnum.order_excel_import_distinct);
        }

        CustomerBaseInfo customer = customerService.checkoutCustomer(orders.get(0).getCustomerCode());

        // 判断客户订单是否已经导入系统中
        EntityWrapper checkOrder = new EntityWrapper();
        checkOrder.in("order_no", orderNos).
                eq("customer_code", customer.getCustomerCode()).
                ne("state", StateEnum.delete.getCode());

        if (selectCount(checkOrder) > 0) {
            throw OperationException.customException(ResultEnum.order_excel_import_exist);
        }

        // 统计可合单的订单
        // 判断是否有可合单 若可以合则执行合单导入从原有数据中移除，否则走拆单导入逻辑
        Map<String, List<OrderInfoDTO>> ordersMap = Maps.newLinkedHashMap();
        orders.forEach( order -> {
            final String orderToken = order.orderToken();
            final String orderNo = order.getOrderNo();
            ArrayList<OrderCommodityDTO> orderCommodities = Lists.newArrayList();
            excelOrderCommodityMap.get(orderNo).forEach((commodityCode, excelCommodity) -> {
                OrderCommodityDTO orderCommodity = new OrderCommodityDTO();
                BeanUtils.copyProperties(excelCommodity, orderCommodity);
                orderCommodities.add(orderCommodity);
            });
            order.setCommodities(orderCommodities);

            if (ordersMap.containsKey(orderToken)) {
                ordersMap.get(orderToken).add(order);
            } else {
                ArrayList<OrderInfoDTO> hOrders = Lists.newArrayList();
                hOrders.add(order);
                ordersMap.put(orderToken, hOrders);
            }

        });
        ordersMap.forEach((key, hOrders) -> {

            if (hOrders.size() > 1) {
                // 可合单数据处理
                mergeImportOrder(hOrders, customer, operator);
            } else if (hOrders.size() == 1) {
                excelOrderImportHandler(hOrders, customer, operator);
            }
        });
        ThreadPool.excutor(() -> pickingBillService.triggerGenerator(customer.getCustomerCode(), 15));
    }

    /**
     * Excel 订单导入处理
     * @param orders
     * @param customer
     * @param operator
     */
    private void excelOrderImportHandler(List<OrderInfoDTO> orders, CustomerBaseInfo customer, String operator) {
        if (CollectionUtils.isEmpty(orders)) return;

        orders.forEach( orderInfoDTO -> {
            if (orderInfoDTO.getCommodities().size() > 0) {
                // 取消订单处理
                if (StringUtils.equalsIgnoreCase(BillState.order_cancel.getCode(), orderInfoDTO.getBillState())) {
                    cancelOrder(orderInfoDTO.getCustomerCode(), orderInfoDTO.getOrderNo(), operator);
                    return;
                }

                orderHandler(StringUtils.join(orderInfoDTO.getOrderNo(), orderInfoDTO.getCustomerCode()),
                        orderInfoDTO,
                        customer,
                        false,
                        operator);
            }
        });
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
     * 合并导入订单
     * @param importOrders 导入订单数据
     * @param customer 客户
     * @param operator 操作人
     */
    @Transactional
    public void mergeImportOrder(List<OrderInfoDTO> importOrders, CustomerBaseInfo customer, final String operator) {
        if (CollectionUtils.isEmpty(importOrders)) return;

        // 获取合单商品数据对比规则
        TreeMap<String, Integer> commodityNumMap = Maps.newTreeMap();
        final StringBuffer orderNos = new StringBuffer();
        importOrders.forEach(orderInfoDTO -> {

            if (StringUtils.isNotBlank(orderNos.toString())) {
                orderNos.append(",");
            }

            orderNos.append(orderInfoDTO.getOrderNo());
            orderInfoDTO.getCommodities().forEach( commodity-> {
                final String commodityCode = commodity.getBarCode();
                int nums = commodity.getNumbers();
                if (commodityNumMap.containsKey(commodityCode)) {
                    nums += commodityNumMap.get(commodityCode);
                }

                commodityNumMap.put(commodityCode, nums);

            });
        });
        final StringBuffer splitToken = new StringBuffer();

        commodityNumMap.forEach((key, value) -> {

            if (StringUtils.isNotBlank(splitToken.toString())) {
                splitToken.append(",");
            }

            splitToken.append(StringUtils.joinWith("+", key, value));
        });
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("rule_token", splitToken.toString());
        SplitPackageRule splitPackageRule = splitPackageRuleService.selectOne(wrapper);
        Boolean mergeFlag = splitPackageRule == null ? false : true;

        // 验证合单规则
        if (mergeFlag) {
            // 添加原始订单商品数据
            final String systemOrderNo = GeneratorCodeUtil.dataTime(5);
            Order order = new Order();
            BeanUtils.copyProperties(importOrders.get(0), order);
            order.setIsMerge(1);
            order.setIsMatched(1);
            PoUtil.add(order, operator);
            order.setOrderNo(orderNos.toString());
            order.setSystemOrderNo(systemOrderNo);
            insert(order);
            importOrders.forEach( orderInfoDTO ->  processOrderCommodities(orderInfoDTO.getCommodities(), order, customer.getId(), true, operator));

            String mailNo = UUID.randomUUID().toString();
            commodityNumMap.forEach((key, value) -> {
                MailPickingDetail mailPickingDetail = new MailPickingDetail();
                mailPickingDetail.setOrderNo(systemOrderNo);
                mailPickingDetail.setMailNo(mailNo);
                mailPickingDetail.setCommodityCode(key);
                mailPickingDetail.setPackageNums(value);
                mailPickingDetailService.add(mailPickingDetail);
            });
        } else {
            excelOrderImportHandler(importOrders, customer, operator);
        }
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

    /***
     * 订单超时预警
     */
	@Override
	public void runOrderTimeOutWarning() {
		//查询
		
	}

    /**
     * 导出订单数据
     *
     * @param orderNos
     * @return
     */
    @Override
    public List<? extends BaseRowModel> excelExport(List<String> orderNos) {
        return baseMapper.excelExport(orderNos);
    }

}
