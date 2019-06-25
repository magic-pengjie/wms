package com.magic.card.wms.warehousing.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.po.CodeProduct;
import com.magic.card.wms.common.service.ICodeProductService;
import com.magic.card.wms.common.utils.DateUtil;
import com.magic.card.wms.common.utils.EasyExcelUtil;
import com.magic.card.wms.warehousing.mapper.PurchaseBillMapper;
import com.magic.card.wms.warehousing.model.dto.PurchaseBillDTO;
import com.magic.card.wms.warehousing.model.dto.PurchaseBillQueryDTO;
import com.magic.card.wms.warehousing.model.po.PurchaseBill;
import com.magic.card.wms.warehousing.model.po.PurchaseBillDetail;
import com.magic.card.wms.warehousing.model.vo.PurchaseBillExcelVO;
import com.magic.card.wms.warehousing.model.vo.PurchaseBillVO;
import com.magic.card.wms.warehousing.service.IPurchaseBillDetailService;
import com.magic.card.wms.warehousing.service.IPurchaseBillService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 采购单据表 服务实现类
 * </p>
 *
 * @author pengjie
 * @since 2019-06-22
 */
@Service
@Slf4j
public class PurchaseBillServiceImpl extends ServiceImpl<PurchaseBillMapper, PurchaseBill>
		implements IPurchaseBillService {

	@Autowired
	private PurchaseBillMapper purchaseBillMapper;
	@Autowired
	private IPurchaseBillDetailService purchaseBillDetailService;
	@Autowired
	private ICodeProductService codeProductService;

	@Override
	public Page<PurchaseBillVO> selectPurchaseBillList(PurchaseBillQueryDTO dto, PageInfo pageInfo) {
		long total = purchaseBillMapper.selectPurchaseBillListCount(dto);
		log.info("PurchaseBillServiceImpl selectPurchaseBillList counts={}", total);
		Page<PurchaseBillVO> page = new Page<>(pageInfo.getCurrent(), pageInfo.getPageSize());
		if (total > 0) {
			page.setRecords(purchaseBillMapper.selectPurchaseBillList(page, dto));
		}
		page.setTotal(total);
		return page;
	}

	@Override
	@Transactional
	public void update(PurchaseBillDTO dto) {
		// 根据id修改采购单数据
		PurchaseBill bill = new PurchaseBill();
		BeanUtils.copyProperties(dto, bill);
		Date date = new Date();
		bill.setUpdateUser(Constants.DEFAULT_USER);
		bill.setUpdateTime(date);
		Wrapper<PurchaseBill> w = new EntityWrapper<>();
		w.eq("id", dto.getId());
		this.update(bill, w);
		log.info("update PurchaseBill success id={}", dto.getId());
		List<PurchaseBillDetail> detailList = dto.getDetailList();
		if (!StringUtils.isEmpty(detailList)) {
			// 先删除采购单商品明细、再新增
			Wrapper<PurchaseBillDetail> w1 = new EntityWrapper<>();
			w1.eq("purchase_id", dto.getId());
			purchaseBillDetailService.delete(w1);
			detailList.forEach(detail -> {
				detail.setUpdateUser(Constants.DEFAULT_USER);
				detail.setUpdateTime(date);
			});
			purchaseBillDetailService.insertBatch(detailList);
			log.info("update PurchaseBill detail success ");
		}

	}

	@Override
	@Transactional
	public void add(PurchaseBillDTO dto) throws BusinessException {
		// 根据id修改采购单数据
		PurchaseBill bill = new PurchaseBill();
		BeanUtils.copyProperties(dto, bill);
		add(bill, dto.getDetailList());
	}
	
	/**
	 * 新增
	 * @param bill
	 * @param detailList
	 */
	private void add(PurchaseBill bill,List<PurchaseBillDetail> detailList) {
		Date date = new Date();
		bill.setCreateUser(Constants.DEFAULT_USER);
		bill.setCreateTime(date);
		//生成采购单编码
		CodeProduct codeProduct = new CodeProduct();
		codeProduct.setType("cg");
		codeProduct.setTypeName("采购单编码");
		codeProduct.setProductDate(DateUtil.getTodayShort());
		codeProduct.setLength(3);
		String code = codeProductService.getCode(codeProduct);
		bill.setPurchaseNo(code);
		bill.setName(bill.getCustomerName()+bill.getPurchaseNo()+"采购单");
		if(StringUtils.isEmpty(bill.getMaker())) {
			bill.setMaker(Constants.DEFAULT_USER);
		}
		if(StringUtils.isEmpty(bill.getMakeDate())) {
			bill.setMakeDate(DateUtil.getStringDateShort());
		}
		purchaseBillMapper.insert(bill);
		log.info("add PurchaseBill success id={},ipurchase_no={}", bill.getId(),bill.getPurchaseNo());
		if (!StringUtils.isEmpty(detailList)) {
			detailList.forEach(detail -> {
				detail.setPurchaseId(bill.getId());
				detail.setCreateUser(Constants.DEFAULT_USER);
				detail.setCreateTime(date);
			});
			purchaseBillDetailService.insertBatch(detailList);
			log.info("add PurchaseBill detail success ");
		}
	}
	
	/**
	 * 采购单验重
	 * @param bill 单据表
	 * @param detailList 商品明细表
	 * @return
	 */
	public boolean checkRepeat(PurchaseBill bill,List<PurchaseBillDetail> detailList) {
		return false;
	}

	@Override
	@Transactional
	public void delete(long id) throws BusinessException {
		//只有保存状态的单据可删除
		Wrapper<PurchaseBill> w = new EntityWrapper<>();
		w.eq("id", id);
		PurchaseBill bill = this.selectById(id);
		if(StringUtils.isEmpty(bill)) {
			throw new BusinessException(ResultEnum.delete_purchase_not_exsit);
		}
		if(!"save".equals(bill.getBillState())) {
			throw new BusinessException(ResultEnum.delete_purchase_state_error);
		}
		this.delete(id);
		log.info("delete PurchaseBill success id={}", id);
		Wrapper<PurchaseBillDetail>  w2 = new EntityWrapper<>();
		w2.eq("purchase_id", id);
		purchaseBillDetailService.delete(w2);
		log.info("delete PurchaseBill detail success ");
	}

	@Override
	public void importPurchase(MultipartFile file) throws BusinessException, IOException {
		List<PurchaseBillExcelVO> dataList = EasyExcelUtil.readExcel(file.getInputStream(), PurchaseBillExcelVO.class, 0, 1);
		if(StringUtils.isEmpty(dataList)) {
			throw new BusinessException(ResultEnum.purchase_file_size_zero);
		}
		PurchaseBill bill = new PurchaseBill();
		BeanUtils.copyProperties(dataList.get(0),bill);
		List<PurchaseBillDetail> detailList = new ArrayList<PurchaseBillDetail>();
		dataList.forEach(data->{
			PurchaseBillDetail detail = new PurchaseBillDetail();
			BeanUtils.copyProperties(data,detail);
			detailList.add(detail);
		});
		add(bill, detailList);
	}

}
