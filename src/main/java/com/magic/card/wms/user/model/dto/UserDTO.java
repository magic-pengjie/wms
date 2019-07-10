package com.magic.card.wms.user.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 用户dto
 * @author PENGJIE
 * @date 2019年6月13日
 */
@Data
@ApiModel(description = "用户dto")
public class UserDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
     * 用户名
     */
	@ApiModelProperty(value = "用户名")
	@NotBlank(message="用户名不能为空")
    private String userNo;
    /**
     * 密码
     */
	@ApiModelProperty("密码")
    private String password;
    /**
     * 姓名
     */
	@ApiModelProperty("姓名")
    @NotBlank(message="姓名不能为空")
    private String name;
	/**
	 * 角色ID
	 */
	@ApiModelProperty(value="角色主键List")
	@NotNull(message="角色不能为空")
	private List<Long> roleKeyList;
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDTO [");
		if (userNo != null)
			builder.append("userNo=").append(userNo).append(", ");
		if (password != null)
			builder.append("password=").append(password).append(", ");
		if (name != null)
			builder.append("name=").append(name).append(", ");
		if (roleKeyList != null)
			builder.append("roleKey=").append(roleKeyList);
		builder.append("]");
		return builder.toString();
	}
	
}
