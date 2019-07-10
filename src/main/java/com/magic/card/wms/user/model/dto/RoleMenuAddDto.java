package com.magic.card.wms.user.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 	新增权限目录关系请求实体
 * @author zhouhao
 * @since 2019-06-18
 */
@Data
@ApiModel(description = "新增权限菜單关系请求实体",value="新增权限菜單关系请求实体")
public class RoleMenuAddDto implements Serializable{

	private static final long serialVersionUID = 1L;

    /**
     * 	权限主鍵ID
     */
	@ApiModelProperty(value="权限ID")
	@NotNull(message="权限不能为空")
	private Long roleKey;
	
    /**
     * 	菜單主鍵ID
     */
	@ApiModelProperty(value="菜單主鍵IDList")
	@NotEmpty(message="菜單不能为空")
	private List<Long> menuKeyList;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoleMenuAddDto [");
		if (roleKey != null)
			builder.append("roleKey=").append(roleKey).append(", ");
		if (menuKeyList != null)
			builder.append("menuKeyList=").append(menuKeyList);
		builder.append("]");
		return builder.toString();
	}
	
}
