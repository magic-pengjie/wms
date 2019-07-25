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
 * 库位批量绑定
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/24 17:49
 * @since : 1.0.0
 */
@Data
@ApiModel("批量绑定客户")
public class BatchBindStorehouseDTO implements Serializable {
    private static final long serialVersionUID = 1052088606298854302L;

    @ApiModelProperty("客户ID")
    @NotNull(message = "客户ID不可为空")
    private String customerId;

    @ApiModelProperty("库位IDS")
    @Size(min = 1, message = "至少提供一个库位ID")
    private List<String> storehouseIds;
}
