package com.magic.card.wms.common.model.enums;

public class Constants {
	

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
	 * 数据状态0-失效
	 */
	public static final Integer STATE_0 = 0;
	/**
	 * 数据状态0-游戏
	 */
	public static final Integer STATE_1 = 1;

}
