package com.magic.card.wms.check.model.po;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *	 盘点记录表
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
     * 盘点批次Code
     */
    private String checkCode;
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
     * 库位类型
     */
    private String storehouseType;
    /**
     * 库位编码
     */
    private String storehouseCode;
    /**
     * 库存数量
     */
    private Integer storeNums;
    /**
     * 初盘数量
     */
    private Integer firstCheckNums;
    /**
     * 复盘数量
     */
    private Integer secondCheckNums;
    /**
     * 终盘数量
     */
    private Integer thirdCheckNums;
    /**
     * 盘点日期
     */
    private Date checkDate;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CheckRecord{");
        sb.append("checkCode='").append(checkCode).append('\'');
        sb.append(", customerCode='").append(customerCode).append('\'');
        sb.append(", skuId='").append(skuId).append('\'');
        sb.append(", checkUser='").append(checkUser).append('\'');
        sb.append(", storehouseType='").append(storehouseType).append('\'');
        sb.append(", storehouseCode='").append(storehouseCode).append('\'');
        sb.append(", storeNums=").append(storeNums);
        sb.append(", firstCheckNums=").append(firstCheckNums);
        sb.append(", secondCheckNums=").append(secondCheckNums);
        sb.append(", thirdCheckNums=").append(thirdCheckNums);
        sb.append(", checkDate=").append(checkDate);
        sb.append(", diffNums=").append(diffNums);
        sb.append(", approver='").append(approver).append('\'');
        sb.append(", approveTime=").append(approveTime);
        sb.append(", billState='").append(billState).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
