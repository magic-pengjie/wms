package com.magic.card.wms.baseset.model.dto.split;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * com.magic.card.wms.baseset.model.dto
 * 拆包规则
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/8 14:45
 * @since : 1.0.0
 */
@Data
@ApiModel("拆包模型数据")
public class SplitPackageRuleDTO implements Serializable {
    private static final long serialVersionUID = -4571694547025409381L;
    @ApiModelProperty("订单商品数据")
    @Size(min = 1, message = "商品数据不可为空")
    private List<SplitCommodityDTO> orderCommodities;
    @ApiModelProperty("订单拆分包裹商品数据")
    private List<List<SplitCommodityDTO>> packageCommodities;

    /**
     * 获取拆包TOKEN
     * @return
     */
    public String splitPackageToken() {
        List<String> tokens = Lists.transform(orderCommodities, orderCommodity ->
                orderCommodity.commodityToken()
        );
        tokens = tokens.stream().sorted().collect(Collectors.toList());
        return StringUtils.join(tokens, ",");
    }

    /**
     * 获取包裹TOKEN
      * @return
     */
    public List<String> splitPackageDetailToken() {
        ArrayList<String> tokens = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(packageCommodities)) {
            packageCommodities.forEach(
                    splitCommodityDTOS -> {

                        if (CollectionUtils.isEmpty(splitCommodityDTOS)) return;

                        List<String> commodities = Lists.transform(splitCommodityDTOS, orderCommodity ->
                               orderCommodity.commodityToken()
                        ).stream().sorted().collect(Collectors.toList());
                        tokens.add(StringUtils.join(commodities, ","));
                    }
            );
        }
        return tokens;
    }

}
