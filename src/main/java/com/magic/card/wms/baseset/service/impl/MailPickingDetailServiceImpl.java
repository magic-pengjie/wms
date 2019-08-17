package com.magic.card.wms.baseset.service.impl;

import com.alibaba.excel.util.ObjectUtils;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.MailPickingDetailMapper;
import com.magic.card.wms.baseset.model.po.CommodityReplenishment;
import com.magic.card.wms.baseset.model.po.MailPickingDetail;
import com.magic.card.wms.baseset.model.po.StorehouseConfig;
import com.magic.card.wms.baseset.service.ICommodityReplenishmentService;
import com.magic.card.wms.baseset.service.IMailPickingDetailService;
import com.magic.card.wms.baseset.service.IStorehouseConfigService;
import com.magic.card.wms.common.model.enums.BillState;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.model.enums.StoreTypeEnum;
import com.magic.card.wms.common.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    /**
     * 最大货篮编号号
     */
    @Value("${pick.bill.max.basket.nums}")
    public Integer MAX_BASKET_NUM = 15;
    public static final String FIRST_AREA_KEY = "FIRST_AREA";
    public static final String SECOND_AREA_KEY = "SECOND_AREA";
    public static final String OTHER_AREA_KEY = "OTHER_AREA";

    /**
     * 第一区域
     */
    public static final List<String> FIRST_AREA = Lists.newArrayList("上海");
    /**
     * 第二区域
     */
    public static final List<String> SECOND_AREA = Lists.newArrayList("江苏", "浙江", "安徽");

    @Autowired
    private IStorehouseConfigService storehouseConfigService;
    @Autowired
    private ICommodityReplenishmentService commodityReplenishmentService;
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
        PoUtil.add(mailPickingDetail, webUtil.operator());
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
//    @Override
//    public List<Map> virtualMails(String customerCode, Integer executeSize) {
//        EntityWrapper wrapper = new EntityWrapper();
//        wrapper.eq("woi.customer_code", customerCode).
//                // 加载 已锁定（导入系统15分钟默认锁定）的订单生成
//                eq("is_lock", 1).
//                eq("woi.state", StateEnum.normal.getCode()).
//                ne("woi.bill_state", BillState.order_cancel).
//                groupBy("wmpd.mail_no, wmpd.order_no, woi.create_time").
//                // TODO 后期添加 快递公司、区域优先生成拣货单
//                orderBy("woi.create_time");
//        List<Map> virtualMails = baseMapper.virtualMails(new Page(1, MAX_BASKET_NUM), wrapper);
//
//        if (CollectionUtils.isEmpty(virtualMails) || virtualMails.size() < executeSize) return null;
//
//        return virtualMails;
//    }

    /**
     * 获取商户订单不同区域的快递单数据
     *
     * @param customerCode 客户CODE
     * @param executeSize 允许操作数
     * @return
     */
    @Override
    public Map<String, List<List<Map>>> areaVirtualMails(String customerCode, Integer executeSize){
        Map<String, List<List<Map>>> areaVirtualMails = Maps.newHashMap();
        // First Area 上海
        areaVirtualMails.put(FIRST_AREA_KEY, firstAreaVirtualMails(customerCode, executeSize));
        // Second Area 江浙皖
        areaVirtualMails.put(SECOND_AREA_KEY, secondAreaVirtualMails(customerCode, executeSize));
        // Other Area 全国
        areaVirtualMails.put(OTHER_AREA_KEY, otherAreaVirtualMails(customerCode, executeSize));
        return areaVirtualMails;
    }

    /**
     * 获取一区包裹数据
     *
     * @param customerCode
     * @param executeSize
     * @return
     */
    private List<List<Map>> firstAreaVirtualMails(String customerCode, Integer executeSize) {
        // First Area 上海
        EntityWrapper firstAreaWrapper = basetVirtualMailWrapper(customerCode);
        firstAreaWrapper.andNew();
        FIRST_AREA.forEach(area -> {

            if (FIRST_AREA.indexOf(area) > 0) {
                firstAreaWrapper.or();
            }

            firstAreaWrapper.like("woi.prov", area, SqlLike.RIGHT);
        });
        return areaVirtualPageMails(firstAreaWrapper, executeSize);
    }

    /**
     * 获取二区包裹数据
     *
     * @param customerCode
     * @param executeSize
     * @return
     */
    private List<List<Map>> secondAreaVirtualMails(String customerCode, Integer executeSize) {
        EntityWrapper secondAreaWrapper = basetVirtualMailWrapper(customerCode);
        secondAreaWrapper.andNew();
        SECOND_AREA.forEach(area -> {

            if (SECOND_AREA.indexOf(area) > 0) {
                secondAreaWrapper.or();
            }

            secondAreaWrapper.like("woi.prov", area, SqlLike.RIGHT);
        });
        return areaVirtualPageMails(secondAreaWrapper, executeSize);
    }

    /**
     * 获取全国其他地区的包裹数据
     * @param customerCode
     * @param executeSize
     * @return
     */
    private List<List<Map>> otherAreaVirtualMails(String customerCode, Integer executeSize) {
        EntityWrapper otherAreaWrapper = basetVirtualMailWrapper(customerCode);
        Lists.newArrayList(FIRST_AREA, SECOND_AREA).forEach(areas ->
                areas.forEach(area -> otherAreaWrapper.notLike("woi.prov", area, SqlLike.RIGHT))
        );
        return areaVirtualPageMails(otherAreaWrapper, executeSize);
    }

    /**
     * 分页包裹数据
     *
     * @param wrapper
     * @param executeSize
     * @return
     */
    private List<List<Map>> areaVirtualPageMails(EntityWrapper wrapper, Integer executeSize) {
        List<List<Map>> areaPage = Lists.newArrayList();
        List<Map> virtualMails = baseMapper.virtualMails(wrapper);

        if (CollectionUtils.isNotEmpty(virtualMails)) {
            final int inall = virtualMails.size();
            for (int page = 0; page <= inall / MAX_BASKET_NUM; page ++) {
                int startIndex = page * MAX_BASKET_NUM;
                int endIndex = startIndex + MAX_BASKET_NUM > inall ? inall : startIndex + MAX_BASKET_NUM;
                List<Map> maps = virtualMails.subList(startIndex, endIndex);

                if (executeSize <= maps.size()) {
                    areaPage.add(maps);
                }

            }
        }

        return areaPage;
    }

    /**
     * 获取虚拟包裹基本查询条件
     * @return
     */
    private EntityWrapper basetVirtualMailWrapper(String customerCode) {
        EntityWrapper baseWrapper = new EntityWrapper();
        baseWrapper.eq("woi.customer_code", customerCode).
                eq("is_lock", 1).
                eq("woi.state", StateEnum.normal.getCode()).
                eq("woi.bill_state", BillState.order_save.getCode()).
                groupBy("wmpd.mail_no, wmpd.order_no, woi.create_time").
                orderBy("woi.create_time");
        return baseWrapper;
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

    /**
     * 拣货单商品数据对应库存不足时通知处理
     *
     * @param pickNo
     */
    @Override
    public void needNoticeReplenishment(String pickNo) {
        List<Map> noticeReplenishments = baseMapper.needNoticeReplenishment(pickNo, BillState.order_cancel.getCode(), StoreTypeEnum.JHQ.getCode());

        if (CollectionUtils.isNotEmpty(noticeReplenishments)) {
            CommodityReplenishment baseCommodityReplenishment = new CommodityReplenishment();
            PoUtil.add(baseCommodityReplenishment, webUtil.operator());
            List<StorehouseConfig> updateStorehouseConfigs = Lists.newArrayList();
            StorehouseConfig baseUpdateStorehouseConfig = new StorehouseConfig();
            PoUtil.update(baseUpdateStorehouseConfig, webUtil.operator());
            List<CommodityReplenishment> commodityReplenishments = noticeReplenishments.
                    stream().
                    filter(noticeReplenishment -> {
                        // 减库存
                        StorehouseConfig storehouseConfig = new StorehouseConfig();
                        BeanUtils.copyProperties(baseUpdateStorehouseConfig, storehouseConfig);
                        storehouseConfig.setId(MapUtils.getLong(noticeReplenishment, "id"));
                        storehouseConfig.setAvailableNums(MapUtils.getInteger(noticeReplenishment, "omitNums") * -1);
                        updateStorehouseConfigs.add(storehouseConfig);
                        return MapUtils.getInteger(noticeReplenishment, "omitNums") > 0;
                    }).
                    map(noticeReplenishment -> {
                        // commodityIds.add(MapUtils.getString(noticeReplenishment, "commodityId"));
                        CommodityReplenishment commodityReplenishment = new CommodityReplenishment();
                        BeanUtils.copyProperties(baseCommodityReplenishment, commodityReplenishment);
                        commodityReplenishment.setReplenishmentNo(GeneratorCodeUtil.dataTime(5));
                        commodityReplenishment.setCommodityId(MapUtils.getString(noticeReplenishment, "commodityId"));
                        commodityReplenishment.setCheckoutId(MapUtils.getString(noticeReplenishment, "storehouseId"));
                        commodityReplenishment.setStockoutNums(MapUtils.getInteger(noticeReplenishment, "omitNums"));
//                        commodityReplenishment.setReplenishmentNums(MapUtils.getInteger(noticeReplenishment, "omitNums"));
                        return commodityReplenishment;
                    }).
                    collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(commodityReplenishments)) {
                commodityReplenishmentService.executorReplenishmentOperation(commodityReplenishments);
            }
//            commodityReplenishmentService.insertBatch(commodityReplenishments);

            if (CollectionUtils.isNotEmpty(updateStorehouseConfigs)) {
                storehouseConfigService.updateBatchById(updateStorehouseConfigs);
            }
        }
    }

    /**
     * 包裹数据漏检记录
     *
     * @param pickNo        拣货单号
     * @param mailNo        快递单号
     * @param commodityCode 商品编号
     * @param omitNums      漏检数量
     */
    @Override @Transactional
    public void packageOmit(String pickNo, String mailNo, String commodityCode, int omitNums) {
        baseMapper.packageOmit(pickNo, mailNo, commodityCode, omitNums);
    }

    /**
     * 更新拣货单包裹数据
     *
     * @param pickNo             拣货单号
     * @param mailNo             快递单号
     * @param excludeCommodities 排除产品条形码
     */
    @Override @Transactional
    public void updatePackageCommodity(String pickNo, String mailNo, ArrayList<String> excludeCommodities) {
        EntityWrapper detailsWrapper = new EntityWrapper();
        detailsWrapper.
                eq("pick_no", pickNo).
                eq("mail_no", mailNo).
                notIn("commodity_code", excludeCommodities).
                ne("state", StateEnum.delete.getCode());
        updateForSet(
                String.format("pick_nums = package_nums, update_user = '%s', update_time = '%t'", webUtil.operator(), DateTime.now().toDate()),
                detailsWrapper
        );
    }
}
