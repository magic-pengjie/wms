package com.magic.card.wms.baseset.model.dto.invoice;

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
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/10 11:55
 * @since : 1.0.0
 */
@Data
@ApiModel("配货单漏检")
public class InvoiceCheckOmitDTO implements Serializable {
    private static final long serialVersionUID = -3288701537012416042L;

    @ApiModelProperty("拣货单号")
    @NotBlank(message = "拣货单号不可为空")
    private String pickNo;

    @ApiModelProperty("拣货人")
    @NotBlank(message = "拣货人不可为空")
    private String pickOperator;

    @Valid
    @Size(min = 1, message = "漏检订单信息至少一个")
    private List<OmitMailDTO> mails;
}
