package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * com.magic.card.wms.baseset.model.dto
 * 订单修改
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/19 14:06
 * @since : 1.0.0
 */
@Data
public class OrderUpdateDTO implements Serializable {
    private static final long serialVersionUID = 4946149603754790756L;

    /**
     * 系统订单号
     */
    @NotNull(message = "系统订单号")
    @ApiModelProperty("系统订单号不可为空，否则验证不通过")
    private String systemOrderNo;

    /**
     * 收件人姓名
     */
    @NotNull(message = "收件人姓名不可为空")
    @ApiModelProperty("收件人姓名不可为空，否则验证不通过")
    private String reciptName;

    /**
     * 收货人邮编
     */
    @ApiModelProperty("收货人邮编")
    @NotNull(message = "收货人邮编")
    private String postCode;
    /**
     * 用户所在省
     */
    @ApiModelProperty("用户所在省")
    @NotNull(message = "用户所在省")
    private String prov;

    /**
     * 户所在市县（区）
     */
    @ApiModelProperty("户所在市县（区）")
    @NotNull(message = "户所在市县（区）")
    private String city;

    /**
     * 地址
     */
    @NotNull(message = "收件人地址不可为空")
    @ApiModelProperty("收件人地址不可为空，否则验证不通过")
    private String reciptAddr;

    /**
     * 电话
     */
    @NotNull(message = "收件人电话不可为空")
    @ApiModelProperty("收件人电话不可为空，否则验证不通过")
    private String reciptPhone;

    /**
     * 快递类型
     */
    @ApiModelProperty("指定快递类型")
    private String expressKey;

    /**
     * 是否B2B
     */
    @ApiModelProperty("是否为B2B订单")
    private boolean isB2b;

    /**
     * 是否B2B
     */
    @ApiModelProperty("是否为批量订单")
    private boolean isBatch;

    /**
     * 商品金额
     */
    @ApiModelProperty("商品金额")
    private BigDecimal goodsValue;

    /**
     * 单据状态(保存:save确认:confirm 作废及退单:cancel )
     */
    @ApiModelProperty("单据状态，默认：save / 确认:confirm 作废及退单:cancel ")
    private String billState;

    @Valid
    private List<OrderCommodityDTO> commodities;

    /**
     * 订单备注
     */
    private String remark;
}
