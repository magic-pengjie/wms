package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.SplitPackageRuleDetailMapper;
import com.magic.card.wms.baseset.model.dto.order.OrderCommodityDTO;
import com.magic.card.wms.baseset.model.po.SplitPackageRuleDetail;
import com.magic.card.wms.baseset.service.ISplitPackageRuleDetailService;
import com.magic.card.wms.common.model.enums.StateEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * com.magic.card.wms.baseset.service.impl
 * 拆包规则匹配包裹详情
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/7 13:59
 * @since : 1.0.0
 */
@Slf4j
@Service
public class SplitPackageRuleDetailServiceImpl extends ServiceImpl<SplitPackageRuleDetailMapper,
        SplitPackageRuleDetail> implements ISplitPackageRuleDetailService {
    /**
     * 订单包裹信息
     *
     * @param ruleToken
     * @param orderCommodities
     * @return
     */
    @Override
    public List<List<OrderCommodityDTO>> orderPackages(String ruleToken, List<OrderCommodityDTO> orderCommodities) {
        EntityWrapper<SplitPackageRuleDetail> wrapper = new EntityWrapper<>();
        wrapper.eq("rule_token", ruleToken).eq("state", StateEnum.normal.getCode());
        List<SplitPackageRuleDetail> splitPackageRuleDetails = selectList(wrapper);

        if (CollectionUtils.isNotEmpty(splitPackageRuleDetails)) {
            HashMap<String, OrderCommodityDTO> commodityMaps = Maps.newHashMap();
            orderCommodities.forEach(orderCommodityDTO -> {
                final String commodityCode = orderCommodityDTO.getBarCode();
                if (!commodityMaps.containsKey(commodityCode)) {
                    commodityMaps.put(commodityCode, orderCommodityDTO);
                }
            });

            ArrayList<List<OrderCommodityDTO>> orderPackages = Lists.newArrayList();
            splitPackageRuleDetails.forEach(splitPackageRuleDetail -> {
                String[] commoditiesToken = StringUtils.split(splitPackageRuleDetail.getPackageToken(), ",");
                ArrayList<OrderCommodityDTO> commodities = Lists.newArrayList();
                for (int i = 0; i < commoditiesToken.length; i++) {
                    String[] commodityInfo = StringUtils.split(commoditiesToken[i], "+");
                    if (commodityInfo != null && commodityInfo.length == 2) {
                        final String commodityCode = commodityInfo[0];
                        final Integer nums = Integer.parseInt(commodityInfo[1]);
                        OrderCommodityDTO baseOrderCommodity = commodityMaps.get(commodityCode);
                        OrderCommodityDTO orderCommodityDTO = new OrderCommodityDTO();
                        BeanUtils.copyProperties(baseOrderCommodity, orderCommodityDTO);
                        orderCommodityDTO.setNumbers(nums);
                        commodities.add(orderCommodityDTO);
                    }
                }
                orderPackages.add(commodities);
            });

            return orderPackages;


        }

        return null;
    }
}
