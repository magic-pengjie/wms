package com.magic.card.wms.baseset.model.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.model.dto.order
 * 出库订单导入（带有快递单号）
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/9/2 10:10
 * @since : 1.0.0
 */
@Data
public class OutboundOrderDTO implements Serializable {
    private static final long serialVersionUID = 1760016719887821652L;

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
    @ApiModelProperty("是否为B2B订单")
    private int isB2b;

    /**
     * 是否B2B
     */
    @ApiModelProperty("是否为批量订单")
    private int isBatch;
    /**
     * 商品金额
     */
    @ApiModelProperty("商品金额")
    private BigDecimal goodsValue;
    /**
     * 单据状态
     */
    @ApiModelProperty("单据状态")
    private String billState;
    /**
     * 订单包裹数据
     */
    private Map<String, List<OrderCommodityDTO>> packages;
}
