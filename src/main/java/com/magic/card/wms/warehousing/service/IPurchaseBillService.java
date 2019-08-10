package com.magic.card.wms.warehousing.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.warehousing.model.dto.ComfirmReqDTO;
import com.magic.card.wms.warehousing.model.dto.GroundingReqDTO;
import com.magic.card.wms.warehousing.model.dto.PurchaseBillDTO;
import com.magic.card.wms.warehousing.model.dto.BillQueryDTO;
import com.magic.card.wms.warehousing.model.po.PurchaseBill;
import com.magic.card.wms.warehousing.model.vo.PurchaseBillVO;
import com.magic.card.wms.warehousing.model.vo.PurchaseWarehousingVO;

/**
 * <p>
 * 采购单据表 服务类
 * </p>
 *
 * @author pengjie
 * @since 2019-06-22
 */
public interface IPurchaseBillService extends IService<PurchaseBill> {

	/**
	 * 查询采购单列表
	 * @param dto 请求参数
	 * @param page 分页对象
	 * @return
	 */
	Page<PurchaseBillVO> selectPurchaseBillList(BillQueryDTO dto,PageInfo page);
	/**
	 *  上架商品列表查询
	 * @param dto 请求参数
	 * @param page 分页对象
	 * @return
	 */
	Page<PurchaseWarehousingVO> selectWarehousingList(BillQueryDTO dto,PageInfo page);
	
	/**
	 * 新增
	 * @param dto 请求参数
	 * @return
	 */
	void add(PurchaseBillDTO dto) throws BusinessException;
	
	/**
	 * 修改
	 * @param dto 请求参数
	 * @return
	 */
	void update(PurchaseBillDTO dto);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	void delete(long id) throws BusinessException;
	
	/**
	 * 采购单导入
	 * @param file
	 * @return
	 */
	void importPurchase(MultipartFile file) throws BusinessException, IOException;
	
	/***
	 * 确认操作
	 * @param dto
	 */
	String confirm(ComfirmReqDTO dto);
	/***
	 * 上架操作
	 * @param dto
	 */
	void grounding(GroundingReqDTO dto);
	/**
	 * 审批操作
	 * @param purchaseId 采购单id
	 * @param id 商品明细主键
	 * @param result 审批结果
	 * @param approveDesc 审批意见
	 */
	void approve(long purchaseId, long id ,int result, String approveDesc);
	
	/**
	 * 食品预警任务
	 */
	void FoodWarningTask();
	
}
