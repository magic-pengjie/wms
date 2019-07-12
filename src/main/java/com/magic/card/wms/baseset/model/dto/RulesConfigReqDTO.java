package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 规则配置模型
 * @author PENGJIE
 * @date 2019年7月11日
 */
@Data
@ApiModel(value = "规则配置模型", description = "规则配置模型")
public class RulesConfigReqDTO {
	 /**
     * 规则类型
     */
	@ApiModelProperty("规则类型 not null food:食品类 ;unsalableGoods:滞销品;logistics:物流信息预警;replenishment:补货预警;inventory:库存预警;split:商品拆分")
    private String ruleType;
	/**
     * 规则类型名称
     */
	@ApiModelProperty("规则类型名称")
    private String ruleTypeName;
    /**
     * 规则名称
     */
	@ApiModelProperty("规则名称")
    private String ruleName;
    /**
     * 规则key（商品拆分使用传商品编码）
     */
	@ApiModelProperty("规则key（商品拆分使用传商品编码）")
    private String ruleKey;
    

}
