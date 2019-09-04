package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.dto.order.OrderCommodityDTO;
import com.magic.card.wms.baseset.model.dto.split.SplitCommodityDTO;
import com.magic.card.wms.baseset.model.dto.split.SplitPackageRuleDTO;
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

    /**
     * 删除
     * @param ids
     */
    void deleted(List<String> ids);

    /**
     * 创建拆包规则
     * @param splitPackageRule
     */
    void generateRule(SplitPackageRuleDTO splitPackageRule);

    /**
     * 获取订单包裹
     * @param ruleToken
     * @return
     */
    List<List<SplitCommodityDTO>> orderSplitPackages(String ruleToken);

    /**
     * 合单规则保存
     * @param mergeToken
     */
    void mergeRule(String mergeToken);
}
