package com.magic.card.wms.baseset.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * com.magic.card.wms.baseset.model.dto
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 14:27
 * @since : 1.0.0
 */
@Data
@ApiModel(value = "订单信息", description = "订单信息")
public class OrderInfoDTO implements Serializable {
    private static final long serialVersionUID = -5050831342653951057L;
    
    /**
     * 订单号
     */
    @NotNull(message = "订单号不可为空")
    @ApiModelProperty("订单号不可为空，否则验证不通过")
    private String orderNo;
    /**
     * 商家编码
     */
    @NotNull(message = "商家编码不可为空")
    @ApiModelProperty("商家编码不可为空，否则验证不通过")
    private String customerCode;
    /**
     * 商家名称
     */
    @ApiModelProperty("商家名称")
    private String customerName;
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
    @NotNull(message = "收件人电话不可为空")
    @ApiModelProperty("收件人电话不可为空，否则验证不通过")
    private Boolean isB2b;
    /**
     * 商品金额
     */
    @ApiModelProperty("商品金额")
    private Boolean goodsValue;
    /**
     * 单据状态(保存:save确认:confirm 作废及退单:cancel )
     */
    @ApiModelProperty("单据状态，默认：save / 确认:confirm 作废及退单:cancel ")
    private String billState;

    
    @Valid
    @NotNull(message = "订单商品不可为空")
    @Size(message = "至少有一个商品", min = 1)
    private List<OrderCommodityDTO> commodities;
    
    /**
     * 快递单主键id
     */
    private long id;
    /**
     * 快递单号
     */
    private String mailNo;
    
    /**
     * 发送次数
     */
    private Integer sendNums;
    /**
     * 发送失败原因
     */
    private String failReason;

    /**
     * 商品重量
     */
    private Double presetWeight;
    
    /**
     * 订单备注
     */
    private String remark;
}
