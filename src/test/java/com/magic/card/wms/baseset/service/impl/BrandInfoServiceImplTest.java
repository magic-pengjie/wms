package com.magic.card.wms.baseset.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.MagicWmsApplicationTests;
import com.magic.card.wms.baseset.model.dto.BrandInfoDTO;
import com.magic.card.wms.baseset.service.IBrandInfoService;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * com.magic.card.wms.baseset.service.impl
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/18/018 15:52
 * @since : 1.0.0
 */
@Slf4j
public class BrandInfoServiceImplTest extends MagicWmsApplicationTests {

    @Autowired
    private IBrandInfoService brandInfoService;


    @Test
    public void loadGrid() {
//        LoadGrid list = brandInfoService.loadGrid(new Page(1, 2), "");
//        log.info("查询数据： {}", JSONArray.toJSONString(list));
    }

    @Test
    public void addBrandInfo() {
        BrandInfoDTO brandInfoDTO = new BrandInfoDTO();
        brandInfoDTO.setName("全友家私");
        brandInfoService.addBrandInfo(brandInfoDTO, Constants.DEFAULT_USER);
        brandInfoDTO.setName("绿色树漆");
        brandInfoService.addBrandInfo(brandInfoDTO, Constants.DEFAULT_USER);
        brandInfoDTO.setName("苏北大米");
        brandInfoService.addBrandInfo(brandInfoDTO, Constants.DEFAULT_USER);
        brandInfoDTO.setName("英格兰快乐奶");
        brandInfoService.addBrandInfo(brandInfoDTO, Constants.DEFAULT_USER);

    }

    @Test
    public void updateBrandInfo() {
        BrandInfoDTO brandInfoDTO = new BrandInfoDTO();
        brandInfoDTO.setName("全友家私123");
        brandInfoDTO.setId(1l);
        brandInfoService.updateBrandInfo(brandInfoDTO, Constants.DEFAULT_USER);
    }

    @Test
    public void deleteBrandInfo() {
//        brandInfoService.deleteBrandInfo(1l, Constants.DEFAULT_USER, false);
//        brandInfoService.deleteBrandInfo(1l, Constants.DEFAULT_USER, true);
    }
}