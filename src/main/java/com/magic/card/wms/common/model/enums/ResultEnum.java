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
	req_body_json_null(10016, "请求body json数据不可为空！"),
	req_body_json_resolve(10017, "请求body json数据解析错误！"),
	req_body_json_param_requrie(10018, "请求参数 %s 是必须提供的！"),

	system_busy(11111, "系统繁忙请稍后再试"),

	commodity_stock_setting_deficiency(10013, "当前商品设置库位！"),

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
	split_rule_exist( 200012, "拆包规则已存在！"),
	split_rule_not_exist( 200013, "拆包规则不存在！"),
    store_house_config_jhq_max_one(200014, "商家商品配置拣货区库位只能存在一个"),
    store_house_config_jhq_exist(200015, "商家商品已配置拣货区库位"),


	excel_import_storehouse(300000,"Excel导入仓库库位信息失败！"),
	customer_no_exist(300001, "商家不存在！"),
	customer_bind_commodity(300002, "当前商家已绑定商品条码"),
	store_house_config_exist(300003, "当前库位已经配置，请勿重复配置"),
	consumable_config_exist( 300004, "商品耗材已配置"),
	replenishment_no_exist( 30005, "补货单不存在"),
	replenishment_finnished( 30006, "补货单已补货完成"),
	express_number_size_err( 30007,  "快递单号格式错误，有且只有十三位数字！"),
	express_number_exist( 30008,  "快递单号已存在！"),
	express_number_not_exist( 30009,  "快递单号不存在！"),
	express_number_exhaust( 30010, "快递单号已耗尽！"),
    express_provider_not_support( 30011, "暂不支持此提供商！"),

	select_purchase_failed(400000,"查询单据失败"),
	add_purchase_repeat(400001,"采购单已存在、新增失败"),
	delete_purchase_not_exsit(400002,"采购单不存在删除失败"),
	delete_purchase_state_error(400003,"采购单已确认删除失败"),
	purchase_file_size_zero(400004,"上传文件为空"),
	purchase_file_is_null(400005,"上传数据为空"),
	opr_type_error(400006,"操作类型有误"),
	purchase_update_failed(400007,"状态为保存才允许修改"),
	purchase_comfirm_failed(400007,"状态为保存才允许确认"),
	purchase_recevie_failed(400008,"状态为确认才允许收货"),
	purchase_in_failed(400009,"状态为待收货才允许确认收货"),
	purchase_approve_failed(400010,"状态已入库或者审批失败才允许审核"),
	purchase_commodity_repeat(400011,"采购单商品重复"),
	purchase_commodity_notexist(400012,"此商品未在本系统维护，请联系管理员添加{desc}"),
	purchase_food_error(400013,"商品为食品,则生产日期和保质期必填"),
	

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
	order_excel_import_err(500012, "Excel订单导入失败"),
	order_excel_export_err( 500013, "订单Excel数据导出失败"),
	order_excel_import_distinct( 500014, "订单Excel数据导入订单号有重复，请核实后在导入！"),
	order_excel_import_exist(500015, "订单Excel数据导入订单号已存在，请勿重复导入！"),
	order_commodity_no_exist( 500016, "订单商品不存在，无法完成分包！"),
	order_had_split_package( 500017,  "订单已经拆包, 请勿重复操作！"),
	package_had_weigh( 500018, "订单已经称重完毕，请勿重复操作"),
	monitoring_type_no_exist( 500019, "包裹监控状态不存在！"),
    order_not_allow_cancel( 500020, "当前订单不允许取消！"),
	invoice_pick_cancel( 500021, "拣货单已作废！"),
	invoice_pick_not_allow_cancel( 500022, "当前拣货单不可作废！"),
    order_merge_arguments_err( 500023, "合并订单数据不符合要求，至少提供两个系统订单号！"),
    order_merge_arguments_not_exist( 500024, "提供的订单号，可能有误！"),
    order_merge_commodity_err(500025, "合并订单商品数据不存在，请核实后再重新操作！"),
	order_excel_export_no_data( 500026, "没有满足条件的订单数据"),
	order_split_package_commodity_number_err(500027, "订单拆包商品数量有误，请确认后在提交！"),
	order_customer_commodity_not_exist( 500028, "商家订单中部分商品没有再系统中维护，请确认后再导入！"),


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
