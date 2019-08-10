package com.magic.card.wms.baseset.model.dto.invoice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * com.magic.card.wms.baseset.model.dto.invoice
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/10 16:16
 * @since : 1.0.0
 */
@Data
@ApiModel("获取商品缺货库位")
public class OmitStokeDTO implements Serializable {
    private static final long serialVersionUID = 8402502396694362492L;
    @ApiModelProperty("商家编码")
    @NotBlank(message = "商家编码")
    private String customerCode;
    @ApiModelProperty("商品Code")
    @Size(min = 1, message = "商品Code至少给一个")
    private List<String> commodityCodes;
}
