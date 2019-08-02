package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * com.magic.card.wms.baseset.model.dto
 * 补货完成
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/31 10:35
 * @since : 1.0.0
 */
@Data
@ApiModel("补货完成数据")
public class ReplenishmentFinishedDTO implements Serializable {
    private static final long serialVersionUID = 1821541828052144860L;
    @ApiModelProperty("补货单号")
    @NotBlank(message = "补货单号不可为空")
    private String replenishmentNo;
    @Valid
    @ApiModelProperty("补货库位")
    @Size(min = 1, message = "至少有一个库位信息")
    private List<ReplenishmentStorehouseDTO> storehouses;
}
