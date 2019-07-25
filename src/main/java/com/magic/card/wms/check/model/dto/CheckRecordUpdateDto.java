package com.magic.card.wms.check.model.dto;

import com.magic.card.wms.check.model.po.CheckRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 *	 盘点结束 请求参数
 * @author Zhouhao
 *
 */
@Data
@ApiModel(description = "盘点结束 請求实体类")
public class CheckRecordUpdateDto implements Serializable{
	
	private static final long serialVersionUID = -4892174121422130829L;


    @ApiModelProperty(value="盘点记录List")
    @NotEmpty(message="盘点记录不能为空")
    private List<CheckRecord> checkRecordList;

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("CheckRecordUpdateDto{");
		sb.append("checkRecordList=").append(checkRecordList);
		sb.append('}');
		return sb.toString();
	}
}
