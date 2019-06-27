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
	query_error(100003,"查询失败"),
	add_error(100004,"新增失败"),
	update_error(100005,"修改失败"),
	delete_error(100006,"删除失败"),
	download_error(100007,"下载失败"),
	upload_error(100008,"上传失败"),
	data_check_exist(10009, "数据检测已存在"),
	data_add_failed(10010, "数据添加失败"),
	data_update_failed(10011, "数据更新失败"),
	data_delete_failed(10012, "数据删除失败"),
	
	query_user_failed(200000,"查询用户失败"),
	add_user_failed(200001,"新增用户失败"),
	add_user_exist(200002,"查询用户已存在，请重新新增"),
	user_login_failed(200003,"用户登录失败"),
	user_name_not_exist(200004,"用户不存在"),
	user_pwd_error(200005,"用户或密码不正确，请确认！"),
	user_state_error(200007,"用户状态不正确，请确认！"),
	dist_exist(20008, "常量的code已经存在，请重新填写"),
	store_house_error(20009, "仓库信息异常"),
	query_role_failed(200010,"查询角色列表失败"),
	add_role_failed(200011,"新增角色失败"),
	
	add_purchase_repeat(400001,"采购单已存在、新增失败"),
	delete_purchase_not_exsit(400002,"采购单不存在删除失败"),
	delete_purchase_state_error(400003,"采购单已确认删除失败"),
	purchase_file_size_zero(400004,"上传文件为空"),
	purchase_file_is_null(400005,"上传数据为空"),
	
	
	unkown_error(999999, "系统异常");
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
