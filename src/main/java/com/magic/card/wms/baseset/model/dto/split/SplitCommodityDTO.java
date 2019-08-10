package com.magic.card.wms.baseset.model.dto.split;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
@ApiModel("拆包商品")
public
class SplitCommodityDTO implements Serializable {
    private static final long serialVersionUID = 997770017843083319L;
    @ApiModelProperty("商品条码")
    private String commodityCode;
    @ApiModelProperty("商品数量")
    private int nums;

    /**
     * 获取商品token
     * @return
     */
    public String commodityToken() {
        return StringUtils.joinWith("+", commodityCode, nums);
    }
}
