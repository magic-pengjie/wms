package com.magic.card.wms.warehousing.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.common.model.PageInfo;
import com.magic.card.wms.warehousing.model.dto.ReturnGoodsDTO;
import com.magic.card.wms.warehousing.model.dto.ReturnGoodsRecordsDTO;
import com.magic.card.wms.warehousing.model.po.ReturnGoodsRecords;
import com.magic.card.wms.warehousing.model.vo.ReturnGoodsVO;

/**
 * <p>
 * 退货记录表 服务类
 * </p>
 *
 * @author pengjie
 * @since 2019-08-06
 */
public interface IReturnGoodsRecordsService extends IService<ReturnGoodsRecords> {

	/**
	 * 查询退货记录
	 * @param page 分页对象
	 * @param dto 查询条件对象
	 * @return
	 */
	Page<ReturnGoodsVO> selectReturnList(PageInfo page,ReturnGoodsDTO dto);
	
	/**
	 * 退货和上架
	 * @param dto
	 */
	void returnAndGrounding(ReturnGoodsRecordsDTO dto);
}
