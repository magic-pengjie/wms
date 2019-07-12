package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.CommoditySkuMapper;
import com.magic.card.wms.baseset.model.dto.CommoditySkuDTO;
import com.magic.card.wms.baseset.model.po.CommoditySku;
import com.magic.card.wms.baseset.service.ICommoditySkuService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.utils.PoUtil;
import com.magic.card.wms.common.utils.WrapperUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品sku表 服务实现类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-19
 */
@Service
public class CommoditySkuServiceImpl extends ServiceImpl<CommoditySkuMapper, CommoditySku> implements ICommoditySkuService {
    /**
     * 默认提供的Columns
     */
    private static Map<String, String> defaultColumns = Maps.newConcurrentMap();
    static {
        defaultColumns.put("id", "id");
        defaultColumns.put("skuCode", "sku_code");
        defaultColumns.put("skuName", "sku_name");
        defaultColumns.put("commodityId", "commodity_id");
        defaultColumns.put("spec", "spec");
        defaultColumns.put("barCode", "bar_code");
        defaultColumns.put("modelNo", "model_no");
        defaultColumns.put("singleUnit", "single_unit");
        defaultColumns.put("singleWeight", "single_weight");
        defaultColumns.put("singleWeightUnit", "single_weight_unit");

        defaultColumns.put("singleWeightUnit", "single_weight_unit");
        defaultColumns.put("singleVolume", "single_volume");
        defaultColumns.put("singleVolumeUnit", "single_volume_unit");
    }

    /**
     * 加载商品数据
     *
     * @param loadGrid
     * @return
     */
    @Override
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        Page<CommoditySku> skuPage = loadGrid.generatorPage();
        EntityWrapper<CommoditySku> skuEntityWrapper = new EntityWrapper<>();

        skuEntityWrapper.eq("state", 1);
        WrapperUtil.searchSet(skuEntityWrapper, defaultColumns, loadGrid.getSearch());

        if (MapUtils.isNotEmpty(loadGrid.getOrder())) {
            WrapperUtil.orderSet(skuEntityWrapper, defaultColumns, loadGrid.getOrder());
        } else {
            skuEntityWrapper.orderBy("update_time", false);
        }


        List<Map<String, Object>> list = baseMapper.selectMapsPage(skuPage, skuEntityWrapper);

        return LoadGrid.instance(skuPage, list);
    }

    /**
     * 添加商品数据
     *
     * @param commoditySkuDTO
     * @param operation
     * @return
     */
    @Override @Transactional
    public void add(CommoditySkuDTO commoditySkuDTO, String operation) {
        checkCommoditySKU(commoditySkuDTO, false);
        CommoditySku sku = new CommoditySku();
        PoUtil.add(commoditySkuDTO, sku, Constants.DEFAULT_USER);

        if (this.baseMapper.insert(sku) < 1)
            throw OperationException.DATA_OPERATION_ADD;

    }

    /**
     * 修改商品数据
     *
     * @param commoditySkuDTO
     * @param operation
     * @return
     */
    @Override @Transactional
    public void update(CommoditySkuDTO commoditySkuDTO, String operation) {
        checkCommoditySKU(commoditySkuDTO, true);
        CommoditySku sku = new CommoditySku();
        PoUtil.update(commoditySkuDTO, sku, Constants.DEFAULT_USER);

        if (this.baseMapper.updateById(sku) < 1)
            throw OperationException.DATA_OPERATION_UPDATE;

    }

    /**
     * 删除数据
     *
     * @param id
     */
    @Override @Transactional
    public void delete(Long id) {

        if (this.baseMapper.deleteById(id) < 1)
            throw OperationException.DATA_OPERATION_DELETE;

    }

    /**
     * 添加修改前CommoditySKU数据检查
     * @param commoditySkuDTO
     * @param updateOperation
     */
    private void checkCommoditySKU(CommoditySkuDTO commoditySkuDTO, Boolean updateOperation) {

        if (updateOperation)
            PoUtil.checkId(commoditySkuDTO.getId());

        // 检测 商品二维码是否已存在数据库
        EntityWrapper<CommoditySku> skuEntityWrapper = new EntityWrapper<>();
        skuEntityWrapper.eq("bar_code", commoditySkuDTO.getBarCode());

        if (updateOperation) {
            skuEntityWrapper.ne("id", commoditySkuDTO.getId());
        }

        if (this.selectCount(skuEntityWrapper) > 0) {
            throw OperationException.customException(ResultEnum.data_check_exist, "当前商品的二维码已存在");
        }

    }
}
