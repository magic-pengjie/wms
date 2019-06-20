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
@ApiModel(description = "用户dto")
public class UserUpdateDTO implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 用户名
     */
	@ApiModelProperty("用户名")
	@NotBlank(message="用户名不能为空")
    private String userNo;
}
