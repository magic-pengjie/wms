package com.magic.card.wms.warehousing.service;

import com.magic.card.wms.warehousing.model.po.PurchaseBillDetail;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 采购单明细表 服务类
 * </p>
 *
 * @author pengjie
 * @since 2019-06-22
 */
public interface IPurchaseBillDetailService extends IService<PurchaseBillDetail> {

	/**
	 * 采购单商品明细查询
	 * @param purchaseId
	 * @return 采购单id
	 */
	List<PurchaseBillDetail> selectPurchaseBillDetail(long purchaseId);
}
