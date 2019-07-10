package com.magic.card.wms.user.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 用户dto
 * @author zhouhao
 * @date 2019年6月20日18:41:39
 */
@Data
@ApiModel(description = "用户修改dto", value="用户修改dto")
public class UserUpdateDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 用户名
	 */
	@ApiModelProperty(value="用户ID")
	@NotNull(message="用户ID不能为空")
	private Long userKey;
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
	 * 数据状态(1:正常，0:删除)
	 */
	@ApiModelProperty(value="数据状态(1:正常，0:删除)")
	private Integer state;
	
	/**
	 * 角色ID
	 */
	@ApiModelProperty(value="角色主键List")
	private  List<Long> roleKeyList;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserUpdateDTO [");
		if (userKey != null)
			builder.append("userKey=").append(userKey).append(", ");
		if (userNo != null)
			builder.append("userNo=").append(userNo).append(", ");
		if (password != null)
			builder.append("password=").append(password).append(", ");
		if (name != null)
			builder.append("name=").append(name).append(", ");
		if (state != null)
			builder.append("state=").append(state).append(", ");
		if (roleKeyList != null)
			builder.append("roleKeyList=").append(roleKeyList);
		builder.append("]");
		return builder.toString();
	}
	
}
