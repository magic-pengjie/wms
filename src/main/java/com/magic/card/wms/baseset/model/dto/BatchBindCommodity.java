package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * com.magic.card.wms.baseset.model.dto
 * 商家批量绑定商品信息
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/24 14:39
 * @since : 1.0.0
 */
@Data
@ApiModel("批量绑定商品")
public class BatchBindCommodity implements Serializable {
    private static final long serialVersionUID = 41456295728821164L;
    /**
     * 商家ID
     */
    @ApiModelProperty("商家ID")
    @NotNull(message = "商家ID不可为空！")
    private String customerId;
    /**
     * 商品条形码
     */
    @ApiModelProperty("商品条形码")
    @Size(min = 1, message = "至少携带一个商品条形码！")
    private List<String> commodityCodes;
}
