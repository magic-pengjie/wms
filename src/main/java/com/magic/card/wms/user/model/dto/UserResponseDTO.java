package com.magic.card.wms.user.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 用户登录成功，返回前端dto
 * @author PENGJIE
 * @date 2019年6月13日
 */
@Data
@ApiModel(description = "用户登录成功，返回前端dto")
public class UserResponseDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
     * 	用户名
     */
	@ApiModelProperty("用户账号")
    private String userNo;
    /**
     * 	密码
     */
	@ApiModelProperty("密码")
    private String password;
    /**
     * 	姓名
     */
	@ApiModelProperty("姓名")
    private String name;
    /**
     * 	登录态Token
     */
    @ApiModelProperty("登录态Token")
    private String token;
    
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserResponseDTO [");
		if (userNo != null)
			builder.append("userNo=").append(userNo).append(", ");
		if (password != null)
			builder.append("password=").append(password).append(", ");
		if (name != null)
			builder.append("name=").append(name).append(", ");
		if (token != null)
			builder.append("token=").append(token);
		builder.append("]");
		return builder.toString();
	}
	
}
