package com.magic.card.wms.baseset.model.po;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;

import com.magic.card.wms.common.model.po.BasePo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-24
 */
@Data
@TableName("wms_order_info")
@EqualsAndHashCode(callSuper = false)
public class Order extends BasePo implements Serializable {

    private static final long serialVersionUID = -8181490159280427275L;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 系统订单号 订单号 + 商家编码
     */
    private String systemOrderNo;

    /**
     * 商家编码
     */
    private String customerCode;
    /**
     * 商家名称
     */
    private String customerName;
    /**
     * 收件人姓名
     */
    private String reciptName;
    /**
     * 收货人邮编
     */
    private String postCode;
    /**
     * 用户所在省
     */
    private String prov;
    /**
     * 户所在市县（区）
     */
    private String city;
    /**
     * 地址
     */
    private String reciptAddr;
    /**
     * 电话
     */
    private String reciptPhone;
    /**
     * 快递公司标识
     */
    private String expressKey;

    /**
     * 是否是批量订单
     */
    private int isBatch;

    /**
     * 是否B2B
     */
    private int isB2b;
    /**
     * 商品金额
     */
    private BigDecimal goodsValue;
    /**
     * 单据状态(保存:save确认:confirm 作废及退单:cancel )
     */
    private String billState;
}
