package com.magic.card.wms.baseset.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.po.CommodityReplenishment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.mapper
 * 商品补货 Mapper
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/12 15:44
 * @since : 1.0.0
 */
public interface CommodityReplenishmentMapper extends BaseMapper<CommodityReplenishment> {

    /**
     * 检出商品库位的基本配置信息
     * @param storeCode
     * @return
     */
    Map checkoutStorehouseConfigInfo(String storeCode);

    /**
     * 加载补货信息数据
     * @param generatorPage
     * @param entityWrapper
     * @return
     */
    List<Map> loadGrid(Page generatorPage, @Param("ew") EntityWrapper entityWrapper);
}
