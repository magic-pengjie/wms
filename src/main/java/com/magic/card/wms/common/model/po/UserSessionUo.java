package com.magic.card.wms.common.model.po;

import java.io.Serializable;

import lombok.Data;

/**
 * 登录态Session实体
 *  @author Zhouhao
 *	@date2019年7月4日17:19:56
 *	@version 1.0
 */
@Data
public class UserSessionUo implements Serializable{

	private static final long serialVersionUID = -3496444004156165151L;
	/**
     * 	用户ID
     */
    private Long id;
    /**
     * 	用户名
     */
    private String userNo;
    /**
     *	 密码
     */
//    private String password;
    /**
     *	 姓名
     */
    private String name;
     /**
	  *  客户主键ID
     */
    private String customerId;
     /**
	  *	 客户编码
     */
    private String customerCode;
    /**
	 *	 客户名称
     */
	private String customerName;
    /**
	 *	 品牌id
     */
	private String brandId;
	/**
	 *	 公司地址
	 */
	private String address;
    /**
	 *	 联系人
     */
	private String contactPerson;
    /**
	 *	 联系电话
     */
	private String phone;
    /**
	 *	 租用面积
     */
	private String store_area;

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("UserSessionUo{");
		sb.append("id=").append(id);
		sb.append(", userNo='").append(userNo).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", customerId='").append(customerId).append('\'');
		sb.append(", customerCode='").append(customerCode).append('\'');
		sb.append(", customerName='").append(customerName).append('\'');
		sb.append(", brandId='").append(brandId).append('\'');
		sb.append(", address='").append(address).append('\'');
		sb.append(", contactPerson='").append(contactPerson).append('\'');
		sb.append(", phone='").append(phone).append('\'');
		sb.append(", store_area='").append(store_area).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
