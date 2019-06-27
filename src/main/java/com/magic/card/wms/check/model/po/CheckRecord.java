package com.magic.card.wms.check.model.po;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.magic.card.wms.common.model.po.BasePo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 盘点记录表
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_check_record")
public class CheckRecord extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商家编码
     */
    private String customerCode;
    /**
     * sku id
     */
    private String skuId;
    /**
     * 盘点人
     */
    private String checkUser;
    /**
     * 零拣库位
     */
    private String storehouseP;
    /**
     * 存储库位
     */
    private String storehouseS;
    /**
     * 库存数量
     */
    private Integer storeNums;
    /**
     * 盘点数量
     */
    private Integer checkNums;
    /**
     * 盘点日期
     */
    private String checkDate;
    /**
     * 差异
     */
    private Integer diffNums;
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
