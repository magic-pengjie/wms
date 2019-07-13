package com.magic.card.wms.baseset.model.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.po
 * 商品补货单
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/12 15:36
 * @since : 1.0.0
 */
@Data
@TableName("wms_commodity_replenishment")
@EqualsAndHashCode(callSuper = false)
public class CommodityReplenishment extends BasePo implements Serializable {
    private static final long serialVersionUID = -1965048340623316478L;

    /**
     * 补货单号
     */
    private Long replenishmentNo;
    /**
     * 客户商品ID
     */
    private Long commodityId;
    /**
     * 存储库位ID
     */
    private String storageId;
    /**
     * 零拣库位ID
     */
    private String checkoutId;
    /**
     * 缺货量
     */
    private Integer stockoutNums;
    /**
     * 补货数量
     */
    private Integer replenishmentNums;
    /**
     * 是否清库
     */
    private Boolean isFinally;
    /**
     * 流程状态
     */
    private String processStage;
}
