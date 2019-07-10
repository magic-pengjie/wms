package com.magic.card.wms.foreign.model.dto;

import java.util.Date;
import java.util.List;

import com.magic.card.wms.baseset.model.po.OrderCommodity;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * 订单基本信息
 * @author PENGJIE
 * @date 2019年7月5日
 */
@XStreamAlias("requestOrder")
@Data
public class RequestOrder {
	/**
	 * 电商标识 not null
	 */
	private String ecCompanyId;
	/**
	 * 物流公司ID not null
	 */
	private String logisticProviderID;
	/**
	 * 客户标识 
	 */
	private String customerId;
	/**
	 * 物流订单号 not null 
	 */
	private String txLogisticID;
	/**
	 * 业务交易号（新业务类型待定：252国内小包）
	 */
	private String tradeNo="252";
	/**
	 * 物流运单号 not null
	 */
	private String mailNo;
	/**
	 * 订单类型(0-COD 1-普通订单 
	3 - 退货单),标准接口默认设置为1 not null
	 */
	private Integer orderType=1;
	/**
	 * 服务类型(0-自己联系 1-在线下单（上门揽收）4-限时物流 8-快捷COD 16-快递保障)，标准接口默认设置为0 not null
	 */
	private Integer serviceType=0;
	/**
	 * 物流公司取货开始时间
	 */
	private Date sendStartTime;
	/**
	 * 物流公司取货结束时间
	 */
	private Date sendEndTime;
	/**
	 * 收货人
	 */
	@XStreamAlias("receiver")
	private Persion receiver;
	/**
	 * 发货人
	 */
	@XStreamAlias("sender")
	private Persion sender;
	/**
	 * 商品列表
	 */
	@XStreamAlias("items")
	private List<OrderCommodity> orderCommodity;
}
