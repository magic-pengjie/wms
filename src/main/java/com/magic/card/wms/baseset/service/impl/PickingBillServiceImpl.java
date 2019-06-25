package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.mapper.PickingBillMapper;
import com.magic.card.wms.baseset.model.po.MailPicking;
import com.magic.card.wms.baseset.model.po.Order;
import com.magic.card.wms.baseset.model.po.PickingBill;
import com.magic.card.wms.baseset.service.IMailPickingService;
import com.magic.card.wms.baseset.service.IOrderService;
import com.magic.card.wms.baseset.service.IPickingBillService;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.model.po.PoUtils;
import com.magic.card.wms.config.express.ExpressProviderManager;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IMailPickingService mailPickingService;
    @Autowired
    private ExpressProviderManager expressProviderManager;

    /**
     * 触发生成
     *
     * @param customerCode
     */
    @Transactional
    @Override @Synchronized // 添加同步锁
    public void triggerGenerator(String customerCode, Integer executeSize) {
        long flag = System.currentTimeMillis();
        //触发规则生成拣货单 （满足20生成拣货单）
        //获取（满足要求）的订单数据
        List<Order> orders = orderService.obtainOrderList(customerCode, executeSize);

        if (orders == null || orders.isEmpty()) return;

        //生成拣货单
        Long pickNo = System.currentTimeMillis(); //使用系统时间作为拣货单的单号
        PickingBill pickingBill = new PickingBill();
        pickingBill.setPickNo(pickNo.toString());
        pickingBill.setProcessStage("new_pick"); // 新的拣货单
        pickingBill.setBillState("save");// 设置拣货单状态 save
        PoUtils.add(pickingBill, Constants.TRIGGER_GENERATOR_PICK_USER);

        if (this.baseMapper.insert(pickingBill) < 1) return;

        //生成快递拣货篮 20
        // TODO 生成快递拣货篮可优化多线程
        for (int i = 1; i <= executeSize; i++) {
            Order order = orders.get(i-1);
            MailPicking mailPicking = new MailPicking();
            mailPicking.setOrderNo(order.getOrderNo());
            mailPicking.setPickNo(pickingBill.getPickNo());
            mailPicking.setBasketNum(i);
            //获取快递单号
            mailPicking.setMailNo(expressProviderManager.useExpressNo(order.getExpressKey()));
            //获取订单标准重量
            mailPicking.setPresetWeight(orderService.orderCommodityWeight(order.getOrderNo(), order.getCustomerCode()));
            mailPicking.setWeightUnit("kg");
            mailPickingService.generatorMailPicking(mailPicking, Constants.TRIGGER_GENERATOR_PICK_USER);
            order.setState(StateEnum.order_pick.getCode());
            order.setUpdateTime(new Date());
            order.setUpdateUser(Constants.TRIGGER_GENERATOR_PICK_USER);
            // 更新订单-拣货状态
            orderService.updateById(order);
        }

        log.info("触发规则生成拣货单耗时统计： {}ms", System.currentTimeMillis()-flag);
    }

    /**
     * 定时任务生成(每个整点半执行一次)
     */
    @Override
    public void timingGenerator() {
        log.info("定时任务执行中****** times: {}", System.currentTimeMillis() );
    }
}
