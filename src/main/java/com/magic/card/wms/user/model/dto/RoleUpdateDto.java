package com.magic.card.wms.user.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增权限 请求实体
 * @author zhouhao
 * @since 2019-06-18
 */
@Data
@ApiModel(description = "新增权限 请求实体")
public class RoleUpdateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @ApiModelProperty("权限ID")
	@NotNull(message="权限ID不能为空")
    private Integer roleKey;
    /**
     * 权限类型
     */
	@ApiModelProperty("权限类型")
    private String roleType;
    /**
     * 权限编码
     */
	@ApiModelProperty("权限编码")
    private String roleCode;
    /**
     * 权限名称
     */
	@ApiModelProperty("权限名称")
    private String roleName;
    /**
     * 权限描述
     */
	@ApiModelProperty("权限描述")
    private String roleDesc;
	/**
	 * 权限描述
	 */
	@ApiModelProperty("数据状态(1:正常，0:删除)")
	private Integer state;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoleAddDto [");
		if (roleType != null)
			builder.append("roleType=").append(roleType).append(", ");
		if (roleCode != null)
			builder.append("roleCode=").append(roleCode).append(", ");
		if (roleName != null)
			builder.append("roleName=").append(roleName).append(", ");
		if (state != null)
			builder.append("state=").append(state).append(", ");
		if (roleDesc != null)
			builder.append("roleDesc=").append(roleDesc);
		builder.append("]");
		return builder.toString();
	}

}
