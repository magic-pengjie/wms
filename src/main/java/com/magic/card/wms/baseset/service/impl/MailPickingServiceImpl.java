package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.MailPickingMapper;
import com.magic.card.wms.baseset.model.po.MailPicking;
import com.magic.card.wms.baseset.service.IMailPickingService;
import com.magic.card.wms.baseset.service.IOrderCommodityService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.utils.PoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * com.magic.card.wms.baseset.service.impl
 *  快递篮拣货服务接口实现类
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/25/025 9:58
 * @since : 1.0.0
 */
@Slf4j
@Service
public class MailPickingServiceImpl extends ServiceImpl<MailPickingMapper, MailPicking> implements IMailPickingService {
    @Autowired
    private IOrderCommodityService orderCommodityService;

    /**
     * 获取拣货单所有漏检商品数据信息
     *
     * @param pickNo
     * @param state
     * @return
     */
    @Override
    public List<Map> omitOrderCommodityList(String pickNo, Integer state) {
        return baseMapper.omitOrderCommodityList(pickNo, state);
    }

    /**
     * 加载拣货单所有拣货篮数据
     *
     * @param pickNo
     * @return
     */
    @Override
    public List<Map> loadMailPickings(String pickNo) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("pick_no", pickNo).
                eq("state", StateEnum.normal.getCode()).
                orderBy("basket_num");
        List<Map> mailPickings = baseMapper.selectMaps(wrapper);

        if (CollectionUtils.isNotEmpty(mailPickings)){
            List<String> orderNos = mailPickings.stream().map(
                    map -> map.get("orderNo").toString()
                ).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(orderNos)) {
                Map<String, List> orderCommodities = orderCommodityService.loadBatchOrderCommodityGrid(orderNos);
                mailPickings.stream().forEach(mailPicking ->
                        mailPicking.put(
                                "orderCommodities",
                                orderCommodities.get(
                                    MapUtils.getString(mailPicking, "orderNo")
                                )
                        )
                );
            }


        }

        return mailPickings;
    }

    /**
     * 自动生成快递篮拣货单服务
     *
     * @param mailPicking
     * @param operator
     */
    @Override @Transactional
    public void generatorMailPicking(MailPicking mailPicking, String operator) {
        PoUtil.add(mailPicking, operator);
        this.insert(mailPicking);
    }

    /**
     * 自动生成配货单清单
     * @param pickNo 拣货单号
     * @return
     */
    @Override
    public List<Map> generatorInvoiceList(String pickNo) {
        //拣货区拿去货物
        List<Map> invoiceList = this.baseMapper.invoiceList(pickNo, Constants.BILL_STATE_CANCEL, Constants.PICKING_AREA_CODE);

        if (invoiceList == null && invoiceList.isEmpty()) {
            throw OperationException.addException("配货单生成异常，数据为空请核实数据！");
        }

        //补货预警提示
        List<Map> replenishmentNotices = Lists.newLinkedList();
        invoiceList.forEach(invoice->{
            int bayNums = MapUtils.getIntValue(invoice, "bayNums");
            int availableNums = MapUtils.getIntValue(invoice, "availableNums");
            // 可用量小于购买量
            if (availableNums < bayNums) {
                replenishmentNotices.add(invoice);
            }
        });

        new Thread(() -> {
            //TODO 拣货区库存不足 补货提醒功能待实现
            if (CollectionUtils.isNotEmpty(replenishmentNotices)) {
                replenishmentNotices.forEach(map -> {
                    log.warn("拣货区库存不足请及时补货： 商品条形码：{}", map.get("barCode"));
                });
            }
        }, "JHQ-Notice-Thread-NO." + System.currentTimeMillis()).start();

        return invoiceList;
    }

    /**
     * 自动更新拣货篮所有订单完成状态
     *
     * @param pickNo
     */
    @Override @Transactional
    public void autoUpdatePickingFinishState(String pickNo, String operator) {
        // TODO 自动更新拣货篮所有订单完成状态
    }

    /**
     * 更新拣货篮指定订单 -> 完成状态
     *
     * @param pickNo
     * @param orderNo
     */
    @Override @Transactional
    public void updatePickingFinishState(String pickNo, String orderNo, String operator) {
        // 获取对应的拣货篮信息
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("pick_no", pickNo).
                eq("order_no", orderNo);
        MailPicking mailPicking = selectOne(wrapper);

        if (mailPicking == null && mailPicking.getIsFinish() == 1) return;

        if (baseMapper.countOrderCommodityUnfinished(orderNo) == 0) {
            // 设置状态
            mailPicking.setIsFinish(1);
            PoUtil.update(mailPicking, operator);
            updateById(mailPicking);
        }

    }

    /**
     * 配货单清单检查数据
     *
     * @param pickNo
     * @param commodityCode
     * @return
     */
    @Override
    public List<Map> invoiceCheckList(String pickNo, String commodityCode) {
        return baseMapper.invoiceCheckList(pickNo, commodityCode);
    }

    /**
     * 获取拣货单各个拣货篮的完成状态
     *
     * @param pickNo
     * @return
     */
    @Override
    public List<Map> obtainPickingFinishState(String pickNo) {
        return baseMapper.obtainPickingFinishState(pickNo);
    }
}
