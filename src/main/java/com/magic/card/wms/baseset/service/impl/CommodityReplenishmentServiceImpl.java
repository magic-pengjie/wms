package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.CommodityReplenishmentMapper;
import com.magic.card.wms.baseset.mapper.StorehouseConfigMapper;
import com.magic.card.wms.baseset.model.dto.ReplenishmentFinishedDTO;
import com.magic.card.wms.baseset.model.po.CommodityReplenishment;
import com.magic.card.wms.baseset.service.ICommodityReplenishmentService;
import com.magic.card.wms.baseset.service.IStorehouseConfigService;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.BillState;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.utils.WrapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
   }
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
        loadGrid.finallyResult(page, baseMapper.loadGrid(loadGrid.generatorPage(), new EntityWrapper()));
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
     * 补货完成信息
     *
     * @param replenishmentFinished
     */
    @Override
    public void replenishmentFinished(ReplenishmentFinishedDTO replenishmentFinished) {

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
