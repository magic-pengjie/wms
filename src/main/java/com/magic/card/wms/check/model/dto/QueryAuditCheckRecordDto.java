package com.magic.card.wms.check.model.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryAuditCheckRecordDto implements Serializable{
	
	private static final long serialVersionUID = 8399436889935279638L;

    @ApiModelProperty(value="商家编码")
    private String customerCode;

    @ApiModelProperty(value="盘点人")
	private String checkUser;
    
    @ApiModelProperty(value="盘点时间")
    private String checkDate;
    
    @ApiModelProperty(value="单据状态")
    private String billState;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryAuditCheckRecordDto [");
		if (customerCode != null)
			builder.append("customerCode=").append(customerCode).append(", ");
		if (checkUser != null)
			builder.append("checkUser=").append(checkUser).append(", ");
		if (checkDate != null)
			builder.append("checkDate=").append(checkDate).append(", ");
		if (billState != null)
			builder.append("billState=").append(billState);
		builder.append("]");
		return builder.toString();
	}
}
