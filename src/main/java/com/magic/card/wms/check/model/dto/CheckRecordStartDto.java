package com.magic.card.wms.check.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *	 开始盘点 请求参数
 * @author Zhouhao
 *
 */
@Data
@ApiModel(description = "开始盘点 請求实体类")
public class CheckRecordStartDto implements Serializable{
	
	private static final long serialVersionUID = -4892174121422130829L;
    
    /**
     * 	库位IDList
     */
    @ApiModelProperty(value="库位id列表")
    @NotEmpty(message="盘点库位不能为空")
    private List<Long> storeIdList;
    
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CheckRecordStartDto [");
		if (storeIdList != null)
			builder.append("storeIdList=").append(storeIdList);
		return builder.toString();
	}

}
