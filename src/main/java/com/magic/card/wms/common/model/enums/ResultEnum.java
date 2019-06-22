package com.magic.card.wms.common.model.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 统一返回枚举类
 * 公共错误类:100000开始
 * 用户管理错误:200000开始
 * 基础管理:300000开始
 * 入库管理:400000 开始
 * 出库管理:500000开始
 * 报表      : 600000开始
 * @author PENGJIE
 * @date 2019年6月13日
 */
@AllArgsConstructor
public enum ResultEnum {
	
	success(0,"成功"),
	fail(-1,"失败"),
	req_params_error(100000,"请求参数异常"),
	req_params_null(100001,"请求参数为空"),
	query_failed(100002,"查询异常"),
	
	query_user_failed(200000,"查询用户失败"),
	add_user_failed(200001,"新增用户失败"),
	add_user_exist(200002,"查询用户已存在，请重新新增"),
	user_login_failed(200003,"用户登录失败"),
	user_name_not_exist(200004,"用户不存在"),
	user_pwd_error(200005,"用户或密码不正确，请确认！"),
	user_state_error(200007,"用户状态不正确，请确认！")
	;
	
	
	private static Map<Integer,ResultEnum> enumMap = new HashMap();
	static {
		for (ResultEnum e : EnumSet.allOf(ResultEnum.class)) {
			enumMap.put(e.code, e);
		}
	}
	@Setter
	@Getter
	private Integer code;
	@Setter
	@Getter
	private String msg;
	
	
}
