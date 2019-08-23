package com.magic.card.wms.check.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *	 商家商品列表dto返回
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-26
 */
@Data
public class CustomerCommodityDto implements Serializable {

    private static final long serialVersionUID = -3107161252979052762L;


    @ApiModelProperty(value = "商品ID")
    private String commodityId;//商品ID

    @ApiModelProperty(value = "商品名称")
    private String commodityName;//商品名称

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CheckRecordQueryResponse{");
        sb.append(", commodityId='").append(commodityId).append('\'');
        sb.append(", commodityName='").append(commodityName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
