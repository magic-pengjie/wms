package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.CommodityReplenishmentMapper;
import com.magic.card.wms.baseset.mapper.StorehouseConfigMapper;
import com.magic.card.wms.baseset.model.dto.CommodityReplenishmentDTO;
import com.magic.card.wms.baseset.model.po.CommodityReplenishment;
import com.magic.card.wms.baseset.service.ICommodityReplenishmentService;
import com.magic.card.wms.baseset.service.IStorehouseConfigService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.BillState;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.model.enums.StoreTypeEnum;
import com.magic.card.wms.common.utils.PoUtil;
import com.magic.card.wms.common.utils.WebUtil;
import com.magic.card.wms.common.utils.WrapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * com.magic.card.wms.baseset.service.impl
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/12 15:48
 * @since : 1.0.0
 */
@Slf4j
@Service
public class CommodityReplenishmentServiceImpl extends ServiceImpl<CommodityReplenishmentMapper, CommodityReplenishment> implements ICommodityReplenishmentService {
   public static final Map<String, String> DEFAULT_COLUMNS = Maps.newConcurrentMap();
   static {
       DEFAULT_COLUMNS.put("id", "wcr.id");
       DEFAULT_COLUMNS.put("replenishmentNo", "wcr.replenishment_no");
       DEFAULT_COLUMNS.put("commodityId", "wcr.commodity_id");
       DEFAULT_COLUMNS.put("storageId", "wcr.storage_id");
       DEFAULT_COLUMNS.put("checkoutId", "wcr.checkout_id");
       DEFAULT_COLUMNS.put("stockoutNums", "wcr.stockout_nums");
       DEFAULT_COLUMNS.put("replenishmentNums", "wcr.replenishment_nums");
       DEFAULT_COLUMNS.put("isFinally", "wcr.is_finally");
       DEFAULT_COLUMNS.put("processStage", "wcr.process_stage");
       DEFAULT_COLUMNS.put("state", "wcr.state");
       DEFAULT_COLUMNS.put("skuName", "wcs.sku_name");
       DEFAULT_COLUMNS.put("commodityCode", "wci.commodity_code");
       DEFAULT_COLUMNS.put("storeCode", "wsi.store_code");
       DEFAULT_COLUMNS.put("modelNo", "wcs.model_no");
       DEFAULT_COLUMNS.put("singleUnit", "wcs.single_unit");
       DEFAULT_COLUMNS.put("spec", "wcs.spec");
       DEFAULT_COLUMNS.put("createTime", "wcr.create_time");
       DEFAULT_COLUMNS.put("customerName", "wcbi.customer_name");
   }

   @Autowired
   private WebUtil webUtil;
    @Resource
    private StorehouseConfigMapper storehouseConfigMapper;
    @Autowired
    private IStorehouseConfigService storehouseConfigService;

    /**
     * 分页查询排序数据加载
     *
     * @param loadGrid
     * @return
     */
    @Override
    public void loadGrid(LoadGrid loadGrid) {
        Page page = loadGrid.generatorPage();
        EntityWrapper entityWrapper = new EntityWrapper();
        WrapperUtil.autoSettingSearch(entityWrapper, DEFAULT_COLUMNS, loadGrid.getSearch());
        WrapperUtil.autoSettingOrder(entityWrapper, DEFAULT_COLUMNS, loadGrid.getOrder(), defaultSetOrder-> defaultSetOrder.orderBy("wcr.create_time"));
        List<Map> maps = baseMapper.loadGrid(page, entityWrapper);

        if (CollectionUtils.isNotEmpty(maps)) {
            maps.forEach(map -> {
                final String replenishmentNo = MapUtils.getString(map, "replenishmentNo");
                map.put("ccStores", baseMapper.loadGStorehouse(replenishmentNo, StoreTypeEnum.CCQ.getCode()));
            });
        }

        loadGrid.finallyResult(page, maps);
    }

    /**
     * 补货单清单
     *
     * @param ids
     * @return
     */
    @Override
    public List<Map> replenishmentList(List<String> ids) {
        EntityWrapper<CommodityReplenishment> wrapper = new EntityWrapper<>();
        wrapper.
                in("wcr.id", ids).
                eq("wcr.state", StateEnum.normal.getCode()).
                eq("wcr.process_stage", BillState.replenishment_process_create.getCode());
        List<Map> commodityReplenishments = baseMapper.loadGrid(null, wrapper);

        if (CollectionUtils.isNotEmpty(commodityReplenishments)) {
            HashMap<String, Map> replenishmentInfo = Maps.newHashMap();
            commodityReplenishments.stream().filter(commodityReplenishment -> {
                final String storeCode = MapUtils.getString(commodityReplenishment, "storeCode");

                if (replenishmentInfo.containsKey(storeCode)) {
                    Map map = replenishmentInfo.get(storeCode);
                    return false;
                } else {
                    replenishmentInfo.put(storeCode, commodityReplenishment);
                }
              return true;
            }).collect(Collectors.toList());

        }

        return null;
    }

    /**
     * 加载补货单推荐的存储库位信息
     *
     * @param replenishmentNo
     * @return
     */
    @Override
    public List<Map> replenishmentStorehouse(String replenishmentNo) {
        return null;
    }

    /**
     * 执行补货操作
     *
     * @param commodityReplenishments
     */
    @Override @Transactional
    public void executorReplenishmentOperation(List<CommodityReplenishment> commodityReplenishments) {
        List<String> checkoutIds = commodityReplenishments.
                stream().
                map(commodityReplenishment -> commodityReplenishment.getCheckoutId()).
                collect(Collectors.toList());

        EntityWrapper<CommodityReplenishment> wrapper = new EntityWrapper();
        wrapper.eq("is_finally", 0).in("checkout_id", checkoutIds);

        List<CommodityReplenishment> updateCommodityReplenishment = selectList(wrapper);

        if (CollectionUtils.isNotEmpty(updateCommodityReplenishment)) {
            List<CommodityReplenishment> removes = Lists.newArrayList();

            updateCommodityReplenishment.forEach(commodityReplenishment -> {
                int index = checkoutIds.indexOf(commodityReplenishment.getCheckoutId());
                if (index >= 0) {
                    CommodityReplenishment replenishment = commodityReplenishments.get(index);
                    commodityReplenishment.setStockoutNums(replenishment.getStockoutNums());
                    commodityReplenishment.setUpdateTime(new Date());
                    removes.add(replenishment);
                }
            });

            if (CollectionUtils.isNotEmpty(removes)) {
                commodityReplenishments.removeAll(removes);
            }

            updateBatchById(updateCommodityReplenishment);
        }

        if (CollectionUtils.isNotEmpty(commodityReplenishments)) {
            insertBatch(commodityReplenishments);
        }
    }

    /**
     * 获取存储库位
     *
     * @param replenishmentNo
     * @return
     */
    @Override
    public List<Map> loadStorehouse(String replenishmentNo) {
        return baseMapper.loadGStorehouse(replenishmentNo, StoreTypeEnum.CCQ.getCode());
    }

    /**
     * 补货完成信息
     *
     * @param replenishmentFinished
     */
    @Override @Transactional
    public void replenishmentFinished(CommodityReplenishmentDTO replenishmentFinished) {
        EntityWrapper<CommodityReplenishment> wrapper = new EntityWrapper<>();
        wrapper.eq("replenishment_no", replenishmentFinished.getReplenishmentNo()).eq("is_finally", 0);
        CommodityReplenishment commodityReplenishment = selectOne(wrapper);
        // 获取补货单信息
        if (commodityReplenishment == null) {
            throw OperationException.customException(ResultEnum.replenishment_no_exist);
        }

        commodityReplenishment.setIsFinally(1);
        PoUtil.update(commodityReplenishment, webUtil.operator());

        if (CollectionUtils.isNotEmpty(replenishmentFinished.getReplenishmentInfos())) {
            int replenishmentNums = replenishmentFinished.getReplenishmentInfos().stream().mapToInt((CommodityReplenishmentDTO.ReplenishmentInfo replenishmentInfo) -> {

                commodityReplenishment.setReplenishmentNums(
                        replenishmentInfo.getNums() + commodityReplenishment.getReplenishmentNums()
                );

                if (StringUtils.isBlank(commodityReplenishment.getStorageId())) {
                    commodityReplenishment.setStorageId("" + replenishmentInfo.getId());
                } else {
                    commodityReplenishment.setStorageId(
                            StringUtils.joinWith(",",
                                    commodityReplenishment.getStorageId(),
                                    replenishmentInfo.getId()
                            )
                    );
                }

                if (StringUtils.isBlank(commodityReplenishment.getReplenishmentRecord())) {
                    commodityReplenishment.setReplenishmentRecord("" + replenishmentInfo.getNums());
                } else {
                    commodityReplenishment.setReplenishmentRecord(
                            StringUtils.joinWith(",",
                                    commodityReplenishment.getReplenishmentRecord(),
                                    replenishmentInfo.getNums()
                            )
                    );
                }

                String setString = String.format("available_nums = available_nums - %s", replenishmentInfo.getNums());
                storehouseConfigService.updateForSet(setString, new EntityWrapper().eq("id", replenishmentInfo.getId()));
                return replenishmentInfo.getNums();
            }).sum();
            // 增加拣货区库位库存
            String plusSet = String.format("available_nums = available_nums + %s", replenishmentNums);
            storehouseConfigService.updateForSet(plusSet, new EntityWrapper().eq("id", commodityReplenishment.getCheckoutId()));
            // 更新补货信息
            updateById(commodityReplenishment);
        }
    }

    /**
     * 增加库存不足补货信息
     * @param storeCode 零拣库位编号
     * @param stockoutNums 商品缺少量
     */
    @Override
    public void addReplenishmentNotice(String storeCode, Integer stockoutNums) {

    }

    /**
     * 拣货区补货数据录入
     * @param storeCode 库位编号
     * @param commodityCode  商品code
     * @param replenishmentNums 补货数量
     */
    public void processReplenishment(String storeCode, String commodityCode, Integer replenishmentNums) {

    }
}
