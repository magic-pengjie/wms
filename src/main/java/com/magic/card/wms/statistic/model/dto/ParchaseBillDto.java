package com.magic.card.wms.statistic.model.dto;

import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.common.model.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *	入库报表請求实体类
 * </p>
 * @author zhouhao
 * @since 2019年8月6日19:08
 */
@Data
@ApiModel(description = "入库报表請求实体类", value="入库报表請求实体类")
public class ParchaseBillDto extends PageInfo implements Serializable {

    private static final long serialVersionUID = -1613590060813034939L;

    //可按月、年、商家、查询入库信息，明细可导出
    @ApiModelProperty(value = "商家Code")
//    @NotBlank(message = "商家不能为空！")
    private String customerCode;

    //可按月、年、商家、查询入库信息，明细可导出
    @ApiModelProperty(value = "起始时间")
    private String startDate;

    //可按月、年、商家、查询入库信息，明细可导出
    @ApiModelProperty(value = "结束时间")
    private String endDate;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ParchaseBillDto{");
        sb.append("customerCode='").append(customerCode).append('\'');
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append('}');
        return sb.toString();
    }
}
