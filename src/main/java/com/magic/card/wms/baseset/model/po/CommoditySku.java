package com.magic.card.wms.baseset.model.po;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;

/**
 * <p>
 * 商品sku表
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-19
 */
@Data
@TableName("wms_commodity_sku")
public class CommoditySku extends BasePo implements Serializable {

    private static final long serialVersionUID = 3901415146620204349L;

    /**
     * sku编码
     */
    private String skuCode;
    /**
     * sku名称
     */
    private String skuName;

    /**
     * 规格
     */
    private String spec;
    /**
     * 商品条码
     */
    private String barCode;
    /**
     * 商品型号
     */
    private String modelNo;

    /**
     * 是否是食品
     */
    private Boolean isFoodstuff;
    /**
     * 商品单位
     */
    private String singleUnit;
    /**
     * 单个重量
     */
    private BigDecimal singleWeight;
    /**
     * 单重单位
     */
    private String singleWeightUnit;
    /**
     * 单个体积
     */
    private BigDecimal singleVolume;
    /**
     * 单体单位
     */
    private String singleVolumeUnit;
}
