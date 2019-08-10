package com.magic.card.wms.warehousing.model.po;

import com.baomidou.mybatisplus.enums.IdType;
import com.magic.card.wms.common.model.po.BasePo;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 退货记录表
 * </p>
 *
 * @author pengjie
 * @since 2019-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wms_return_goods_records")
public class ReturnGoodsRecords extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 公司编码
     */
    private String customerCode;
    /**
     * 商品编码
     */
    private String commodityCode;
    /**
     * 快递单号
     */
    private String mailNo;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 系统订单号
     */
    private String systemOrderNo;
    /**
     * 发货数量
     */
    private Integer deliveryNums;
    /**
     * 商品状态(正常:normal;残次residual)
     */
    private String commodityState;
    /**
     * 退货数量
     */
    private Integer returnNums;
    /**
     * 残次商品数量
     */
    private Integer residualNums;
    /**
     * 正常商品数量
     */
    private Integer normalNums;
    /**
     * 残次商品库位
     */
    private String residualStorehouse;
    /**
     * 正常商品库位
     */
    private String normalStorehouse;
    /**
     * 退货备注
     */
    private String returnRemark;
    /**
     * 收件人姓名
     */
    private String reciptName;
    /**
     * 收件人电话
     */
    private String reciptPhone;

}
