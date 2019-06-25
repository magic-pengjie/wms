package com.magic.card.wms.warehousing.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.warehousing.mapper.PurchaseBillDetailMapper;
import com.magic.card.wms.warehousing.model.po.PurchaseBillDetail;
import com.magic.card.wms.warehousing.service.IPurchaseBillDetailService;

/**
 * <p>
 * 采购单明细表 服务实现类
 * </p>
 *
 * @author pengjie
 * @since 2019-06-22
 */
@Service
public class PurchaseBillDetailServiceImpl extends ServiceImpl<PurchaseBillDetailMapper, PurchaseBillDetail> implements IPurchaseBillDetailService {

	@Override
	public List<PurchaseBillDetail> selectPurchaseBillDetail(long purchaseId) {
		Wrapper<PurchaseBillDetail> w = new EntityWrapper<>();
		w.eq("purchase_id", purchaseId);
		w.eq("state", Constants.STATE_1);
		List<PurchaseBillDetail> list = this.selectList(w);
		return list;
	}

}
