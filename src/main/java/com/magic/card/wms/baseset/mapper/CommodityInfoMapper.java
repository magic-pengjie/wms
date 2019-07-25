package com.magic.card.wms.baseset.mapper;


import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.magic.card.wms.baseset.model.po.Commodity;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-19
 */
public interface CommodityInfoMapper extends BaseMapper<Commodity> {


    /**
     * 查询商品信息
     * @param commodityCode 商品条形码
     * @param customerCode  客户编码
     * @return
     */
    Commodity selectCommodity(@Param("commodityCode")String commodityCode,@Param("customerCode")String customerCode);
}
