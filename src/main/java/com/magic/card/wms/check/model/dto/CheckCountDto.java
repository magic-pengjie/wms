package com.magic.card.wms.check.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *	 盘点统计查询
 * @author Zhouhao
 *
 */
@Data
@ApiModel(description = "盘点查询统计 請求实体类")
public class CheckCountDto implements Serializable{
	
	private static final long serialVersionUID = 1727581510222482592L;
	
    /**
     * 	盘点类型
     */
    @ApiModelProperty("盘点类型(M:月盘，Q:季盘，Y:年盘)")
	private String checkType;

    /**
     * 	商家
     */
    @ApiModelProperty("商家ID")
	@NotBlank(message = "商家不能为空")
    private String customerId;

	/**
	 * 商家Code
	 */
	private String customerCode;

    /**
     * 	库区(A,B,C,D。。。)
     */
    @ApiModelProperty("库区(A,B,C,D。。。)")
	private List<String> areaCode;
    
    /**
     * 	商品id
     */
    @ApiModelProperty("商品id列表")
    private List<Integer> commodityId;


	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("CheckCountDto{");
		sb.append("checkType='").append(checkType).append('\'');
		sb.append(", customerId='").append(customerId).append('\'');
		sb.append(", customerCode='").append(customerCode).append('\'');
		sb.append(", areaCode=").append(areaCode);
		sb.append(", commodityId=").append(commodityId);
		sb.append('}');
		return sb.toString();
	}
}
