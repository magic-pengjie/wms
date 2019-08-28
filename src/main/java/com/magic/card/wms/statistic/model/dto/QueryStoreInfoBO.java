package com.magic.card.wms.statistic.model.dto;

import com.magic.card.wms.common.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 *	库位信息查询 請求实体类
 * </p>
 * @author zhouhao
 * @since 2019年8月6日19:08
 */
@Data
@ApiModel(description = "库位信息查询請求实体类", value="库位信息查询請求实体类")
public class QueryStoreInfoBO extends PageInfo implements Serializable {

    private static final long serialVersionUID = -1613590060813034939L;

    @ApiModelProperty(value = "商家Code")
    @NotBlank(message = "商家不能为空")
    private String customerCode;

    @ApiModelProperty(value = "商品ID")
    private String commodityId;

    @ApiModelProperty(value = "库位类型")
    private String houseCode;

    @ApiModelProperty(value = "该商家已用库存")
    private boolean available;

    @ApiModelProperty(value = "该商家 未用库存")
    private boolean unavailable;

    @ApiModelProperty(value = "该商家 占用库存")
    private boolean usedStores;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("QueryStoreInfoBO{");
        sb.append("customerCode='").append(customerCode).append('\'');
        sb.append(", commodityId='").append(commodityId).append('\'');
        sb.append(", houseCode='").append(houseCode).append('\'');
        sb.append(", available=").append(available);
        sb.append(", unavailable=").append(unavailable);
        sb.append(", usedStores=").append(usedStores);
        sb.append('}');
        return sb.toString();
    }
}
