package com.magic.card.wms.baseset.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.magic.card.wms.baseset.model.po.OrderCommodity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.mapper
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 14:44
 * @since : 1.0.0
 */
public interface OrderCommodityMapper extends BaseMapper<OrderCommodity> {
    /**
     * 加载订单商品
     * @param orderNo
     * @return
     */
    List<Map> loadOrderCommodityGrid(@Param("orderNo") String orderNo);

    /**
     * 加载批量订单商品
     * @param orderNos
     * @return
     */
    List<Map> loadBatchOrderCommodityGrid(@Param("orderNos") List<String> orderNos);
}
