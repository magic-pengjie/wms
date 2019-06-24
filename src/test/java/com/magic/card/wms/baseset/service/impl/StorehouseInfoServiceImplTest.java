package com.magic.card.wms.baseset.service.impl;

import com.magic.card.wms.MagicWmsApplicationTests;
import com.magic.card.wms.baseset.model.dto.StorehouseInfoDTO;
import com.magic.card.wms.baseset.service.IStorehouseInfoService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * com.magic.card.wms.baseset.service.impl
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/19/019 11:16
 * @since : 1.0.0
 */
@Slf4j
public class StorehouseInfoServiceImplTest extends MagicWmsApplicationTests {
    private String[] houseCodes = {"CK-GN-CCQ", "CK-GN-JHQ", "CK-GN-HCQ", "CK-GN-BZQ", "CK-GN-THQ", "CK-GN-CPQ"};
    private String[] areaCodes = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I"
            , "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    @Autowired
    private IStorehouseInfoService storehouseInfoService;

    @Test
    public void addStorehouseInfo() throws BusinessException {
        StorehouseInfoDTO storehouseInfoDTO = new StorehouseInfoDTO();
        int i = 0, order = 0;
        for (String houseCode: houseCodes) {
            storehouseInfoDTO.setHouseCode(houseCode);
            for (int j = 0; j < 3; j++) {
                storehouseInfoDTO.setAreaCode(areaCodes[i]);

                for (int store = 1; store < 50; store++) {
                    int roadNum = RandomUtils.nextInt(1, 20);
                     storehouseInfoDTO.setStoreCode(areaCodes[i] + "-" + roadNum + "-" + store);
                     storehouseInfoDTO.setPriorityValue(order++);
                     storehouseInfoService.add(storehouseInfoDTO, Constants.DEFAULT_USER);
                }

                i++;
            }
        }
    }

    @Test
    public void updateStorehouseInfo() {
    }
}