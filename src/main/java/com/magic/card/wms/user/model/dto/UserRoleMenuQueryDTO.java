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
     * 用户主键
     */
	@ApiModelProperty(value="用户主键")
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
	 * 客户编码
	 */
	@ApiModelProperty(value="客户编码")
	private String customerCode;
	/**
	 * 客户名称
	 */
	@ApiModelProperty(value="客户名称")
	private String customerName;
	/**
	 * 品牌id
	 */
	@ApiModelProperty(value="品牌id")
	private String brandId;
	/**
	 * 公司地址
	 */
	@ApiModelProperty(value="公司地址")
	private String address;
	/**
	 * 联系人
	 */
	@ApiModelProperty(value="联系人")
	private String contactPerson;

	/**
	 * 联系电话
	 */
	@ApiModelProperty(value="联系电话")
	private String phone;

	/**
	 * 租用面积
	 */
	@ApiModelProperty(value="租用面积")
	private Double storeArea;
	/**
	 * 角色信息List
	 */
	@ApiModelProperty(value="角色List")
	private List<RoleInfo> roleList;
	
	/**
	 * 菜单信息List
	 */
	@ApiModelProperty(value="菜单信息List")
	private List<MenuQueryResponseDto> menuList;

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("UserRoleMenuQueryDTO{");
		sb.append("userKey=").append(userKey);
		sb.append(", userNo='").append(userNo).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", customerCode='").append(customerCode).append('\'');
		sb.append(", customerName='").append(customerName).append('\'');
		sb.append(", brandId='").append(brandId).append('\'');
		sb.append(", address='").append(address).append('\'');
		sb.append(", contactPerson='").append(contactPerson).append('\'');
		sb.append(", phone='").append(phone).append('\'');
		sb.append(", storeArea=").append(storeArea);
		sb.append(", roleList=").append(roleList);
		sb.append(", menuList=").append(menuList);
		sb.append('}');
		return sb.toString();
	}
}
