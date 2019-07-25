package com.magic.card.wms.common.model.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.magic.card.wms.common.mapper.CodeProductMapper;
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
	upload_file_inexistence(10013, "上传文件不存在"),
	upload_file_suffix_err(10014, "上传文件格式有误"),
	upload_file_resolve_err(10015, "上传文件解析异常"),
	system_busy(11111, "系统繁忙请稍后再试"),

	commodity_stock_setting_deficiency(10013, "当前商品库存不存在！"),

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


	excel_import_storehouse(300000,"Excel导入仓库库位信息失败！"),
	customer_no_exist(300001, "商家不存在！"),
	customer_bind_commodity(300002, "当前商家已绑定商品条码"),
	store_house_config_exist(300003, "当前库位已经配置，请勿重复配置"),


	select_purchase_failed(400000,"查询单据失败"),
	add_purchase_repeat(400001,"采购单已存在、新增失败"),
	delete_purchase_not_exsit(400002,"采购单不存在删除失败"),
	delete_purchase_state_error(400003,"采购单已确认删除失败"),
	purchase_file_size_zero(400004,"上传文件为空"),
	purchase_file_is_null(400005,"上传数据为空"),
	opr_type_error(400006,"操作类型有误"),
	purchase_recevie_failed(400007,"状态为保存才允许开始收货"),
	purchase_comfirm_failed(400008,"状态为待收货才允许确认收货"),
	purchase_in_failed(400009,"状态为待收货才允许确认收货"),
	purchase_approve_failed(400010,"状态已入库才允许审核"),

	invoice_pick_no(500000, "拣货单号不可为空"),
	invoice_pick_commodity_exist(50001, "该清单不需要此类商品，检错了呦！"),
	invoice_pick_commodity_overflow(50002, "该清单商品需求已满，拣多喽！"),
	invoice_pick_commodity_omit(50004, "商品漏检"),
	invoice_pick_finish(50003, "拣货单已完成"),
	invoice_pick_close(50005, "复检已经结束，请勿重复操作"),
	invoice_pick_lock(50012, "拣货单已锁定，请确认后解锁！"),
	order_not_exist(50006, "当前订单不存在，请确认后在提交"),
	order_cancel(50007, "当前订单已取消，请将商品放回库位"),
	order_weight_warning(50008, "称重不在预定范围值内"),
	order_package_no_hc(50009, "当前订单商品没有设置耗材"),
	order_lock(50010, "订单超过15分钟已锁定"),
	order_package_no_exist(50011, "订单包裹不存在，请核实包裹快递单号！"),


	express_fee_config_exist(7000, "此快递费配置数据已存在"),

	check_signature_error(900000, "验签不通过"),
	data_repeat(900001, "数据重复"),
	data_error(900099, "数据处理失败"),
	
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
