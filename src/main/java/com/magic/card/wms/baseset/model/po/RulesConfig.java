package com.magic.card.wms.baseset.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 规则配置
 * </p>
 *
 * @author pengjie
 * @since 2019-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wms_rules_config")
public class RulesConfig extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 规则类型
     */
    private String ruleType;
    /**
     * 规则类型名称
     */
    private String ruleTypeName;
    /**
     * 规则名称
     */
    private String ruleName;
    
    /**
     * 规则key（商品拆分使用传商品编码）
     */
    private String ruleKey;
    /**
     * 左侧规则值
     */
    private String leftValue;
    /**
     * 左侧比较符
     */
    private String leftSymbol;
    /**
     * 右侧规则值
     */
    private String rightValue;
    /**
     * 右侧比较符
     */
    private String rightSymbol;
    /**
     * 计算公式
     */
    private String calcFormulas;

}
