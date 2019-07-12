package com.magic.card.wms.check.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *	 盘点记录表
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-26
 */
@Data
public class AuditCheckRecordDto implements Serializable {

	private static final long serialVersionUID = -3107161252979052762L;

    @ApiModelProperty(value = "单据状态(初始化save ;审批中 approving;审批完成 approved;审批失败 approve_fail :作废:cancel )")
    @NotBlank(message="审核状态不能为空")
    private String billState;
    
    @ApiModelProperty(value = "备注")
    private String remark;
    
    @ApiModelProperty(value = "盘点记录List")
    private List<CheckRecordDto> checkRecordList;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AuditCheckRecordDto [");
		if (billState != null)
			builder.append("billState=").append(billState).append(", ");
		if (remark != null)
			builder.append("remark=").append(remark).append(", ");
		if (checkRecordList != null)
			builder.append("checkRecordList=").append(checkRecordList);
		builder.append("]");
		return builder.toString();
	}
    
}
