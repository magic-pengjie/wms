package com.magic.card.wms.baseset.service.impl;

import com.magic.card.wms.MagicWmsApplicationTests;
import com.magic.card.wms.baseset.model.dto.StorehouseConfigDTO;
import com.magic.card.wms.baseset.service.IStorehouseConfigService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.Constants;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * com.magic.card.wms.baseset.service.impl
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/19/019 18:59
 * @since : 1.0.0
 */
public class StorehouseConfigServiceImplTest extends MagicWmsApplicationTests {
    @Autowired
    private IStorehouseConfigService storehouseConfigService;

    @Test
    public void addStorehouseConfig() throws BusinessException {
        StorehouseConfigDTO dto = new StorehouseConfigDTO();
        dto.setStorehouseId("42"); // 设置库位
        dto.setCustomerId("1");  // 设置客户
        dto.setCommodityId("1");// 设置产品

        dto.setStoreNums(30000); // 库存量
        dto.setAvailableNums(30000); // 可用数量
        dto.setLockNums(0);

        storehouseConfigService.add(dto, Constants.DEFAULT_USER);
    }

    @Test
    public void updateStorehouseConfig() {
    }
}