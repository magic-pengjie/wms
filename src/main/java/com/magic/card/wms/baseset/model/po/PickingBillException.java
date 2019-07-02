package com.magic.card.wms.baseset.model.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 拣货异常记录
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-27
 */
@Data @TableName("wms_picking_bill_exception")
@EqualsAndHashCode(callSuper = false)
public class PickingBillException extends BasePo implements Serializable {

    private static final long serialVersionUID = -5593878622050998058L;

    /**
     * 拣货单号
     */
    private String pickNo;
    /**
     * 订单商品不足时记录订单号
     */
    private String orderNo;
    /**
     * 检错/多少拣商品条形码
     */
    private String commodityCode;
    /**
     * 流程阶段
     */
    private String processStage;
    /**
     * 商品异常数量统计
     */
    private Integer exceptionNumber;
    /**
     * 错拣:error  遗漏:omit ;多拣:overflow
     */
    private String exceptionState;

    /**
     * 增加商品异常数量
     * @param plusNum 增量
     */
    public void exceptionNumberPlus(Integer plusNum) {
        this.exceptionNumber += plusNum;
    }
}
