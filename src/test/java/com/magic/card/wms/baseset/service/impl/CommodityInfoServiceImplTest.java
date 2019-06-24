package com.magic.card.wms.baseset.service.impl;

import com.magic.card.wms.MagicWmsApplicationTests;
import com.magic.card.wms.baseset.model.dto.CommodityInfoDTO;
import com.magic.card.wms.baseset.service.ICommodityInfoService;
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
 * @date : 2019/6/19/019 16:55
 * @since : 1.0.0
 */
public class CommodityInfoServiceImplTest extends MagicWmsApplicationTests {
    @Autowired
    private ICommodityInfoService commodityInfoService;

    @Test
    public void addCommodityInfo() throws BusinessException {
        CommodityInfoDTO dto = new CommodityInfoDTO();
        dto.setCommodityCode("1111111111111");
        dto.setCommodityName("飞鹤第一阶段奶粉");
        dto.setCustomerId("1");
        dto.setPackingNum(12);
        dto.setPackingUnit("箱");
        dto.setPackingVolume(new BigDecimal(0.95));
        dto.setPackingVolumeUnit("m³");
        dto.setPackingWeight(new BigDecimal(13.5));
        dto.setPackingWeightUnit("KG");
        commodityInfoService.add(dto, Constants.DEFAULT_USER);
    }

    @Test
    public void updateCommodityInfo() {
    }
}