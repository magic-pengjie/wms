package com.magic.card.wms.baseset.model.dto.invoice;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * com.magic.card.wms.baseset.model.dto.invoice
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/10 11:59
 * @since : 1.0.0
 */
@Data
@ApiModel("漏检订单信息")
public class OmitMailDTO implements Serializable {
    private static final long serialVersionUID = -8578334166795952286L;
    @NotBlank(message = "快递号不可为空")
    private String mailNo;
    @NotBlank(message = "订单号不可为空")
    private String orderNo;
    @Valid
    @Size(min = 1, message = "包裹商品漏检信息不可为空")
    private List<OmitCommodity> commodities;
}
