package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * com.magic.card.wms.baseset.model.dto
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/19/019 16:37
 * @since : 1.0.0
 */
@Data
@ApiModel("商品-客户维系数据")
public class CommodityInfoDTO implements Serializable {
    private static final long serialVersionUID = -8290971927245688671L;

    @ApiModelProperty(notes = "数据修改时请提供数据，否则将会抛出异常")
    private Long id;

    /**
     * 商品编码
     */
    @NotNull(message = "商品码是必填项")
    @ApiModelProperty(value = "商品编码，必填项，若为空数据验证不通过")
    private String commodityCode;
    /**
     * 客户id
     */
    @NotNull(message = "客户是必填项")
    @ApiModelProperty(value = "客户id，必选项，若为空数据验证不通过")
    private String customerId;
    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String commodityName;
    /**
     * 包装量
     */
    @ApiModelProperty("包装量")
    private Integer packingNum;
    /**
     * 包装单位
     */
    @ApiModelProperty("包装单位")
    private String packingUnit;
    /**
     * 包装重量
     */
    @ApiModelProperty("包装重量")
    private BigDecimal packingWeight;
    /**
     * 包重单位
     */
    @ApiModelProperty("包重单位")
    private String packingWeightUnit;
    /**
     * 包装体积
     */
    @ApiModelProperty("包装体积")
    private BigDecimal packingVolume;
    /**
     * 包体单位
     */
    @ApiModelProperty("包体单位")
    private String packingVolumeUnit;

    @ApiModelProperty("备注")
    private String remark;
}
