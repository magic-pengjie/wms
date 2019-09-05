package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.PickingBillMapper;
import com.magic.card.wms.baseset.model.dto.invoice.InvoiceCheckOmitDTO;
import com.magic.card.wms.baseset.model.po.*;
import com.magic.card.wms.baseset.service.*;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.BillState;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.utils.*;
import com.magic.card.wms.config.express.ExpressProviderManager;
import lombok.Synchronized;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    @Autowired
    private WebUtil webUtil;

    @Value("${pick.bill.max.basket.nums}")
    public Integer MAX_BASKET_NUM = 15;

    static {
        defaultColumns.put("id", "id");
        defaultColumns.put("pickNo", "pick_no");
        defaultColumns.put("isB2b", "is_B2B");
        defaultColumns.put("processStage", "process_stage");
        defaultColumns.put("billState", "bill_state");
        defaultColumns.put("printTimes", "state");
        defaultColumns.put("createTime", "create_time");
        defaultColumns.put("areaLevel", "area_level");
    }

    /**
     * 触发生成
     *
     * @param customerCode 客户编码
     * @param executeSize
     */
    @Override @Transactional
    public void triggerGenerator(String customerCode, Integer executeSize) {
        long start = System.currentTimeMillis();
        log.info("触发规则生成拣货单开始****** start times: {}ms", System.currentTimeMillis() );
        generatorAreaPickingBill(customerCode, executeSize, Constants.TRIGGER_GENERATOR_PICK_USER);
        long end = System.currentTimeMillis();
        log.info("触发规则生成拣货单结束****** end times: {}ms, 总耗时 {}ms", end,end - start );

    }

    /**
     * 执行拣货单生成
     */
    @Override
    public void executorGenerator() {
        timingGenerator(webUtil.operator(), 1);
    }

    /**
     * 配货单检查完毕
     *
     * @param pickNo
     * @param pickOperator
     */
    @Override @Transactional
    public void checkInvoiceFinished(String pickNo, String pickOperator) {
        String operator = webUtil.operator();
        PickingBill pickingBill = checkOutPickBill(pickNo);
        pickingBill.setBillState(BillState.pick_finish.getCode());
        pickingBill.setPickUser(pickOperator);
        pickingBill.setProcessStage(BillState.pick_process_check_close.getCode());
        PoUtil.update(pickingBill, operator);
        updateById(pickingBill);
        mailPickingService.updatePickingFinishState(pickNo, Lists.newArrayList());
        // 更新订单状态 -> 打包中。。。
        baseMapper.updateOrderBillState(pickNo, BillState.order_packing.getCode(),BillState.order_cancel.getCode());
    }

    /**
     * 配货单漏检
     *
     * @param omitInfo
     */
    @Override
    public <T> void checkInvoiceOmit(T omitInfo) {
        InvoiceCheckOmitDTO invoiceCheckOmitDTO = (InvoiceCheckOmitDTO)omitInfo;
        final String pickNo = invoiceCheckOmitDTO.getPickNo();
        checkOutPickBill(pickNo);

        List<String> excludeMails = Lists.newLinkedList();
        invoiceCheckOmitDTO.getMails().forEach(omitMailDTO -> {
            final String mailNo = omitMailDTO.getMailNo();
            String omitToken = StringUtils.joinWith(
                    "&&",
                    invoiceCheckOmitDTO.getPickNo(),
                    omitMailDTO.getOrderNo(),
                    mailNo);
            excludeMails.add(mailNo);
            ArrayList<String> excludeCommodities = Lists.newArrayList();
            omitMailDTO.getCommodities().forEach(omitCommodity -> {
                final String commodityCode = omitCommodity.getCommodityCode();
                final int omitNums = omitCommodity.getOmitNums();
                excludeCommodities.add(commodityCode);
                mailPickingDetailService.packageOmit(pickNo, mailNo, commodityCode, omitNums);
                pickingBillExceptionService.handleException( omitToken,
                        StringUtils.joinWith(
                                "&&",
                                commodityCode,
                                omitNums
                        )
                        ,
                        BillState.pick_exception_omit
                        ,
                        invoiceCheckOmitDTO.getPickOperator()
                );
            });
            // 更新包裹其他商品数据信息
            mailPickingDetailService.updatePackageCommodity(pickNo, mailNo, excludeCommodities);
        });

        // 更新拣货单其他包裹拣货
        mailPickingService.updatePickingFinishState(pickNo, excludeMails);
    }

    /**
     * 配货单检测结束
     * @param pickNo
     * @return
     */
    @Override @Transactional
    public Object checkInvoiceClose(String pickNo) {
        //region  此处逻辑将重写 2019.7.23 - 10:27处理完成
        String operator = webUtil.operator();
        // 检出拣货单基本信息
        PickingBill pickingBill = checkOutPickBill(pickNo);

        // 更新订单状态 -> 打包中。。。
        baseMapper.updateOrderBillState(pickNo, BillState.order_packing.getCode(),BillState.order_cancel.getCode());
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
                                            MapUtils.getString(omitOrderCommodity, "orderNo"),
                                            MapUtils.getString(omitOrderCommodity, "mailNo"))
                                    ,
                                    StringUtils.joinWith(
                                            "&&",
                                            MapUtils.getString(omitOrderCommodity, "commodityCode"),
                                            MapUtils.getString(omitOrderCommodity, "omitNums")
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
        //endregion
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
        WrapperUtil.autoSettingSearch(wrapper, defaultColumns, loadGrid.getSearch());

        if (MapUtils.isNotEmpty(loadGrid.getOrder())) {
            WrapperUtil.autoSettingOrder(wrapper, defaultColumns, loadGrid.getOrder());
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
//        checkOutPickBill(pickNo);
        pickLockProcess(pickNo, true);
        return mailPickingService.loadMailPickings(pickNo);
    }


    /**
     * 配货单检测
     *
     * @param pickNo
     * @param commodityCode
     */
    @Override @Transactional
    public Integer checkInvoice(String pickNo, String commodityCode) {
        String operator = webUtil.operator();
        // 先检测 拣货单是否存在
        PickingBill pickingBill = checkOutPickBill(pickNo);
        // 更新拣货单状态
        if (BillState.pick_save.getCode().equalsIgnoreCase(pickingBill.getBillState())) {
            pickingBill.setBillState(BillState.pick_ing.getCode());
            PoUtil.update(pickingBill, operator);
            updateById(pickingBill);
        }

        // 检测清单物品
        List<Map> checkList = mailPickingDetailService.invoiceCheckCommodityList(pickNo, commodityCode);

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
        String mailDetailId = MapUtils.getString(invoice, "mailDetailId");
        MailPickingDetail mailPickingDetail = mailPickingDetailService.selectById(mailDetailId);
        // 增量拣货商品
        mailPickingDetail.pickNumsPlus(1);
        PoUtil.update(mailPickingDetail, operator);
        // 更新订单商品数据
        mailPickingDetailService.updateById(mailPickingDetail);
        //执行货篮 拣货状态更新
        WmsThreadPool.executor(new Thread(() ->
                mailPickingService.updatePickingFinishState(mailPickingDetail.getMailNo()),
                "Update-Picking-Finish-State." + System.currentTimeMillis()));
        // 返回货篮号
        return (Integer) invoice.get("basketNum");
    }

    /**
     * 定时任务生成(每个整点半执行一次)
     */
    @Override @Transactional @Synchronized
    public void timingGenerator(String operator, Integer executeSize) {
        // 获取系统中所有满足要求的订单(订单客户)
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("woi.state", StateEnum.normal.getCode()).
                eq("woi.is_b2b", 0).
                eq("woi.is_batch", 0).
                eq("woi.is_lock", 1).
                eq("woi.is_matched", 1).
                andNew().
                eq("woi.bill_state", BillState.order_save.getCode()).
                or("wpb.bill_state = {0}", BillState.pick_cancel.getCode()).
                groupBy("woi.customer_code").
                having("COUNT(*) > 0");
        List<String> customerCodes = baseMapper.customerCodes(wrapper);

        if (CollectionUtils.isNotEmpty(customerCodes)) {
            customerCodes.stream().forEach(customerCode -> generatorAreaPickingBill(customerCode, executeSize, operator));
        }

    }

    /**
     * 拣货单 -> 生成配货单
     *
     * @param operator  操作人
     * @return
     */
    @Override @Transactional @Synchronized
    public List generatorInvoice(List<String> pickNos) {
        // 拣货单初步判断
        if (CollectionUtils.isEmpty(pickNos)) {
            throw OperationException.customException(ResultEnum.invoice_pick_no);
        }

        String operator = webUtil.operator();
        Integer allowSize = 1;

                EntityWrapper wrapper = new EntityWrapper();
        wrapper.ge("state", StateEnum.normal.getCode()).
                le("state", allowSize).
                ne("bill_state", BillState.pick_cancel.getCode()).
                in("pick_no", pickNos);
        // 获取多个拣货单
        List<PickingBill> pickBills = selectList(wrapper);

        if (pickBills == null || pickBills.isEmpty()) {
            throw OperationException.customException(ResultEnum.invoice_pick_no, "拣货单不存在或拣货单已打印过");
        }

        // 生成多个配货单
        List invoices = Lists.newLinkedList();
        pickBills.forEach(pickBill-> {
            invoices.add(mailPickingService.generatorInvoiceList(pickBill.getPickNo()));
//            //更新拣货单打印次数
//            pickBill.setState(pickBill.getState() + 1);
//            PoUtil.update(pickBill, operator);
//            updateById(pickBill);
        });

        return invoices;
    }

    /**
     * 自动生成区域拣货单
     * @param customerCode
     * @param executeSize
     * @param operator
     */
    @Synchronized
    public void generatorAreaPickingBill(String customerCode, Integer executeSize, String operator) {
        Map<String, List<List<Map>>> areaVirtualMails = mailPickingDetailService.areaVirtualMails(customerCode, executeSize);
        areaVirtualMails.forEach((area, packageVirtualMails) ->
            packageVirtualMails.forEach(virtualMails -> {
                if (CollectionUtils.isEmpty(virtualMails)) return;

                //生成拣货单 时间戳 年月日时分秒 + 随机四位数
                String pickNo = "JH" + GeneratorCodeUtil.dataTime(4);
                PickingBill pickingBill = new PickingBill();
                pickingBill.setPickNo(pickNo);
                pickingBill.setAreaLevel(area);
                pickingBill.setProcessStage(BillState.pick_process_new.getCode()); // 新的拣货单
                pickingBill.setBillState(BillState.pick_save.getCode());// 设置拣货单状态 save
                PoUtil.add(pickingBill, operator);

                if (this.baseMapper.insert(pickingBill) < 1) return;

                //生成快递拣货篮
                Map<String, Order> ordersMap = orderService.ordersMap(
                        virtualMails.
                                stream().
                                filter(virtualMail -> !StringUtils.equalsIgnoreCase(
                                        BillState.pick_cancel.getCode(),
                                        MapUtils.getString(virtualMail, "billState"))).
                                map(virtualMail ->
                                        virtualMail.get("systemOrderNo").toString()).
                                collect(Collectors.toList()));
                Map<String, Integer> ignoreVirtualMails = Maps.newLinkedHashMap();
                for (int basketNum = 1; basketNum <= virtualMails.size(); basketNum++) {
                    Map virtualMail = virtualMails.get(basketNum - 1);
                    final String pickBillState = MapUtils.getString(virtualMail, "billState");
                    final String systemOrderNo = MapUtils.getString(virtualMail, "systemOrderNo");
                    final String virtualMailNo = MapUtils.getString(virtualMail, "mailNo");

                    if (StringUtils.equalsIgnoreCase(BillState.pick_cancel.getCode(), pickBillState)) {
                        ignoreVirtualMails.put(virtualMailNo, basketNum);
                        continue;
                    }

                    Order order = ordersMap.get(systemOrderNo);
                    //获取快递单号
                    String realMail = expressProviderManager.useExpressNo(order.getExpressKey());

                    MailPicking mailPicking = new MailPicking();
                    mailPicking.setOrderNo(order.getSystemOrderNo());
                    mailPicking.setPickNo(pickingBill.getPickNo());
                    mailPicking.setBasketNum(basketNum);
                    mailPicking.setMailNo(realMail);
                    mailPicking.setProcessState(BillState.package_picking.getCode());
                    //获取订单标准重量
                    mailPicking.setPresetWeight(mailPickingDetailService.mailPickingWeight(virtualMailNo, systemOrderNo));
                    mailPicking.setWeightUnit("kg");
                    mailPickingService.generatorMailPicking(mailPicking, operator);

                    // region 更新拣货篮详情信息
                    EntityWrapper wrapper = new EntityWrapper();
                    wrapper.eq("mail_no",  virtualMailNo).eq("state", StateEnum.normal.getCode());
                    MailPickingDetail mailPickingDetail = new MailPickingDetail();
                    mailPickingDetail.setMailNo(mailPicking.getMailNo());
                    mailPickingDetail.setPickNo(mailPicking.getPickNo());
                    PoUtil.update(mailPickingDetail, operator);
                    mailPickingDetailService.update(mailPickingDetail, wrapper);
                    // endregion
                    // region 更新订单状态（已生成拣货单数据）
                    order.setBillState(BillState.order_picking.getCode());
                    order.setUpdateTime(new Date());
                    order.setUpdateUser(operator);
                    orderService.updateById(order);
                    // endregion
                }

                // 拣货区库存对应减少，若拣货区库存小于拣货单所需值怎已负值形式展现，同时库存是否充足，若不足则通知补货
                mailPickingDetailService.needNoticeReplenishment(pickNo);

                // 更新对应的包裹拣货单以及篮号
                if (MapUtils.isNotEmpty(ignoreVirtualMails)) {
                    List<String> mailNos = Lists.newArrayList();
                    ignoreVirtualMails.forEach((ignoreVirtualMail, value) -> {
                        mailNos.add(ignoreVirtualMail);
                        mailPickingService.updateForSet(String.format("pick_no = '%s', basket_num = %s", pickNo, value), new EntityWrapper().eq("mail_no", ignoreVirtualMail));
                    });
                    mailPickingDetailService.updateForSet(String.format("pick_no = '%s'", pickNo), new EntityWrapper().in("mail_no", mailNos));
                }
            }));
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
        // 拣货单是否作废
        if (StringUtils.equalsIgnoreCase(BillState.pick_cancel.getCode(), pickingBill.getBillState())) {
            throw OperationException.customException(ResultEnum.invoice_pick_cancel);
        }

        return pickingBill;
    }

    /**
     * 拣货单加解锁处理
     * @param pickNo 拣货单号
     * @param lock 是否锁定
     */
    @Transactional
    public void pickLockProcess(String pickNo, Boolean lock) {
        PickingBill pickingBill = checkOutPickBill(pickNo);

        if (lock && StringUtils.equalsIgnoreCase(BillState.pick_process_lock.getCode(), pickingBill.getProcessStage())) {
            throw OperationException.customException(ResultEnum.invoice_pick_lock);
        }

        if (StringUtils.equalsIgnoreCase(BillState.pick_finish.getCode(), pickingBill.getBillState())) {
            throw OperationException.customException(ResultEnum.invoice_pick_finish);
        }

        String setPickLock = String.format("process_stage = '%s'", lock ? BillState.pick_process_lock.getCode() : BillState.pick_process_unlock.getCode());
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("pick_no", pickNo).ne("state", StateEnum.delete.getCode());
        updateForSet(setPickLock, wrapper);
    }

    /**
     * 批量作废
     *
     * @param pickNos 拣货单号
     */
    @Override @Transactional
    public void batchCancel(List<String> pickNos) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.in("pick_no", pickNos).
                eq("process_stage", BillState.pick_process_new.getCode()).
                eq("bill_state", BillState.pick_save.getCode());
        baseMapper.batchCancel(BillState.pick_cancel.getCode(), wrapper);
    }

    /**
     * 拣货单作废处理
     *
     * @param pickNo 拣货单号
     */
    @Override @Transactional
    public void cancel(String pickNo) {
        PickingBill pickingBill = checkOutPickBill(pickNo);

        if (!StringUtils.equalsIgnoreCase(BillState.pick_save.getCode(), pickingBill.getBillState()) ||
            !StringUtils.equalsIgnoreCase(BillState.pick_process_new.getCode(), pickingBill.getProcessStage())) {
            throw OperationException.customException(ResultEnum.invoice_pick_not_allow_cancel);
        }

        pickingBill.setBillState(BillState.pick_cancel.getCode());
        PoUtil.update(pickingBill, webUtil.operator());
        updateById(pickingBill);
     }

    /**
     * 打印配货单
     *
     * @param pickNos
     */
    @Override @Transactional
    public void printInvoices(List<String> pickNos) {
        if (CollectionUtils.isEmpty(pickNos)) return;

        EntityWrapper<PickingBill> wrapper = new EntityWrapper();
        wrapper.in("pick_no", pickNos);
        List<PickingBill> pickingBills = selectList(wrapper);

        if (CollectionUtils.isEmpty(pickingBills)) return;

        pickingBills.forEach(pickingBill -> {
            if (pickingBill.getState() == 1) {
                pickingBill.setBillState(BillState.pick_ing.getCode());
            }

            pickingBill.setState(pickingBill.getState() + 1);
            PoUtil.update(pickingBill, webUtil.operator());
            updateById(pickingBill);
        });
    }
}
