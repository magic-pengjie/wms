package com.magic.card.wms.baseset.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.po.CommodityConsumablesConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.mapper
 * 商品耗材表 Mapper 接口
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/21/021 16:31
 * @since : 1.0.0
 */
public interface CommodityConsumablesConfigMapper extends BaseMapper<CommodityConsumablesConfig> {

    /**
     *
     * @param page
     * @param wrapper
     * @return
     */
    List<Map> loadGrid(Page page, @Param("ew")Wrapper wrapper);

}
