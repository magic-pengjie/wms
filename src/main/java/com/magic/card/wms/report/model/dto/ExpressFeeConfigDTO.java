package com.magic.card.wms.report.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * com.magic.card.wms.report.model.dto
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/11 17:10
 * @since : 1.0.0
 */
@Data
@ApiModel("快递收费配置")
public class ExpressFeeConfigDTO implements Serializable {
    private static final long serialVersionUID = 303944158340805058L;
    private Long id;
    /**
     * 客户编码
     */
    @NotNull
    private String customerCode;

    /**
     * 区域编码
     */
    @NotNull
    @Size(min = 1, message = "区域编号必须传入")
    private List<String> areaCodes;

    /**
     * 快递重量区间
     */
    @NotNull
    private Long rangeId;

    /**
     * 收费值
     */
    @NotNull
    private BigDecimal feeValue;
}
