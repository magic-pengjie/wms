package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.dto.OrderCommodityDTO;
import com.magic.card.wms.baseset.model.po.SplitPackageRule;

import java.util.List;

/**
 * com.magic.card.wms.baseset.service
 * 拆包规则
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/7 13:55
 * @since : 1.0.0
 */
public interface ISplitPackageRuleService extends IService<SplitPackageRule> {
    /**
     * 订单商品数据分包处理
     * @param orderCommodities
     * @return
     */
    List<List<OrderCommodityDTO>> orderSplitPackages(List<OrderCommodityDTO> orderCommodities);

    void add();
    void deleted();
}
