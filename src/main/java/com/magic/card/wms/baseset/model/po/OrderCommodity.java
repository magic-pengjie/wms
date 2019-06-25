package com.magic.card.wms.baseset.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>
 * 订单商品表
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-24
 */
@Data
@TableName("wms_order_commodity")
@EqualsAndHashCode(callSuper = false)
public class OrderCommodity extends BasePo implements Serializable {

    private static final long serialVersionUID = -8575871316743730731L;

    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 商家编码
     */
    private String customerCode;
    /**
     * 商品条形码
     */
    private String barCode;

    /**
     * 商品型号
     */
    private String modelNo;
    /**
     * 商品规格
     */
    private String spec;
    /**
     * 商品数量
     */
    private Integer numbers;

}
