package com.magic.card.wms.baseset.service.impl;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.CommoditySkuMapper;
import com.magic.card.wms.baseset.model.dto.CommoditySkuDTO;
import com.magic.card.wms.baseset.model.po.CommoditySku;
import com.magic.card.wms.baseset.model.vo.ExcelCommoditySkuVO;
import com.magic.card.wms.baseset.service.ICommoditySkuService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.CommodityType;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.utils.EasyExcelUtil;
import com.magic.card.wms.common.utils.PoUtil;
import com.magic.card.wms.common.utils.WebUtil;
import com.magic.card.wms.common.utils.WrapperUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        defaultColumns.put("isConsumable", "is_consumable");
        defaultColumns.put("singleWeight", "single_weight");
        defaultColumns.put("singleWeightUnit", "single_weight_unit");

        defaultColumns.put("singleWeightUnit", "single_weight_unit");
        defaultColumns.put("singleVolume", "single_volume");
        defaultColumns.put("singleVolumeUnit", "single_volume_unit");
    }

    @Autowired
    private WebUtil webUtil;

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
        WrapperUtil.autoSettingSearch(skuEntityWrapper, defaultColumns, loadGrid.getSearch());

        if (MapUtils.isNotEmpty(loadGrid.getOrder())) {
            WrapperUtil.autoSettingOrder(skuEntityWrapper, defaultColumns, loadGrid.getOrder());
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
     * 加载消耗品数据
     *
     * @return
     */
    @Override
    public Map<String, List> comboGridConsumables() {
        EntityWrapper<CommoditySku> entityWrapper = new EntityWrapper();
        entityWrapper.eq("is_consumable", 1).eq("state", StateEnum.normal.getCode());
        List<CommoditySku> commoditySkus = baseMapper.selectList(entityWrapper);

        if (CollectionUtils.isNotEmpty(commoditySkus)) {
            Map<String, List> consumables = Maps.newHashMap();
            commoditySkus.forEach(commoditySku -> {
                final String key = CommodityType.enToch(commoditySku.getCommodityType());
                if (consumables.containsKey(key)) {
                    consumables.get(key).add(commoditySku);
                } else {
                    List commodities = Lists.newLinkedList();
                    commodities.add(commoditySku);
                    consumables.put(key, commodities);
                }
            });
            return consumables;
        }

        return null;
    }

    /**
     * Excel 导入 商品信息
     *
     * @param commodityExcelFiles
     */
    @Override
    public void excelImport(MultipartFile[] commodityExcelFiles) {

        for (MultipartFile commodityExcelFile :
                commodityExcelFiles) {
            String fileName = commodityExcelFile.getOriginalFilename();
            EasyExcelUtil.validatorFileSuffix(fileName);
            try {
                List<ExcelCommoditySkuVO> excelCommoditySkuVOS = EasyExcelUtil.readExcel(commodityExcelFile.getInputStream(), ExcelCommoditySkuVO.class, 1, 1);

                if (CollectionUtils.isNotEmpty(excelCommoditySkuVOS)) {
                    List<String> barCodes = Lists.newLinkedList();
                    CommoditySku baseCommoditySku = new CommoditySku();
                    PoUtil.add(baseCommoditySku, webUtil.operator());
                    List<CommoditySku> commoditySkus = excelCommoditySkuVOS.stream().filter(excelCommoditySkuVO -> {
                        Boolean flag = StringUtils.isBlank(excelCommoditySkuVO.getBarCode());

                        if (flag) return false;

                        barCodes.add(excelCommoditySkuVO.getBarCode());
                        return true;
                    }).map(excelCommoditySkuVO -> {
                        CommoditySku commoditySku = new CommoditySku();
                        BeanUtils.copyProperties(baseCommoditySku, commoditySku);
                        BeanUtils.copyProperties(excelCommoditySkuVO, commoditySku);
                        return commoditySku;
                    }).collect(Collectors.toList());

                    EntityWrapper entityWrapper = new EntityWrapper();
                    entityWrapper.in("bar_code", barCodes).eq("state", StateEnum.normal.getCode());

                    if (selectCount(entityWrapper) > 0) {
                        throw OperationException.customException(ResultEnum.data_check_exist, "当前导入商品二维码已存在，请核实后再导入");
                    }

                    insertBatch(commoditySkus);
                }

            } catch (IOException e) {
                throw OperationException.customException(ResultEnum.upload_file_resolve_err);
            }

        }

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
