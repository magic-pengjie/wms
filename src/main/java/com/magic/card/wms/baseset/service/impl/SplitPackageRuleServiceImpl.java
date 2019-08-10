package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.magic.card.wms.baseset.mapper.SplitPackageRuleMapper;
import com.magic.card.wms.baseset.model.dto.OrderCommodityDTO;
import com.magic.card.wms.baseset.model.dto.split.SplitCommodityDTO;
import com.magic.card.wms.baseset.model.dto.split.SplitPackageRuleDTO;
import com.magic.card.wms.baseset.model.po.SplitPackageRule;
import com.magic.card.wms.baseset.model.po.SplitPackageRuleDetail;
import com.magic.card.wms.baseset.service.ISplitPackageRuleDetailService;
import com.magic.card.wms.baseset.service.ISplitPackageRuleService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.utils.PoUtil;
import com.magic.card.wms.common.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    @Autowired
    private WebUtil webUtil;

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

    @Override
    public void deleted(List<String> ids) {
        deleted(ids);
    }

    /**
     * 创建拆包规则
     *
     * @param splitPackageRule
     */
    @Override @Transactional
    public void generateRule(SplitPackageRuleDTO splitPackageRule) {
        List<SplitCommodityDTO> orderCommodities = splitPackageRule.getOrderCommodities();
        final String ruleToke = splitPackageRule.splitPackageToken();
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("rule_token", ruleToke).eq("state", StateEnum.normal.getCode());
        SplitPackageRule packageRule = selectOne(wrapper);

        if (packageRule != null) {
            throw OperationException.customException(ResultEnum.split_rule_exist);
        }
        packageRule = new SplitPackageRule();
        packageRule.setRuleToken(ruleToke);
        packageRule.setIsSplit(1);
        packageRule.setSingleCommodity(orderCommodities.size() == 1 ? 1 : 0);
        PoUtil.add(packageRule, webUtil.operator());

        insert(packageRule);

        List<List<SplitCommodityDTO>> packageCommodities = splitPackageRule.getPackageCommodities();

        if (CollectionUtils.isEmpty(packageCommodities)) return;

        SplitPackageRuleDetail baseSplitPackageRuleDetail = new SplitPackageRuleDetail();
        PoUtil.add(baseSplitPackageRuleDetail, webUtil.operator());
        ArrayList<SplitPackageRuleDetail> splitPackageRuleDetails = Lists.newArrayList();
        packageCommodities.forEach(splitCommodityDTOS -> splitCommodityDTOS.forEach(splitCommodityDTO -> {
            SplitPackageRuleDetail splitPackageRuleDetail = new SplitPackageRuleDetail();
            BeanUtils.copyProperties(baseSplitPackageRuleDetail, splitPackageRuleDetail);
            splitPackageRuleDetail.setRuleToken(ruleToke);
            splitPackageRuleDetail.setPackageToken(splitCommodityDTO.commodityToken());
            splitPackageRuleDetails.add(splitPackageRuleDetail);
        }));

        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(splitPackageRuleDetails)) {
            splitPackageRuleDetailService.insertBatch(splitPackageRuleDetails);
        }
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
