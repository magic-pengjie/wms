package com.magic.card.wms.user.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 	查询权限目录关系请求实体
 * @author zhouhao
 * @since 2019-06-18
 */
@Data
@ApiModel(description = "查询权限菜單关系请求实体",value="查询权限菜單关系请求实")
public class RoleMenuQueryDto implements Serializable{

	private static final long serialVersionUID = 1L;

    /**
     * 	权限主鍵ID
     */
	@ApiModelProperty(value="权限ID")
	@NotNull(message="权限不能为空")
	private Long roleKey;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoleMenuUpdateDto [");
		if (roleKey != null)
			builder.append("roleKey=").append(roleKey);
		builder.append("]");
		return builder.toString();
	}
	
}
