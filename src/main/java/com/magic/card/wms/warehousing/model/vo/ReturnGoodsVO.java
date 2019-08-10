package com.magic.card.wms.warehousing.model.vo;

import com.magic.card.wms.warehousing.model.po.ReturnGoodsRecords;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 已退货记录视图
 * @author PENGJIE
 * @date 2019年8月6日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ReturnGoodsVO extends ReturnGoodsRecords{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 商家名称
	 */
	private String customerName;
	/**
	 * 商品名称
	 */
	private String commodityName;
	/**
	 * 商家规格
	 */
	private String spec;
	/**
	 * 商品型号
	 */
	private String modelNo;
	/**
	 * 商品品牌
	 */
	private String banner;
}
