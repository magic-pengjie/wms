package com.magic.card.wms.user.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 用户dto
 * @author zhouhao
 * @date 2019年6月20日18:41:39
 */
@Data
@ApiModel(description = "用户修改dto")
public class UserUpdateDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 用户名
	 */
	@ApiModelProperty("用户ID")
	@NotBlank(message="用户ID不能为空")
	private Integer id;
	/**
     	* 用户名
     */
	@ApiModelProperty("用户名")
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
    private String name;
	
	/**
	 * 姓名
	 */
	@ApiModelProperty("数据状态(0:正常，1:删除)")
	private String state;
	
}
