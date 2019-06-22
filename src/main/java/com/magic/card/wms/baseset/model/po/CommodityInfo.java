package com.magic.card.wms.baseset.model.po;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-19
 */
@Data
@TableName("wms_commodity_info")
@EqualsAndHashCode(callSuper = false)
public class CommodityInfo extends BasePo implements Serializable {

    private static final long serialVersionUID = -2959178971546487607L;

    /**
     * 商品编码
     */
    private String commodityCode;
    /**
     * 客户id
     */
    private String customerId;
    /**
     * 商品名称
     */
    private String commodityName;
    /**
     * 包装量
     */
    private Integer packingNum;
    /**
     * 包装单位
     */
    private String packingUnit;
    /**
     * 包装重量
     */
    private BigDecimal packingWeight;
    /**
     * 包重单位
     */
    private String packingWeightUnit;
    /**
     * 包装体积
     */
    private BigDecimal packingVolume;
    /**
     * 包体单位
     */
    private String packingVolumeUnit;
}
