package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * com.magic.card.wms.baseset.model.dto
 * 商品SKU DTO
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/19/019 14:54
 * @since : 1.0.0
 */
@Data
@ApiModel(value = "商品SKU")
public class CommoditySkuDTO implements Serializable {
    private static final long serialVersionUID = 3194778559562955388L;

    @ApiModelProperty("商品唯一标识， 数据修改时必填，否则验证不通过")
    private Long id;

    /**
     * sku编码
     */
    @ApiModelProperty("商品SKU编码， 不填默认是使用商品条形码")
    private String skuCode;
    /**
     * sku名称
     */
    @NotNull(message = "sku商品名称不可为空")
    @ApiModelProperty("SKU名称， 不可为空")
    private String skuName;
    /**
     * 商品id
     */
    @ApiModelProperty("商品编号， 不填默认使用商品条形码")
    private String commodityId;
    /**
     * 规格
     */
    @ApiModelProperty("商品规格")
    private String spec;
    /**
     * 商品条码
     */
    @NotNull(message = "商品条形码不可为空")
    @ApiModelProperty("商品条形码， 不填验证不通过")
    private String barCode;
    /**
     * 商品型号
     */
    @ApiModelProperty("商品型号")
    private String modelNo;

    /**
     * 是否是食品
     */
    @ApiModelProperty("是否是食品")
    @NotNull
    private Boolean isFoodstuff;

    /**
     * 商品单位
     */
    @ApiModelProperty("商品单位：罐")
    private String singleUnit;
    /**
     * 单个重量
     */
    @ApiModelProperty("商品重量：0.25")
    private BigDecimal singleWeight;
    /**
     * 单重单位
     */
    @ApiModelProperty("商品重量单位：kg")
    private String singleWeightUnit;

    /**
     * 单个体积
     */
    @ApiModelProperty("商品体积：0.586")
    private BigDecimal singleVolume;

    /**
     * 单体单位
     */
    @ApiModelProperty("商品体积单位：m³")
    private String singleVolumeUnit;

    @ApiModelProperty("备注")
    private String remark;

    public Boolean getFoodstuff() {
        return isFoodstuff;
    }

    public void setFoodstuff(Boolean foodstuff) {
        isFoodstuff = foodstuff;
    }
}
