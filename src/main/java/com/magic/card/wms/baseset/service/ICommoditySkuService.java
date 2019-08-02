package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.dto.CommoditySkuDTO;
import com.magic.card.wms.baseset.model.po.CommoditySku;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.LoadGrid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品sku表 服务类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-19
 */
public interface ICommoditySkuService extends IService<CommoditySku> {

    /**
     * 加载商品数据
     * @param loadGrid
     * @return
     */
    LoadGrid loadGrid(LoadGrid loadGrid);

    /**
     * 添加商品数据
     * @param commoditySkuDTO
     * @param operation
     * @return
     */
    void add(CommoditySkuDTO commoditySkuDTO, String operation);

    /**
     * 修改商品数据
     * @param commoditySkuDTO
     * @param operation
     * @return
     */
    void update(CommoditySkuDTO commoditySkuDTO, String operation);

    /**
     * 删除数据
     * @param id
     */
    void delete(Long id);

    /**
     * Excel 导入 商品信息
     * @param commodityExcelFiles
     */
    void excelImport(MultipartFile[] commodityExcelFiles);

    /**
     * 加载消耗品数据
     * @return
     */
    Map<String, List> comboGridConsumables();
}
