package com.magic.card.wms.baseset.model.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.magic.card.wms.common.model.po.BasePo;
import com.magic.card.wms.common.utils.PoUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品库存（整体情况）
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-27
 */
@Data
@TableName("wms_commodity_stock")
@EqualsAndHashCode(callSuper = false)
public class CommodityStock extends BasePo implements Serializable {

    private static final long serialVersionUID = -1349165187026960412L;

    /**
     * 客户code
     */
    private String customerCode;
    /**
     * 商品条码
     */
    private String commodityCode;
    /**
     * 商品库存量
     */
    private Long stockNum;
    /**
     * 商品库存占用量(订单导入占用，退货占用...)
     */
    private Long stockOccupyNum;
    /**
     * 商品库存冻结量
     */
    private Long stockFreezeNum;
    /**
     * 库存预警量
     */
    private Long stockDeficiencyNum;
}
