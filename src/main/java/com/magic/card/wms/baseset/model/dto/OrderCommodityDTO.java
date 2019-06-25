package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.dto
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 15:31
 * @since : 1.0.0
 */
@Data
@ApiModel("订单商品明细信息")
public class OrderCommodityDTO implements Serializable {
    private static final long serialVersionUID = 615564015575473823L;
    /**
     * 订单号
     */
    @ApiModelProperty(hidden = true)
    private String orderNo;
    /**
     * 商家编码
     */
    @ApiModelProperty(hidden = true)
    private String customerCode;

    /**
     * 商品条形码
     */
    @NotNull(message = "商品条形码不可为空")
    @ApiModelProperty("商品条形码不可为空，否则验证不通过")
    private String barCode;

    /**
     * 商品型号
     */
    @ApiModelProperty("商品型号")
    private String modelNo;
    /**
     * 商品规格
     */
    @ApiModelProperty("商品规格")
    private String spec;
    /**
     * 商品数量
     */
    @NotNull(message = "商品数量不可为空")
    @ApiModelProperty("商品数量不可为空，否则验证不通过")
    private Integer numbers;
}
