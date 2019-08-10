package com.magic.card.wms.warehousing.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 退货记录表
 * </p>
 *
 * @author pengjie
 * @since 2019-08-06
 */
@Data
@ApiModel(description = "退货记录新增模型")
public class ReturnGoodsRecordsDTO   implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 公司编码
     */
    @ApiModelProperty(" 公司编码")
    @NotBlank(message = "公司编码不为空")
    private String customerCode;
    /**
     * 商品编码
     */
    @ApiModelProperty("商品编码")
    @NotBlank(message = "商品编码不为空")
    private String commodityCode;
    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    @NotBlank(message = "商品id不为空")
    private String commodityId;
    /**
     * 快递单号
     */
    @ApiModelProperty("快递单号")
    @NotBlank(message = "商品编码不为空")
    private String mailNo;
    /**
     * 订单号
     */
    @ApiModelProperty("订单号")
    @NotBlank(message = "订单号不为空")
    private String orderNo;
    /**
     * 系统订单号
     */
    @ApiModelProperty("系统订单号")
    private String systemOrderNo;
    /**
     * 发货数量
     */
    @ApiModelProperty("发货数量")
    private int deliveryNums;
    /**
     * 商品状态(正常:normal;残次residual)
     */
    @ApiModelProperty("商品状态(正常:normal;残次residual)")
    private String commodityState;
    /**
     * 退货数量
     */
    @ApiModelProperty("退货数量")
    @NotNull(message = "退货数量必输")
    private int returnNums;
    /**
     * 残次商品数量
     */
    @ApiModelProperty("残次商品数量")
    private int residualNums;
    /**
     * 正常商品数量
     */
    @ApiModelProperty("正常商品数量")
    private int normalNums;
    /**
     * 残次商品库位编码
     */
    @ApiModelProperty("残次商品库位编码")
    private String residualStorehouse;
    /**
     * 残次商品库位Id
     */
    @ApiModelProperty("残次商品库位Id")
    private String residualStorehouseId;
    /**
     * 正常商品库位编码
     */
    @ApiModelProperty("正常商品库位编码")
    private String normalStorehouse;
    /**
     * 正常商品库位id
     */
    @ApiModelProperty("正常商品库位id")
    private String normalStorehouseId;
    /**
     * 退货备注
     */
    @ApiModelProperty("退货备注")
    private String returnRemark;
    /**
     * 收件人姓名
     */
    @ApiModelProperty("收件人姓名")
    private String reciptName;
    /**
     * 收件人电话
     */
    @ApiModelProperty("收件人电话")
    private String reciptPhone;
}
