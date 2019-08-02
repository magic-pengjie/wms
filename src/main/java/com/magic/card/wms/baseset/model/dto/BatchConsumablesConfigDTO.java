package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * com.magic.card.wms.baseset.model.dto
 * 商品批量耗材设置
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/1 14:09
 * @since : 1.0.0
 */
@Data
@EqualsAndHashCode
public class BatchConsumablesConfigDTO implements Serializable {
    private static final long serialVersionUID = -8724237084775272621L;

    /**
     * 商品条码
     */
    @NotNull(message = "商品条码不可为空")
    @ApiModelProperty("商品条码是必须提供，否则验证不通过")
    @Size(min = 1, message = "至少配置一个商品")
    private List<String> commodityCodes;

    /**
     * 消耗商品条码
     */
    @NotNull(message = "消耗商品条码不可为空")
    @ApiModelProperty("消耗商品条码是必须提供，否则验证不通过")
    @Size(min = 1, message = "至少配置一个消耗商品")
    private List<String> useCommodityCodes;

    /**
     * 左区间值
     */
    @NotNull(message = "左区间值不可为空")
    @ApiModelProperty("左区间值是必须提供，否则验证不通过")
    private Integer leftValue = 1;
    /**
     * 右区间值
     */
    @NotNull(message = "右区间值不可为空")
    @ApiModelProperty("右区间值是必须提供，否则验证不通过")
    private Integer rightValue;
    /**
     * 消耗数量
     */
    @NotNull(message = "消耗数量不可为空")
    @ApiModelProperty("消耗数量是必须提供，否则验证不通过")
    private Integer useNums=1;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
