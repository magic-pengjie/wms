package com.magic.card.wms.user.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.magic.card.wms.user.model.po.MenuInfo;

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
public class RoleMenuQueryResponseDto implements Serializable{

	private static final long serialVersionUID = 1L;

    /**
     * 	权限主鍵ID
     */
	@ApiModelProperty(value="权限ID")
	private Long roleKey;
	
	@ApiModelProperty(value="菜单信息")
	private List<MenuInfo> menuInfoList;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoleMenuQueryResponseDto [");
		if (roleKey != null)
			builder.append("roleKey=").append(roleKey).append(", ");
		if (menuInfoList != null)
			builder.append("menuInfoList=").append(menuInfoList);
		builder.append("]");
		return builder.toString();
	}
	
}
