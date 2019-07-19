package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.PickingBillMapper;
import com.magic.card.wms.baseset.model.po.*;
import com.magic.card.wms.baseset.service.*;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.BillState;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.utils.PoUtil;
import com.magic.card.wms.common.utils.WrapperUtil;
import com.magic.card.wms.config.express.ExpressProviderManager;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * com.magic.card.wms.baseset.service.impl
 * 拣货单服务接口实现类
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 19:05
 * @since : 1.0.0
 */
@Slf4j
@Service
public class PickingBillServiceImpl extends ServiceImpl<PickingBillMapper, PickingBill> implements IPickingBillService {
    private static Map<String, String> defaultColumns = Maps.newConcurrentMap();

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderCommodityService orderCommodityService;
    @Autowired
    private IMailPickingService mailPickingService;
    @Autowired
    private ExpressProviderManager expressProviderManager;
    @Autowired
    private IPickingBillExceptionService pickingBillExceptionService;
    @Autowired
    private IMailPickingDetailService mailPickingDetailService;

    static {
        defaultColumns.put("id", "id");
        defaultColumns.put("pickNo", "pick_no");
        defaultColumns.put("isB2b", "is_B2B");
        defaultColumns.put("processStage", "process_stage");
        defaultColumns.put("billState", "bill_state");
        defaultColumns.put("printTimes", "state");
        defaultColumns.put("createTime", "create_time");
    }

    /**
     * 触发生成
     *
     * @param customerCode
     */
    @Override @Transactional
    public void triggerGenerator(String customerCode, Integer executeSize) {
        long start = System.currentTimeMillis();
        log.info("触发规则生成拣货单开始****** start times: {}ms", System.currentTimeMillis() );
        generatorPickingBill(customerCode, executeSize, Constants.TRIGGER_GENERATOR_PICK_USER);
        long end = System.currentTimeMillis();
        log.info("触发规则生成拣货单结束****** end times: {}ms, 总耗时 {}ms", end,end - start );

    }

    /**
     * 配货单检测结束
     * @param pickNo
     * @param operator
     * @return
     */
    @Override @Transactional
    public Object checkInvoiceClose(String pickNo, String operator) {
        // 检出拣货单基本信息
        PickingBill pickingBill = checkOutPickBill(pickNo);


        // 统计拣货单所有订单商品未分拣完成
        List<Map> omitOrderCommodities = mailPickingService.omitOrderCommodityList(pickNo, StateEnum.normal.getCode());
        BillState exceptionFlag = BillState.pick_finish;

        if (CollectionUtils.isNotEmpty(omitOrderCommodities)) {
            log.warn("配货单检测结束检查数据存在漏拣情况-----拣货单号：{}，操作人： {}", pickNo, operator);
            exceptionFlag = BillState.pick_exception;
            // 统计订单缺少商品量，记入异常流程
            new Thread(() ->
                    omitOrderCommodities.forEach(omitOrderCommodity ->
                            pickingBillExceptionService.handleException(
                                    StringUtils.joinWith(
                                            "&&",
                                            MapUtils.getString(omitOrderCommodity, "pickNo"),
                                            MapUtils.getString(omitOrderCommodity, "orderNo"))
                                    ,
                                    StringUtils.joinWith(
                                            "&&",
                                            MapUtils.getString(omitOrderCommodity, "barCode"),
                                            MapUtils.getString(omitOrderCommodity, "omitNum")
                                    )
                                    ,
                                    BillState.pick_exception_omit
                                    ,
                                    operator
                            )
                    )
            , "Omit-Invoice-Exception." + System.currentTimeMillis()).start();
        } else {
            // 若是没有缺少量，则获取异常流程中是否有错拣、多拣数据
            EntityWrapper wrapper = new EntityWrapper();
            wrapper.eq("pick_no", pickNo).
                    eq("state", StateEnum.normal.getCode());

            if (pickingBillExceptionService.selectCount(wrapper) > 0) {
                log.warn("配货单检测结束检查数据存在错拣、多拣情况-----拣货单号：{}，操作人： {}", pickNo, operator);
                exceptionFlag = BillState.pick_exception;
            }

        }

        pickingBill.setBillState(exceptionFlag.getCode());
        //复检结束
        pickingBill.setProcessStage(BillState.pick_process_check_close.getCode());
        PoUtil.update(pickingBill, operator);
        updateById(pickingBill);
        return exceptionFlag;
    }

    /**
     * 拣货单列表
     *
     * @param loadGrid
     * @return
     */
    @Override
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        Page<Object> page = loadGrid.generatorPage();
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.ne("state", StateEnum.delete.getCode());
        WrapperUtil.searchSet(wrapper, defaultColumns, loadGrid.getSearch());

        if (MapUtils.isNotEmpty(loadGrid.getOrder())) {
            WrapperUtil.orderSet(wrapper, defaultColumns, loadGrid.getOrder());
        } else {
            wrapper.orderBy("create_time", false);
        }

        loadGrid.finallyResult(page, baseMapper.loadGrid(page, wrapper));
        return loadGrid;
    }

    /**
     * 加载拣货单复检数据
     *
     * @param pickNo
     * @return
     */
    @Override
    public List<Map> pickBillLoadGrid(String pickNo) {
        checkOutPickBill(pickNo);
        return mailPickingService.loadMailPickings(pickNo);
    }

    /**
     * 配货单检测
     *
     * @param pickNo
     * @param commodityCode
     * @param operator
     */
    @Override @Transactional
    public Integer checkInvoice(String pickNo, String commodityCode, String operator) {
        // 先检测 拣货单是否存在
        PickingBill pickingBill = checkOutPickBill(pickNo);
        // 更新拣货单状态
        if (BillState.pick_save.getCode().equalsIgnoreCase(pickingBill.getBillState())) {
            pickingBill.setBillState(BillState.pick_ing.getCode());
            PoUtil.update(pickingBill, operator);
            updateById(pickingBill);
        }

        // 检测清单物品
        List<Map> checkList = this.mailPickingService.invoiceCheckList(pickNo, commodityCode);

        if (CollectionUtils.isEmpty(checkList)) {
            // 拣错商品，异常处理
            checkInvoiceException(pickNo, commodityCode, BillState.pick_exception_error, operator);
        }
        // 过滤商品已拣好订单
        checkList = checkList.stream().filter( checkInvoice ->
            MapUtils.getBoolean(checkInvoice, "pickState")
        ).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(checkList)) {
            // 商品拣多，异常处理
            checkInvoiceException(pickNo, commodityCode, BillState.pick_exception_overflow, operator);
        }

        // 获取一个数据进行拣货处理
        Map invoice = checkList.get(0);
        // 获取订单商品数据
        String orderCommodityId = MapUtils.getString(invoice, "orderCommodityId");
        OrderCommodity orderCommodity = orderCommodityService.selectById(orderCommodityId);
        // 增量拣货商品
        orderCommodity.pickNumberPlus(1);
        PoUtil.update(orderCommodity, operator);
        // 更新订单商品数据
        orderCommodityService.updateById(orderCommodity);
        //执行货篮 拣货状态更新
        Thread updatePickingFinishState = new Thread(() ->
                mailPickingService.updatePickingFinishState(pickNo, orderCommodity.getOrderNo(), operator)
        );
        updatePickingFinishState.setName("Update-Picking-Finish-State." + System.currentTimeMillis());
        updatePickingFinishState.start();
        // 返回货篮号
        return (Integer) invoice.get("basketNum");
    }

    /**
     * 定时任务生成(每个整点半执行一次)
     */
    @Override @Transactional @Synchronized
    public void timingGenerator() {
        long start = System.currentTimeMillis();
        log.info("定时任务执行开始****** start times: {}ms", start);
        // 获取系统中所有满足要求的订单(订单客户)
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("state", StateEnum.normal.getCode());
        wrapper.eq("is_b2b", false);
        wrapper.groupBy("customer_code");
        wrapper.having("COUNT(*) > 0");
        List<String> customerCodes = baseMapper.customerCodes(wrapper);
        if (customerCodes != null && customerCodes.size() > 0) {
            customerCodes.stream().forEach(customerCode -> generatorPickingBill(customerCode, 1, Constants.TIMING_GENERATOR_PICK_USER));
        }

        long end = System.currentTimeMillis();
        log.info("定时任务执行结束****** end times: {}ms, 总耗时: {}ms", end, end - start);
    }

    /**
     * 拣货单 -> 生成配货单
     *
     * @param operator  操作人
     * @param allowSize 允许操作次数
     * @param pickNos
     * @return
     */
    @Override @Transactional @Synchronized
    public List generatorInvoice(String operator, Integer allowSize, String... pickNos) {
        // 拣货单初步判断
        if (pickNos ==null && pickNos.length < 1) {
            throw OperationException.customException(ResultEnum.invoice_pick_no);
        }

        EntityWrapper wrapper = new EntityWrapper();
        wrapper.ge("state", StateEnum.normal.getCode());
        wrapper.le("state", allowSize);
        wrapper.in("pick_no", pickNos);
        // 获取多个拣货单
        List<PickingBill> pickBills = this.selectList(wrapper);

        if (pickBills == null || pickBills.isEmpty()) {
            throw OperationException.customException(ResultEnum.invoice_pick_no, "拣货单不存在或拣货单已打印过");
        }

        // 生成多个配货单
        List invoices = Lists.newLinkedList();
        pickBills.forEach(pickBill-> {
            invoices.add(mailPickingService.generatorInvoiceList(pickBill.getPickNo()));
            //更新拣货单打印次数
            pickBill.setState(pickBill.getState() + 1);
            PoUtil.update(pickBill, operator);
            updateById(pickBill);
        });

        return invoices;
    }

    /**
     * 自动生成拣货单
     * @param customerCode
     * @param executeSize
     * @param operator
     */
    @Synchronized // 添加同步锁
    public void generatorPickingBill(String customerCode, Integer executeSize, String operator) {
        //触发规则生成拣货单 （满足20 生成拣货单）
        // 获取（满足要求）的订单数据
        // TODO 生成拣货单 更改规则
        List<Map> virtualMails = mailPickingDetailService.virtualMails(customerCode, executeSize);

        if (CollectionUtils.isEmpty(virtualMails)) return;

        //生成拣货单 时间戳 年月日时分秒 + 随机四位数
        String pickNo = DateTime.now().toString("yyyyMMddHHmmss") + RandomStringUtils.random(4,"0123456789");
        PickingBill pickingBill = new PickingBill();
        pickingBill.setPickNo(pickNo);
        pickingBill.setProcessStage("new_pick"); // 新的拣货单
        pickingBill.setBillState("save");// 设置拣货单状态 save
        PoUtil.add(pickingBill, operator);

        if (this.baseMapper.insert(pickingBill) < 1) return;

        //生成快递拣货篮 20
        for (int basketNum = 1; basketNum <= virtualMails.size(); basketNum++) {
            Map virtualMail = virtualMails.get(basketNum - 1);
            Order order = orderService.selectOne(
                    new EntityWrapper().eq(
                            "system_order_no",
                            virtualMail.get("systemOrderNo")
                    )
            );
            String realMail = expressProviderManager.useExpressNo(order.getExpressKey());
            MailPicking mailPicking = new MailPicking();
            mailPicking.setOrderNo(order.getOrderNo());
            mailPicking.setPickNo(pickingBill.getPickNo());
            mailPicking.setBasketNum(basketNum);
            //获取快递单号
            mailPicking.setMailNo(realMail);
            //获取订单标准重量
            mailPicking.setPresetWeight(mailPickingDetailService.mailPickingWeight(MapUtils.getString(
                    virtualMail,
                    "mailNo"
            )));
            mailPicking.setWeightUnit("kg");
            mailPickingService.generatorMailPicking(mailPicking, operator);

            // region 更新拣货篮详情信息
            EntityWrapper wrapper = new EntityWrapper();
            wrapper.eq("mail_no", MapUtils.getString(
                    virtualMail,
                    "mailNo"
            )).eq("state", StateEnum.normal.getCode());
            MailPickingDetail mailPickingDetail = new MailPickingDetail();
            mailPickingDetail.setMailNo(mailPicking.getMailNo());
            mailPickingDetail.setPickNo(mailPicking.getPickNo());
            PoUtil.update(mailPickingDetail, operator);
            mailPickingDetailService.update(mailPickingDetail, wrapper);
            // endregion

            // region 更新订单状态（已生成拣货单数据）
            order.setState(StateEnum.order_pick.getCode());
            order.setUpdateTime(new Date());
            order.setUpdateUser(operator);
            orderService.updateById(order);
            // endregion
        }
    }

    /**
     *  配货单检测异常处理
     * @param pickNo 拣货单号/拣货单号&&订单号
     * @param commodityCode 商品条形码/商品条形码&&数量
     * @param type 拣货异常类型
     * @param operator 操作人
     */
    private void checkInvoiceException(String pickNo, String commodityCode, BillState type, String operator) {
        ResultEnum invoicePickCommodity = ResultEnum.invoice_pick_commodity_overflow;
        log.warn("配货单检测数据存在错拣、多拣情况-----拣货单号：{}，商品条形码：{}，操作人： {}", pickNo, commodityCode, operator);
        switch (type) {
            case pick_exception_error:
                invoicePickCommodity = ResultEnum.invoice_pick_commodity_exist;
                break;
            case pick_exception_omit:
                invoicePickCommodity = ResultEnum.invoice_pick_commodity_omit;
                break;
        }
        //使用辅助线程处理拣货异常
        new Thread(() ->
                pickingBillExceptionService.handleException(pickNo, commodityCode, type, operator)
                , "PICK-BILL-EXCEPTION-HANDLER." + System.currentTimeMillis()).start();
        throw OperationException.customException(invoicePickCommodity);
    }

    /**
     * 检出基本拣货单信息
     * @param pickNo 拣货单编号
     * @return
     */
    private PickingBill checkOutPickBill(String pickNo) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("pick_no", pickNo);
        PickingBill pickingBill = selectOne(wrapper);

        if (pickingBill == null) {
            throw OperationException.customException(ResultEnum.invoice_pick_no, "拣货单不存在！");
        }
        // 复检是否关闭
        if (StringUtils.equalsIgnoreCase(BillState.pick_process_check_close.getCode(), pickingBill.getProcessStage())) {
            throw OperationException.customException(ResultEnum.invoice_pick_close);
        }
        return pickingBill;
    }
}
