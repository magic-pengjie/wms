package com.magic.card.wms.baseset.service.impl;

import com.magic.card.wms.MagicWmsApplicationTests;
import com.magic.card.wms.baseset.model.dto.DictInfoDTO;
import com.magic.card.wms.baseset.service.IDictInfoService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * com.magic.card.wms.baseset.service.impl
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/18/018 18:37
 * @since : 1.0.0
 */
@Slf4j
public class DictInfoServiceImplTest extends MagicWmsApplicationTests {

    @Autowired
    private IDictInfoService dictInfoService;

    @Test
    public void loadGrid() {
    }

    @Test
    public void loadChild() {
    }

    @Test
    public void addDictInfo() throws BusinessException {
        DictInfoDTO ckgn = new DictInfoDTO();

        ckgn.setDictType("WMC_CC");
        ckgn.setDictTypeName("仓储系统常量");
        ckgn.setDictCode("CK-GN");
        ckgn.setDictName("仓库功能属性");
        dictInfoService.add(ckgn, Constants.DEFAULT_USER);


        ckgn.setDictTypeP("WMC_CC");
        ckgn.setDictTypeNameP("仓储系统常量");
        ckgn.setDictCodeP("CK-GN");
        ckgn.setDictNameP("仓库功能属性");

        ckgn.setDictType("WMC_CC");
        ckgn.setDictTypeName("仓储系统常量");
        ckgn.setDictCode("CK-GN-KDZCQ");
        ckgn.setDictName("快递暂存区");

        dictInfoService.add(ckgn, Constants.DEFAULT_USER);

        ckgn.setDictCode("CK-GN-DRCQ");
        ckgn.setDictName("待入仓区");

        dictInfoService.add(ckgn, Constants.DEFAULT_USER);

        ckgn.setDictCode("CK-GN-CCQ");
        ckgn.setDictName("存储区");

        dictInfoService.add(ckgn, Constants.DEFAULT_USER);

        ckgn.setDictCode("CK-GN-JHQ");
        ckgn.setDictName("拣货区");

        dictInfoService.add(ckgn, Constants.DEFAULT_USER);

        ckgn.setDictCode("CK-GN-HCQ");
        ckgn.setDictName("缓存区");

        dictInfoService.add(ckgn, Constants.DEFAULT_USER);

        ckgn.setDictCode("CK-GN-BZQ");
        ckgn.setDictName("包装区");

        dictInfoService.add(ckgn, Constants.DEFAULT_USER);

        ckgn.setDictCode("CK-GN-THQ");
        ckgn.setDictName("退货区");

        dictInfoService.add(ckgn, Constants.DEFAULT_USER);

        ckgn.setDictCode("CK-GN-CPQ");
        ckgn.setDictName("次品区");

        dictInfoService.add(ckgn, Constants.DEFAULT_USER);

    }

    @Test
    public void updateDictInfo() throws BusinessException {
        DictInfoDTO ckgn = new DictInfoDTO();
        ckgn.setDictTypeName("仓储系统常量123");
        ckgn.setId(1l);

        dictInfoService.update(ckgn, Constants.DEFAULT_USER);
    }
}