package com.magic.card.wms.user.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 用户dto
 * @author ZHOUHAO	
 * @date 2019年6月20日19:50:31
 */
@Data
@ApiModel(description = "用户登录dto")
public class UserLoginDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户名
     */
	@ApiModelProperty("用户名")
	@NotBlank(message="用户名不能为空")
    private String userNo;
    /**
                 * 密码
     */
	@ApiModelProperty("密码")
	@NotBlank(message="密码不能为空")
    private String password;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserLoginDTO [");
		if (userNo != null)
			builder.append("userNo=").append(userNo).append(", ");
		if (password != null)
			builder.append("password=").append(password);
		builder.append("]");
		return builder.toString();
	}
	
}
