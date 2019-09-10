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
 * @author PENGJIE
 * @date 2019年6月13日
 */
@Data
@ApiModel(description = "用户dto",value="新增用户dto")
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
	@ApiModelProperty(value="密码")
    private String password;
    /**
     * 姓名
     */
	@ApiModelProperty(value="姓名")
    @NotBlank(message="姓名不能为空")
    private String name;
    /**
     * 客戶主鍵ID
     */
	@ApiModelProperty(value="客戶主鍵ID")
//    @NotBlank(message="客戶不能为空")
    private String customerId;
	/**
	 * 角色ID
	 */
	@ApiModelProperty(value="角色主键List")
	@NotEmpty(message="角色不能为空")
	private List<Long> roleKeyList;

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("UserDTO{");
		sb.append("userNo='").append(userNo).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", customerId='").append(customerId).append('\'');
		sb.append(", roleKeyList=").append(roleKeyList);
		sb.append('}');
		return sb.toString();
	}
}
