package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.CommodityConsumablesConfigMapper;
import com.magic.card.wms.baseset.model.po.CommodityConsumablesConfig;
import com.magic.card.wms.baseset.service.ICommodityConsumablesConfigService;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.utils.WrapperUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * com.magic.card.wms.baseset.service.impl
 * 商品耗材配置服务（接口实现）
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/21/021 17:14
 * @since : 1.0.0
 */
@Service
public class CommodityConsumablesConfigServiceImpl extends ServiceImpl<CommodityConsumablesConfigMapper, CommodityConsumablesConfig> implements ICommodityConsumablesConfigService {
    /**
     * 默认提供的Columns
     */
    private static Map<String, String> defaultColumns = Maps.newConcurrentMap();
    static {
        defaultColumns.put("id", "wccc.id");
        defaultColumns.put("commodityId", "wccc.commodity_id");
        defaultColumns.put("useCommodityId", "wccc.use_commodity_id");
        defaultColumns.put("customerName", "wcbi.customer_name");
        defaultColumns.put("skuName", "wcs.sku_name");
        defaultColumns.put("useCustomerName", "wcbixh.customer_name");
        defaultColumns.put("useSkuName", "wcsxh.sku_name");
        defaultColumns.put("leftVale", "wccc.left_value");
        defaultColumns.put("rightValue", "wccc.right_value");
        defaultColumns.put("userNums", "wccc.use_nums");
    }

    /**
     * 数据加载（分页搜索排序）
     *
     * @param loadGrid
     * @return
     */
    @Override
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        Page page = loadGrid.page();
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("wccc.state", 1);
        WrapperUtil.searchSet(wrapper, defaultColumns, loadGrid.getSearch());

        if (MapUtils.isNotEmpty(loadGrid.getOrder())) {
            WrapperUtil.orderSet(wrapper, defaultColumns, loadGrid.getOrder());
        } else {
            wrapper.orderBy("wcbi.customer_name");
        }

        loadGrid.finallyResult(page, this.baseMapper.loadGrid(page, wrapper));
        return loadGrid;
    }
}
