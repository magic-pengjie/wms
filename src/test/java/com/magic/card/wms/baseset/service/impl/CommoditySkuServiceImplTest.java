package com.magic.card.wms.baseset.service.impl;

import com.magic.card.wms.MagicWmsApplicationTests;
import com.magic.card.wms.baseset.model.dto.CommoditySkuDTO;
import com.magic.card.wms.baseset.service.ICommoditySkuService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.Constants;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * com.magic.card.wms.baseset.service.impl
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/19/019 15:30
 * @since : 1.0.0
 */
public class CommoditySkuServiceImplTest extends MagicWmsApplicationTests {
    @Autowired
    private ICommoditySkuService commoditySkuService;

    @Test
    public void loadGrid() {
    }

    @Test
    public void addCommoditySKU() throws BusinessException {
        CommoditySkuDTO skuDTO = new CommoditySkuDTO();
        skuDTO.setBarCode("1111111111111");  // 商品二维码
        skuDTO.setCommodityId("1111111111111"); // 商品ID 默认是使用二维码
        skuDTO.setSkuCode("1111111111111"); // 商品SKU码 没有提供默认是二维码
        skuDTO.setSkuName("飞鹤第一阶段奶粉"); // 商品SKU名称
        skuDTO.setSpec("FH-NF-1");
        skuDTO.setSingleUnit("罐");
        skuDTO.setSingleVolume(new BigDecimal(600)); // 6 L
        skuDTO.setSingleVolumeUnit("L");
        skuDTO.setSingleWeight(new BigDecimal(255)); // 255 G
        skuDTO.setSingleWeightUnit("g");
        commoditySkuService.add(skuDTO, Constants.DEFAULT_USER);
    }

    @Test
    public void updateCommoditySKU() {
    }
}