package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.dto
 * 补货库位基本信息
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/31 10:37
 * @since : 1.0.0
 */
@Data
@ApiModel("补货库位信息")
public class ReplenishmentStorehouseDTO implements Serializable {
    private static final long serialVersionUID = -2535711751437001432L;
    @ApiModelProperty("补货库位ID")
    @NotNull(message = "补货库位ID不可为空")
    private String storehouseId;
    @ApiModelProperty("补货数量")
    @Min(value = 1, message = "补货数量不可低于1")
    private int replenishmentNums;
}
