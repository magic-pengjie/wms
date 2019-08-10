package com.magic.card.wms.baseset.model.po;

import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 拣货单表
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-24
 */
@Data
@TableName("wms_picking_bill")
@EqualsAndHashCode(callSuper = false)
public class PickingBill extends BasePo implements Serializable {

    private static final long serialVersionUID = 3519476391143037566L;

    /**
     * 拣货单号
     */
    private String pickNo;
    /**
     * 是否B2B
     */
    @TableField("is_B2B")
    private int isB2b;

    /**
     * 区域等级
     */
    private String areaLevel;

    /**
     * 拣货人
     */
    private String pickUser;

    /**
     * 流程阶段
     */
    private String processStage;
    /**
     * 创建:save  拣货中:picking 拣货完成finish ;拣货异常:exception ;作废 cancel
     */
    private String billState;
}
