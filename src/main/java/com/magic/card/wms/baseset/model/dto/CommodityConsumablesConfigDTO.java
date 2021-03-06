package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 商品耗材设置表
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-21
 */
@Data
@ApiModel("商品耗材关系表")
public class CommodityConsumablesConfigDTO implements Serializable {

    private static final long serialVersionUID = 308682511021212978L;

    /**
     * 主键
     */
    @ApiModelProperty("修改关系是必须提供，否则验证不通过")
    private Long id;
    /**
     * 商品id
     */
    @NotNull(message = "商品ID不可为空")
    @ApiModelProperty("商品ID是必须提供，否则验证不通过")
    private String commodityId;
    /**
     * sku id
     */
    @ApiModelProperty("商品skuID")
    private String skuId;
    /**
     * 消耗商品id
     */
    @NotNull(message = "消耗商品ID不可为空")
    @ApiModelProperty("消耗商品ID是必须提供，否则验证不通过")
    private String useCommodityId;
    /**
     * 消耗商品 sku id
     */
    @ApiModelProperty("消耗商品SKU ID")
    private String useSkuId;
    /**
     * 左区间值
     */
    @NotNull(message = "左区间值不可为空")
    @ApiModelProperty("左区间值是必须提供，否则验证不通过")
    private Integer leftValue;
    /**
     * 右区间值
     */
    @NotNull(message = "右区间值不可为空")
    @ApiModelProperty("右区间值是必须提供，否则验证不通过")
    private Integer rightValue;
    /**
     * 消耗数量
     */
    @NotNull(message = "消耗数量不可为空")
    @ApiModelProperty("消耗数量是必须提供，否则验证不通过")
    private Integer useNums;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;


}
