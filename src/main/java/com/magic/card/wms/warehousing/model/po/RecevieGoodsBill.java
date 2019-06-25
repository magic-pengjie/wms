package com.magic.card.wms.warehousing.model.po;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 收货单据表
 * </p>
 *
 * @author pengjie
 * @since 2019-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wms_recevie_goods_bill")
public class RecevieGoodsBill  extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 采购id
     */
    private long purchaseId;
    /**
     * 收货单号
     */
    private String receivNo;
    /**
     * 签收数量
     */
    private Integer receivNums;
    /**
     * 收货人
     */
    private String receivUser;
    /**
     * 收货时间
     */
    private Date receivTime;
    /**
     * 单据状态(保存:save确认:confirm :作废:cancel )
     */
    private String billState;

}
