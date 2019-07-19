package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.dto
 * 批量添加库位信息
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/18 14:34
 * @since : 1.0.0
 */
@Data
public class BatchStorehouseDTO implements Serializable {
    private static final long serialVersionUID = 8319562756674334152L;

    /**
     * 优先值
     */
    @ApiModelProperty("库位优先值")
    @NotNull(message = "库位优先值不可为空")
    private Integer priorityValue;

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
     * 通道编号
     */
    @NotNull(message = "通道编号不可为空空")
    @ApiModelProperty("通道编号")
    private String channelNo;

    /**
     * 货架号
     */
    @ApiModelProperty("货架号")
    private String rockNo;

    /**
     * 层号
     */
    @ApiModelProperty("层号")
    private String tierNo;

    @ApiModelProperty("最小库位号")
    @Min(value = 1l, message = "库位号不小于1")
    private Integer storeMinNo = 1;
    /**
     * 库位号
     */
    @ApiModelProperty("最大库位号")
    @Min(value = 1l, message = "库位号不小于1")
    private Integer storeMaxNo;

    /**
     * 库位编码
     */
    @ApiModelProperty("库位编码")
    private String storeCode;
}
