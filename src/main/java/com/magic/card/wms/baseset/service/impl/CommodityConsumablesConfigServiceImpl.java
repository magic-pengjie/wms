package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.CommodityConsumablesConfigMapper;
import com.magic.card.wms.baseset.model.dto.CommodityConsumablesConfigDTO;
import com.magic.card.wms.baseset.model.po.CommodityConsumablesConfig;
import com.magic.card.wms.baseset.service.ICommodityConsumablesConfigService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.utils.PoUtil;
import com.magic.card.wms.common.utils.WrapperUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        defaultColumns.put("commodityCode", "wccc.commodity_code");
        defaultColumns.put("useCommodityCode", "wccc.use_commodity_code");
        defaultColumns.put("skuName", "wcs.sku_name");
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
        Page page = loadGrid.generatorPage();
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("wccc.state", 1);
        WrapperUtil.autoSettingSearch(wrapper, defaultColumns, loadGrid.getSearch());

        if (MapUtils.isNotEmpty(loadGrid.getOrder())) {
            WrapperUtil.autoSettingOrder(wrapper, defaultColumns, loadGrid.getOrder());
        } else {
            wrapper.orderBy("wcsxh.sku_name");
        }

        loadGrid.finallyResult(page, this.baseMapper.loadGrid(page, wrapper));
        return loadGrid;
    }

    /**
     * 新增数据
     *
     * @param commodityConsumablesConfigDTO
     * @param operator
     */
    @Override @Transactional
    public void add(CommodityConsumablesConfigDTO commodityConsumablesConfigDTO, String operator) {

    }

    /**
     * 更新修改
     *
     * @param commodityConsumablesConfigDTO
     * @param operator
     */
    @Override @Transactional
    public void update(CommodityConsumablesConfigDTO commodityConsumablesConfigDTO, String operator) {
        PoUtil.checkId(commodityConsumablesConfigDTO.getId());
        CommodityConsumablesConfig commodityConsumablesConfig = new CommodityConsumablesConfig();
        PoUtil.update(commodityConsumablesConfigDTO, commodityConsumablesConfig, operator);

        if (this.baseMapper.updateById(commodityConsumablesConfig) < 1)
            throw OperationException.DATA_OPERATION_ADD;
    }

    /**
     * 删除信息
     *
     * @param id
     */
    @Override @Transactional
    public void delete(Long id) {

        if (this.baseMapper.deleteById(id) < 1)
            throw OperationException.DATA_OPERATION_DELETE;

    }
}
