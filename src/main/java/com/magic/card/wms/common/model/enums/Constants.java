package com.magic.card.wms.common.model.enums;

public class Constants {
	/**
	 * 系统默认数据连接符
	 */
	public static final String DEFAULT_JOINT_MARK = "&&";

	public static final String DEFAULT_USER="system";

	/**
	 * 系统触发执行
	 */
	public static final String TRIGGER_GENERATOR_PICK_USER = "system_trigger";
	/**
	 * 系统定时执行
	 */
	public static final String TIMING_GENERATOR_PICK_USER = "system_timing";

	public static final Integer ACTIVITY_STATE = 1;
	/**
	 * 订单取消CODE
	 */
	public static final String BILL_STATE_CANCEL = "cancel";
	/**
	 * 拣货区CODE JHQ
	 */
	public static final String PICKING_AREA_CODE = "CK-GN-CCQ";
	/**
	 * 存储区CODE
	 */
	public static final String STORAGE_AREA_CODE = "CK-GN-CCQ";

	/**
	 * 数据状态0-失效/未处理
	 */
	public static final Integer STATE_0 = 0;
	/**
	 * 数据状态1-游戏/已处理
	 */
	public static final Integer STATE_1 = 1;
	
	/**
	 * 常量0
	 */
	public static final Integer ZERO = 0;
	/**
	 * 常量0
	 */
	public static final Integer ONE = 1;
	
	/**
	 * 电商标识
	 */
	public static final String ECCOMPANY_ID = "TAOBAO";
	/**
	 * 客户标识
	 */
	public static final String CUSTOMERID = "0";
	/**
	 * 订单类型
	 */
	public static final Integer ORDER_TYPE = 1;
	/**
	 * 服务类型
	 */
	public static final Integer SERVICE_TYPE = 0;
	/**
	 * 业务交易号
	 */
	public static final String TRADENO = "259";
	
	/**
	 * 物流公司ID
	 */
	public static final String LOGISTICPROVIDER_ID = "POSTB";
	/**
	 * 消息类型-创建订单
	 */
	public static final String MSG_TYPE_CREATE = "ORDERCREATE";
	/**
	 * 消息类型-订单更新
	 */
	public static final String MSG_TYPE_UPDATE = "UPDATE";
	
	/**
	 * 单据类型表示-采购单
	 */
	public static final String BILL_TYPE_FLAG_C = "C-";
	/**
	 * 单据类型表示-收货单
	 */
	public static final String BILL_TYPE_FLAG_R = "R-";
	/**
	 * 单据类型表示-上架单
	 */
	public static final String BILL_TYPE_FLAG_S = "S-";

}
