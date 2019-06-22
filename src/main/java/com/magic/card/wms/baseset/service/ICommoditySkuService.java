package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.dto.CommoditySkuDTO;
import com.magic.card.wms.baseset.model.po.CommoditySku;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.LoadGrid;

import java.util.List;

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
    Boolean addCommoditySKU(CommoditySkuDTO commoditySkuDTO, String operation);

    /**
     * 修改商品数据
     * @param commoditySkuDTO
     * @param operation
     * @return
     */
    Boolean updateCommoditySKU(CommoditySkuDTO commoditySkuDTO, String operation);
}
