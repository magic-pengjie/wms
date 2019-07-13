package com.magic.card.wms.report.model.po;

import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * com.magic.card.wms.report.model.po
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/11 17:05
 * @since : 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExpressFeeConfig extends BasePo implements Serializable {
    private static final long serialVersionUID = 4657269850324763322L;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 区域编码
     */
    private String areaCode;

    /**
     * 快递重量区间
     */
    private Long rangeId;

    /**
     * 收费值
     */
    private BigDecimal feeValue;
}
