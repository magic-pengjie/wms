package com.magic.card.wms.baseset.service.impl;

import com.alibaba.excel.util.ObjectUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.MailPickingDetailMapper;
import com.magic.card.wms.baseset.model.po.MailPickingDetail;
import com.magic.card.wms.baseset.service.IMailPickingDetailService;
import com.magic.card.wms.common.model.enums.BillState;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.utils.CommodityUtil;
import com.magic.card.wms.common.utils.PoUtil;
import com.magic.card.wms.common.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * com.magic.card.wms.baseset.service.impl
 * 拣货篮清单服务
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/17 17:28
 * @since : 1.0.0
 */
@Slf4j
@Service
public class MailPickingDetailServiceImpl extends ServiceImpl<MailPickingDetailMapper, MailPickingDetail> implements IMailPickingDetailService {
    @Autowired
    private WebUtil webUtil;

    /**
     * 添加信息
     *
     * @param mailPickingDetail
     */
    @Override @Transactional
    public void add(MailPickingDetail mailPickingDetail) {
//        webUtil.operator()
        PoUtil.add(mailPickingDetail, Constants.DEFAULT_USER);
        insert(mailPickingDetail);
    }

    /**
     * 系统订单号
     *
     * @param orderNo
     */
    @Override
    public void delete(String orderNo) {
        EntityWrapper wrapper = new EntityWrapper();

        wrapper.eq("order_no", orderNo).eq("state", StateEnum.normal.getCode());
        delete(wrapper);
    }

    /**
     * 获取虚拟快递单数据，最终生成拣货单
     *
     * @param customerCode 客户Code
     * @return
     */
    @Override
    public List<Map> virtualMails(String customerCode, Integer executeSize) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("woi.customer_code", customerCode).
                // 加载 已锁定（导入系统15分钟默认锁定）的订单生成
                lt("woi.create_time", DateTime.now().minusMinutes(15).toDate()).
                eq("woi.state", StateEnum.normal.getCode()).
                ne("woi.bill_state", BillState.order_cancel).
                groupBy("wmpd.mail_no, wmpd.order_no, woi.create_time").
                // TODO 后期添加 快递公司、区域优先生成拣货单
                orderBy("woi.create_time");
        List<Map> virtualMails = baseMapper.virtualMails(new Page(1, 20), wrapper);

        if (CollectionUtils.isEmpty(virtualMails) || virtualMails.size() < executeSize) return null;

        return virtualMails;
    }

    /**
     * 快递包裹预重 （kg）
     * 拣货篮总货物称重
     * @param virtualMail 虚拟快递号（uuid）
     * @return
     */
    @Override
    public BigDecimal mailPickingWeight(String virtualMail) {
        List<Map> mailPickingCommodityInfo = baseMapper.mailPickingCommodityInfo(virtualMail);
        //商品对应所有的耗材 (已g计算重量)
        BigDecimal weightTotal = BigDecimal.valueOf(0.00);

        for (Map mailPickingCommodity : mailPickingCommodityInfo) {
            BigDecimal singleWeigh = (BigDecimal) mailPickingCommodity.get("singleWeight");
            singleWeigh = CommodityUtil.unitConversion_G(singleWeigh, MapUtils.getString(mailPickingCommodity, "singleWeightUnit"));
            // 购买数量
            int bayNums = MapUtils.getIntValue(mailPickingCommodity, "bayNums");
            weightTotal = weightTotal.add(singleWeigh.multiply(BigDecimal.valueOf(bayNums)));

            // 判断当前商品是否存在 消耗品
            if (ObjectUtils.isEmpty(mailPickingCommodity.get("useBarCode"))) continue;

            int rightValue = MapUtils.getIntValue(mailPickingCommodity, "rightValue");
            int leftValue = MapUtils.getIntValue(mailPickingCommodity, "leftValue");


            if (rightValue >= leftValue) {
                // 范围消耗品数量
                int useNums = MapUtils.getIntValue(mailPickingCommodity, "useNums");
                int allUseNums = bayNums/rightValue*useNums;

                if (bayNums%rightValue >= leftValue) {
                    allUseNums += useNums;
                }

                BigDecimal useSingleWeigh = (BigDecimal) mailPickingCommodity.get("useSingleWeight");
                useSingleWeigh = CommodityUtil.unitConversion_G(useSingleWeigh, MapUtils.getString(mailPickingCommodity, "useSingleWeightUnit"));

                weightTotal = weightTotal.add( useSingleWeigh.multiply(BigDecimal.valueOf(allUseNums)));
            }
        }

        return weightTotal.divide(new BigDecimal("1000"));
    }

    /**
     * 获取拣货单复核商品数据列表
     *
     * @param pickNo        拣货单号
     * @param commodityCode 商品二维码
     * @return
     */
    @Override
    public List<Map> invoiceCheckCommodityList(String pickNo, String commodityCode) {
        return baseMapper.invoiceCheckCommodityList(pickNo, commodityCode);
    }

    /**
     * 获取拣货完成包裹的清单
     *
     * @param mailNo 快递单号
     * @return
     */
    @Override
    public List<Map> packageFinishedList(String mailNo) {
        return null;
    }

    /**
     * 获取拣货未完成包裹清单
     *
     * @param mailNo 快递单号
     * @return
     */
    @Override
    public List<Map> packageUnfinishedList(String mailNo) {
        return null;
    }

    /**
     * 批量获取快递单商品
     *
     * @param mails 快递单号（多个）
     * @return
     */
    @Override
    public Map<String, List> loadBatchPackageCommodity(List<String> mails) {
        List<Map> mailCommodities = baseMapper.batchLoadMailCommodity(new EntityWrapper());

        if (CollectionUtils.isNotEmpty(mailCommodities)) {
            HashMap<String, List> packageCommodities = Maps.newHashMap();
            mailCommodities.stream().forEach(map -> {
                String mailNo = MapUtils.getString(map, "mailNo");

                if (packageCommodities.containsKey(mailNo)) {
                    packageCommodities.get(mailNo).add(map);
                } else {
                    LinkedList<Object> commodities = Lists.newLinkedList();
                    commodities.add(map);
                    packageCommodities.put(mailNo, commodities);
                }
            });
            return packageCommodities;
        }

        return null;
    }
}
