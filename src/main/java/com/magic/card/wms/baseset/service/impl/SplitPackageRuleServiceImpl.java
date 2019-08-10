package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.magic.card.wms.baseset.mapper.SplitPackageRuleMapper;
import com.magic.card.wms.baseset.model.dto.OrderCommodityDTO;
import com.magic.card.wms.baseset.model.po.SplitPackageRule;
import com.magic.card.wms.baseset.service.ISplitPackageRuleDetailService;
import com.magic.card.wms.baseset.service.ISplitPackageRuleService;
import com.magic.card.wms.common.model.enums.StateEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * com.magic.card.wms.baseset.service.impl
 * 拆包规则service
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/7 13:58
 * @since : 1.0.0
 */
@Slf4j
@Service
public class SplitPackageRuleServiceImpl extends ServiceImpl<SplitPackageRuleMapper,
        SplitPackageRule> implements ISplitPackageRuleService {
    @Autowired
    private ISplitPackageRuleDetailService splitPackageRuleDetailService;

    /**
     * 订单商品数据分包处理
     *
     * @param orderCommodities
     * @return
     */
    @Override
    public List<List<OrderCommodityDTO>> orderSplitPackages(List<OrderCommodityDTO> orderCommodities) {
        String ruleToken = generatorToken(orderCommodities);
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("rule_token", ruleToken).eq("state", StateEnum.normal.getCode());
        SplitPackageRule splitPackageRule = selectOne(wrapper);

        if (splitPackageRule != null) {

            if (splitPackageRule.getIsSplit() == 0) {
                List<List<OrderCommodityDTO>> packages = Lists.newArrayList();
                packages.add(orderCommodities);
                return packages;
            } else {
                return splitPackageRuleDetailService.orderPackages(ruleToken, orderCommodities);
            }

        }

        return null;
    }

    /**
     * 获取RULE TOKEN
     * @param orderCommodities
     * @return
     */
    private String generatorToken(List<OrderCommodityDTO> orderCommodities) {
        List<String> tokens = Lists.transform(orderCommodities, orderCommodity ->
                StringUtils.joinWith("+", orderCommodity.getBarCode(), orderCommodity.getNumbers())
        );
        tokens = tokens.stream().sorted().collect(Collectors.toList());
        return StringUtils.join(tokens, ",");
    }
}
