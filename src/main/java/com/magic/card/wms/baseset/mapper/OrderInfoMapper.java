package com.magic.card.wms.baseset.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.po.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.mapper
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 14:43
 * @since : 1.0.0
 */
public interface OrderInfoMapper extends BaseMapper<Order> {
    List<Map> orderCommodityWeightMap(@Param("orderNo") String orderNo,@Param("customerCode") String customerCode);

    List<Map> loadGrid(Page page, @Param("ew")Wrapper entityWrapper);

    List<Map> orderPackage(@Param("orderNo") String orderNO);
}
