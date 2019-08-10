package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.dto.OrderCommodityDTO;
import com.magic.card.wms.baseset.model.po.SplitPackageRuleDetail;

import java.util.List;

/**
 * com.magic.card.wms.baseset.service
 * 拆包规则 包裹详情
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/7 13:56
 * @since : 1.0.0
 */
public interface ISplitPackageRuleDetailService extends IService<SplitPackageRuleDetail> {

    /**
     * 订单包裹信息
     * @param ruleToken
     * @param orderCommodities
     * @return
     */
    List<List<OrderCommodityDTO>> orderPackages(String ruleToken, List<OrderCommodityDTO> orderCommodities);
}
