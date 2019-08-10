package com.magic.card.wms.baseset.model.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;

import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.po
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/7 12:32
 * @since : 1.0.0
 */
@Data
@TableName("wms_split_package_rules")
public class SplitPackageRule extends BasePo implements Serializable {
    private static final long serialVersionUID = -5777253910637030376L;

    /**
     * 匹配规则token
     */
    private String ruleToken;
    /**
     * 是否单一商品
     */
    private int singleCommodity;
    /**
     * 是否拆包
     */
    private int isSplit;
}
