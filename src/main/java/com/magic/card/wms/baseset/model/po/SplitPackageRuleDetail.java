package com.magic.card.wms.baseset.model.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.collect.Lists;
import com.magic.card.wms.baseset.model.dto.OrderCommodityDTO;
import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * com.magic.card.wms.baseset.model.po
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/7 13:45
 * @since : 1.0.0
 */
@Data
@TableName("wms_split_package_rules_details")
public class SplitPackageRuleDetail extends BasePo implements Serializable {
    private static final long serialVersionUID = 4489087515401753104L;

    /**
     * 匹配规则token
     */
    private String ruleToken;

    /**
     * 包裹规则
     */
    private String packageToken;

    /**
     * 获取包裹商品信息
     * @return
     */
    public List<OrderCommodityDTO> getPackageCommodities() {
        ArrayList<OrderCommodityDTO> commodities = Lists.newArrayList();

        return commodities;
    }
}
