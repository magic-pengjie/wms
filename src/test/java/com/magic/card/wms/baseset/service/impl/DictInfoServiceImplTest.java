package com.magic.card.wms.baseset.service.impl;

import com.google.common.collect.Maps;
import com.magic.card.wms.MagicWmsApplicationTests;
import com.magic.card.wms.baseset.model.dto.DictInfoDTO;
import com.magic.card.wms.baseset.service.IDictInfoService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

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

    private Map<String, String> ar_first = Maps.newTreeMap();

    {
        ar_first.put("110000BJ", "北京市");
        ar_first.put("120000TJ", "天津市");
        ar_first.put("130000HB", "河北省");
        ar_first.put("140000SX", "山西省");
        ar_first.put("150000NM", "内蒙古自治区");
        ar_first.put("210000LN", "辽宁省");
        ar_first.put("220000JL", "吉林省");
        ar_first.put("230000HL", "黑龙江省");
        ar_first.put("310000SH", "上海市");
        ar_first.put("320000JS", "江苏省");
        ar_first.put("330000ZJ", "浙江省");
        ar_first.put("340000AH", "安徽省");
        ar_first.put("350000FJ", "福建省");
        ar_first.put("360000JX", "江西省");
        ar_first.put("370000SD", "山东省");
        ar_first.put("410000HA", "河南省");
        ar_first.put("420000HB", "湖北省");
        ar_first.put("430000HN", "湖南省");
        ar_first.put("440000GD", "广东省");
        ar_first.put("450000GX", "广西壮族自治区");
        ar_first.put("460000HI", "海南省");
        ar_first.put("510000SC", "四川省");
        ar_first.put("520000GZ", "贵州省");
        ar_first.put("530000YN", "云南省");
        ar_first.put("540000XZ", "西藏自治区");
        ar_first.put("500000CQ", "重庆市");
        ar_first.put("610000SN", "陕西省");
        ar_first.put("620000GS", "甘肃省");
        ar_first.put("630000QH", "青海省");
        ar_first.put("640000NX", "宁夏回族自治区");
        ar_first.put("650000XJ", "新疆维吾尔自治区");
        ar_first.put("810000HK", "香港特别行政区");
        ar_first.put("820000Mo", "澳门特别行政区");
        ar_first.put("830000Tw", "台湾省");

    }

    @Autowired
    private IDictInfoService dictInfoService;

    @Test
    public void loadGrid() {
    }

    @Test
    public void loadChild() {
        DictInfoDTO ar = new DictInfoDTO();
        ar.setDictType("CH_AREA");
        ar.setDictTypeName("中国区域");
        ar.setDictCode("CH_AREA_AR");
        ar.setDictName("行政区");
        dictInfoService.add(ar, Constants.DEFAULT_USER);

        ar.setDictTypeP("CH_AREA");
        ar.setDictTypeNameP("仓储系统常量");
        ar.setDictCodeP("CH_AREA_AR");
        ar.setDictNameP("行政区");

        ar.setDictType("AR_FIRST");
        ar.setDictTypeName("一级行政区");

        ar_first.forEach((key,value) -> {
            ar.setDictCode(key);
            ar.setDictName(value);
            dictInfoService.add(ar, Constants.DEFAULT_USER);
        });
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