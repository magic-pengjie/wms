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
 * 入库上架单表
 * </p>
 *
 * @author pengjie
 * @since 2019-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wms_warehousing_bill")
public class WarehousingBill extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 入库单号
     */
    private String warehousingNo;
    /**
     * 收货单id
     */
    private String receivId;
    /**
     * 入库数量
     */
    private Integer warehousingNums;
    /**
     * 入库日期
     */
    private Date warehousingTime;
    /**
     * 存储库位编码
     */
    private String storehouseCode;
    /**
     * 审核人
     */
    private String approver;
    /**
     * 审核时间
     */
    private Date approveTime;
    /**
     * 单据状态(初始化save ;审批中 approving;审批完成 approved;审批失败 approve_fail :作废:cancel )
     */
    private String billState;

}
