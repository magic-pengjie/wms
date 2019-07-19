package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.dto
 * 仓库信息DTO
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/19/019 10:28
 * @since : 1.0.0
 */
@ApiModel("仓库信息")
@Data
public class StorehouseInfoDTO implements Serializable {
    private static final long serialVersionUID = -7512981271401681139L;
    private Long id;
    /**
     * 仓库编码
     */
    @ApiModelProperty("仓库功能编码")
    @NotNull(message = "仓库功能编码不可为空")
    private String houseCode;
    /**
     * 仓库类型
     */
    @ApiModelProperty("区域编码")
    @NotNull(message = "区域编码不可为空")
    private String areaCode;
    /**
     * 库位编码
     */
    @ApiModelProperty("库位编码")
    @NotNull(message = "库位编码不可为空")
    private String storeCode;

    /**
     * 优先值
     */
    @ApiModelProperty("库位优先值")
    @NotNull(message = "库位优先值不可为空")
    private Integer priorityValue;

    /**
     * 是否冻结库存(否:N 是:Y)
     */
    private String isFrozen;
    /**
     * 备注
     */
    private String remark;
}
