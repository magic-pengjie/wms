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
    private String password;
    /**
     *	 姓名
     */
    private String name;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserSessionUo [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (userNo != null)
			builder.append("userNo=").append(userNo).append(", ");
		if (password != null)
			builder.append("password=").append(password).append(", ");
		if (name != null)
			builder.append("name=").append(name);
		builder.append("]");
		return builder.toString();
	}
	
}
