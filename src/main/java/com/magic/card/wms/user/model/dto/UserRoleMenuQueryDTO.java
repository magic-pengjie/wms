package com.magic.card.wms.user.model.dto;

import java.io.Serializable;
import java.util.List;

import com.magic.card.wms.user.model.po.MenuInfo;
import com.magic.card.wms.user.model.po.RoleInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 用户dto
 * @author ZHOUHAO
 * @date 2019年6月22日
 */
@Data
@ApiModel(description = "用户角色菜单dto")
public class UserRoleMenuQueryDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
     * 用户名
     */
	@ApiModelProperty(value="用户名")
    private String userNo;
    /**
     * 密码
     */
	@ApiModelProperty(value="密码")
    private String password;
    /**
     * 姓名
     */
	@ApiModelProperty(value="姓名")
    private String name;
	/**
	 * 角色信息List
	 */
	@ApiModelProperty(value="角色List")
	private List<RoleInfo> roleList;
	
	/**
	 * 菜单信息List
	 */
	@ApiModelProperty(value="菜单信息List")
	private List<MenuInfo> menuList;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRoleMenuQueryDTO [");
		if (userNo != null)
			builder.append("userNo=").append(userNo).append(", ");
		if (password != null)
			builder.append("password=").append(password).append(", ");
		if (name != null)
			builder.append("name=").append(name).append(", ");
		if (roleList != null)
			builder.append("roleList=").append(roleList).append(", ");
		if (menuList != null)
			builder.append("menuList=").append(menuList);
		builder.append("]");
		return builder.toString();
	}
}
