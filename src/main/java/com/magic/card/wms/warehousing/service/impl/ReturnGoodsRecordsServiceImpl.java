package com.magic.card.wms.warehousing.service.impl;

import com.magic.card.wms.warehousing.model.dto.ReturnGoodsDTO;
import com.magic.card.wms.warehousing.model.dto.ReturnGoodsRecordsDTO;
import com.magic.card.wms.warehousing.model.po.ReturnGoodsRecords;
import com.magic.card.wms.warehousing.model.vo.PurchaseBillVO;
import com.magic.card.wms.warehousing.model.vo.ReturnGoodsVO;
import com.magic.card.wms.baseset.service.ICommodityStockService;
import com.magic.card.wms.baseset.service.IStorehouseConfigService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.warehousing.mapper.ReturnGoodsRecordsMapper;
import com.magic.card.wms.warehousing.service.IReturnGoodsRecordsService;

import lombok.extern.slf4j.Slf4j;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 退货记录表 服务实现类
 * </p>
 *
 * @author pengjie
 * @since 2019-08-06
 */
@Service
@Slf4j
public class ReturnGoodsRecordsServiceImpl extends ServiceImpl<ReturnGoodsRecordsMapper, ReturnGoodsRecords> implements IReturnGoodsRecordsService {
	@Autowired
	private ReturnGoodsRecordsMapper returnGoodsRecordsMapper;
	@Autowired
	private IStorehouseConfigService storehouseConfigService;
	@Autowired
	private ICommodityStockService commodityStockService;
	
	@Override
	public Page<ReturnGoodsVO> selectReturnList(PageInfo pageInfo, ReturnGoodsDTO dto) {
		long total = returnGoodsRecordsMapper.selectReturnListCount(dto);
		log.info("PurchaseBillServiceImpl selectPurchaseBillList counts={}", total);
		Page<ReturnGoodsVO> page = new Page<>(pageInfo.getCurrent(), pageInfo.getPageSize());
		if (total > 0) {
			page.setRecords(returnGoodsRecordsMapper.selectReturnList(page, dto));
		}
		page.setTotal(total);
		return page;
	}

	@Override
	public void returnAndGrounding(ReturnGoodsRecordsDTO dto) {
		
		if(!ObjectUtils.isEmpty(dto.getResidualStorehouseId()) && !ObjectUtils.isEmpty(dto.getResidualStorehouse()) && dto.getResidualNums()>0) {
			//修改残次品库存
			storehouseConfigService.save(dto.getCommodityId(), dto.getResidualStorehouseId(), dto.getResidualNums());
		}else if(!ObjectUtils.isEmpty(dto.getNormalStorehouseId()) && !ObjectUtils.isEmpty(dto.getNormalStorehouse())  && dto.getNormalNums()>0) {
			//修改正常品库存
			storehouseConfigService.save(dto.getCommodityId(), dto.getNormalStorehouseId(), dto.getNormalNums());
			//修改总库存
			commodityStockService.addCommodityStock(dto.getCustomerCode(),dto.getCommodityCode(), Long.valueOf(dto.getNormalNums()),Constants.DEFAULT_USER);
		}else {
			throw new OperationException(-1, "退货操作,上架库位、数量必输"); 
		}
		//保存退货记录
		ReturnGoodsRecords record = new ReturnGoodsRecords();
		BeanUtils.copyProperties(dto, record);
		record.setCreateTime(new Date());
		record.setCreateUser(Constants.DEFAULT_USER);
		this.insert(record);
	
}

}
