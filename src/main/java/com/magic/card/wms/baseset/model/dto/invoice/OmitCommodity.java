package com.magic.card.wms.baseset.model.dto.invoice;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.dto.invoice
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/10 12:01
 * @since : 1.0.0
 */
@Data
@ApiModel("漏检商品信息")
public class OmitCommodity implements Serializable {
    private static final long serialVersionUID = 8655894271078563515L;

    @NotBlank(message = "漏检商品条码不可为空")
    private String commodityCode;

    @Min(value = 1, message = "漏检数量至少一个")
    private int omitNums;
}
