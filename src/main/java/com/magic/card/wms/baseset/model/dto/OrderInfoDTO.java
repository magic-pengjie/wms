package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

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
     * 单据状态(保存:save确认:confirm 作废及退单:cancel )
     */
    @ApiModelProperty("单据状态，默认：save / 确认:confirm 作废及退单:cancel ")
    private String billState;

    @Valid
    @NotNull(message = "订单商品不可为空")
    @Size(message = "至少有一个商品", min = 1)
    private List<OrderCommodityDTO> commodities;
}
