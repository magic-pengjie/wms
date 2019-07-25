package com.magic.card.wms.warehousing.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.mapper.CommodityInfoMapper;
import com.magic.card.wms.baseset.model.po.Commodity;
import com.magic.card.wms.baseset.model.po.WarningAgentInfo;
import com.magic.card.wms.baseset.service.ICommodityStockService;
import com.magic.card.wms.baseset.service.IStorehouseConfigService;
import com.magic.card.wms.baseset.service.IWarningAgentInfoService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.enums.AgentTypeEnum;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.po.CodeProduct;
import com.magic.card.wms.common.service.ICodeProductService;
import com.magic.card.wms.common.utils.DateUtil;
import com.magic.card.wms.common.utils.EasyExcelUtil;
import com.magic.card.wms.warehousing.mapper.PurchaseBillMapper;
import com.magic.card.wms.warehousing.model.BillStateEnum;
import com.magic.card.wms.warehousing.model.dto.BillQueryDTO;
import com.magic.card.wms.warehousing.model.dto.ComfirmReqDTO;
import com.magic.card.wms.warehousing.model.dto.PurchaseBillDTO;
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
	@Autowired
	private ICommodityStockService commodityStockService;
	@Autowired
	private IStorehouseConfigService storehouseConfigService;
	@Autowired
	private CommodityInfoMapper commodityInfoMapper;

	@Override
	public Page<PurchaseBillVO> selectPurchaseBillList(BillQueryDTO dto, PageInfo pageInfo) {
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
		if(checkRepeat(dto.getDetailList())) {
			throw new OperationException(ResultEnum.purchase_commodity_repeat);
		}
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
				Commodity commodity = commodityInfoMapper.selectCommodity(detail.getBarCode(), bill.getCustomerCode());
				if(ObjectUtils.isEmpty(commodity)) {
					throw new OperationException(ResultEnum.purchase_commodity_notexist.getCode(),
							ResultEnum.purchase_commodity_notexist.getMsg().replace("{desc}", detail.getBarCode())) ;
				}
				detail.setCommodityId(String.valueOf(commodity.getId()));
				detail.setPurchaseId(bill.getId());
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
		// add采购单
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
		//采购商品验重
		if(checkRepeat(detailList)) {
			throw new OperationException(ResultEnum.purchase_commodity_repeat);
		}
		
		Date date = new Date();
		bill.setBillState(BillStateEnum.save.getCode());
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
				Commodity commodity = commodityInfoMapper.selectCommodity(detail.getBarCode(), bill.getCustomerCode());
				if(ObjectUtils.isEmpty(commodity)) {
					throw new OperationException(ResultEnum.purchase_commodity_notexist.getCode(),
							ResultEnum.purchase_commodity_notexist.getMsg().replace("{desc}", detail.getBarCode())) ;
				}
				detail.setCommodityId(String.valueOf(commodity.getId()));
				detail.setPurchaseId(bill.getId());
				detail.setCreateUser(Constants.DEFAULT_USER);
				detail.setCreateTime(date);
			});
			purchaseBillDetailService.insertBatch(detailList);
			log.info("add PurchaseBill detail success ");
		}
	}
	
	/**
	 * 采购商品验重
	 * @param detailList 商品明细表
	 * @return
	 */
	public boolean checkRepeat(List<PurchaseBillDetail> detailList) {
		Long count = detailList.stream().map(PurchaseBillDetail::getBarCode).distinct().count();
		if(count<detailList.size()) {
			return true;
		}
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
		this.deleteById(id);
		log.info("delete PurchaseBill success id={}", id);
		Wrapper<PurchaseBillDetail>  w2 = new EntityWrapper<>();
		w2.eq("purchase_id", id);
		purchaseBillDetailService.delete(w2);
		log.info("delete PurchaseBill detail success ");
	}

	@Override
	@Transactional
	public void importPurchase(MultipartFile file) throws BusinessException, IOException {
		List<Object> dataList = EasyExcelUtil.readExcel2(file.getInputStream(), PurchaseBillExcelVO.class, 1, 1);
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


	@Override
	@Transactional
	public String confirm(ComfirmReqDTO dto) {
		PurchaseBill bill = this.selectById(dto.getId());
		Date now = new Date();
		if(StringUtils.isEmpty(bill)) {
			throw new OperationException(ResultEnum.select_purchase_failed);
		}
		String warningStr = null;
		if(BillStateEnum.save.getCode().equals(dto.getBillState())) {
			if(!bill.getBillState().equals(dto.getBillState())) {
				throw new OperationException(ResultEnum.purchase_recevie_failed);
			}
			bill.setBillState(BillStateEnum.recevieing.getCode());
		}else if(BillStateEnum.recevieing.getCode().equals(dto.getBillState())) {
			if(!bill.getBillState().equals(dto.getBillState())) {
				throw new OperationException(ResultEnum.purchase_comfirm_failed);
			}
			bill.setBillState(BillStateEnum.recevied.getCode());
			bill.setReceivUser(Constants.DEFAULT_USER);
			bill.setReceivDate(DateUtil.getStringDateShort());
			//修改收货商品数量
			List<PurchaseBillDetail> detailList = dto.getDetailList();
			warning(bill, detailList);
			purchaseBillDetailService.updateBatchById(detailList);
		}else if(BillStateEnum.recevied.getCode().equals(dto.getBillState())) {
			if(!bill.getBillState().equals(dto.getBillState())) {
				throw new OperationException(ResultEnum.purchase_in_failed);
			}
			bill.setBillState(BillStateEnum.stored.getCode());
			List<PurchaseBillDetail> detailList = dto.getDetailList();
			//入库后修改商品库存
			for (PurchaseBillDetail detail : detailList) {
				if(!ObjectUtils.isEmpty(detail.getStorehouseInfo())) {
					//增加商品对应库位库存
					String storehouseInfo = detail.getStorehouseInfo();
					String[] houses = storehouseInfo.split(";");
					for(int i=0;i<houses.length;i++) {
						if( houses[i].indexOf(":")>-1) {
							String storehouseId = houses[i].split(":")[0];
							int numbers = Integer.parseInt(houses[i].split(":")[1]);
							storehouseConfigService.save(String.valueOf(detail.getCommodityId()), storehouseId, numbers);
						}
					}
				}
				//增加总库存
				commodityStockService.addCommodityStock(bill.getCustomerCode(),detail.getBarCode(), Long.valueOf(detail.getReceivNums()),Constants.DEFAULT_USER);
				
			}
			
		}else if(BillStateEnum.stored.getCode().equals(dto.getBillState())) {
			if(!bill.getBillState().equals(dto.getBillState())) {
				throw new OperationException(ResultEnum.purchase_approve_failed);
			}
			bill.setBillState(BillStateEnum.stored.getCode());
			if(!"1".equals(dto.getApproveResult())) {
				bill.setBillState(BillStateEnum.approve_fail.getCode());
			}
			bill.setApprover(Constants.DEFAULT_USER);
			bill.setApproveTime(now);
		}else {
			throw new OperationException(ResultEnum.opr_type_error); 
		}
		
		bill.setUpdateUser(Constants.DEFAULT_USER);
		bill.setUpdateTime(now);
		this.updateById(bill);
		log.info("recevied confirm PurchaseBill  success id:{}",dto.getId());
		return warningStr;
	}
	
	/**
	 * 如果为食品则校验是否需要预警
	 * 当前日期减去生产日期，所得天数大于保质期天数一半时预警提示
	 * @param bill
	 * @param detail
	 * @throws ParseException 
	 */
	public String warning(PurchaseBill bill ,List<PurchaseBillDetail> detailList){
		String warningStr = null;
		for (PurchaseBillDetail detail : detailList) {
			if(Constants.ONE.equals(bill.getIsFood())) {
				if(ObjectUtils.isEmpty(detail.getProductionDate()) || ObjectUtils.isEmpty(detail.getShilfLife())) {
					throw new OperationException(ResultEnum.purchase_food_error);
				}
				if(null==warningStr) {
					try {
						long date = DateUtil.getTwoDays(DateUtil.getStringDateShort(), detail.getProductionDate());
						if(date>(detail.getShilfLife()/2)) {
							//生成预警信息
							WarningAgentInfo warningAgentInfo = new WarningAgentInfo();
							warningAgentInfo.setFid(String.valueOf(bill.getId()));
							warningAgentInfo.setTypeCode(AgentTypeEnum.food.getCode());
							warningAgentInfo.setTypeName(AgentTypeEnum.food.getName());
							warningAgentInfo.setAgentName("采购单:"+bill.getPurchaseNo()+"食品保质期预警");
							warningAgentInfo.setAgentState(Constants.STATE_0);
							warningAgentInfo.insert();
							return "生产日期至今已超过保质期天数一半";
						}
					} catch (ParseException e) {
						log.error("食品预警日期格式转换错误:{}",e);
					}	
				}
				
			}
		}
		return null;
	}
	
}
