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
	 * 	用户ID
	 */
	@ApiModelProperty(value="用户ID")
	private Long id;

	/**
     * 	用户名
     */
	@ApiModelProperty(value="用户账号")
    private String userNo;
    /**
     * 	密码
     */
	@ApiModelProperty(value="密码")
    private String password;
    /**
     * 	姓名
     */
	@ApiModelProperty(value="姓名")
    private String name;
    /**
     * 	登录态Token
     */
    @ApiModelProperty(value="登录态Token")
    private String token;

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("UserResponseDTO{");
		sb.append("id='").append(id).append('\'');
		sb.append(", userNo='").append(userNo).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", token='").append(token).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
