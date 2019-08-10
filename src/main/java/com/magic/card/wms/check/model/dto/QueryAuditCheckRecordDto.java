package com.magic.card.wms.check.model.dto;

import java.io.Serializable;
import java.util.Date;

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
    private Date checkDate;
    
    @ApiModelProperty(value="单据状态")
    private String billState;

	@ApiModelProperty(value="盘点批次Code")
	private String checkCode;

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("QueryAuditCheckRecordDto{");
		sb.append("customerCode='").append(customerCode).append('\'');
		sb.append(", checkUser='").append(checkUser).append('\'');
		sb.append(", checkDate=").append(checkDate);
		sb.append(", billState='").append(billState).append('\'');
		sb.append(", checkCode='").append(checkCode).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
