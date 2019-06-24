package com.magic.card.wms.user.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 	修改权限目录关系请求实体
 * @author zhouhao
 * @since 2019-06-18
 */
@Data
@ApiModel(description = "修改权限菜單关系请求实体")
public class RoleMenuUpdateDto implements Serializable{

	private static final long serialVersionUID = 1L;

    /**
     * 	权限主鍵ID
     */
	@ApiModelProperty("权限ID")
	@NotNull(message="权限不能为空")
	private Long roleKey;
	
    /**
     * 	新增菜單主鍵ID
     */
	@ApiModelProperty("新增菜單主鍵ID")
	private List<Long> addMenuKeyList;
	
	/**
	 * 	删除菜單主鍵ID
	 */
	@ApiModelProperty("删除菜單主鍵ID")
	private List<Long> delMenuKeyList;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoleMenuUpdateDto [");
		if (roleKey != null)
			builder.append("roleKey=").append(roleKey).append(", ");
		if (addMenuKeyList != null)
			builder.append("addMenuKeyList=").append(addMenuKeyList).append(", ");
		if (delMenuKeyList != null)
			builder.append("delMenuKeyList=").append(delMenuKeyList);
		builder.append("]");
		return builder.toString();
	}
	
}
