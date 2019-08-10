package com.magic.card.wms.warehousing.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.magic.card.wms.warehousing.model.dto.ReturnGoodsDTO;
import com.magic.card.wms.warehousing.model.po.ReturnGoodsRecords;
import com.magic.card.wms.warehousing.model.vo.ReturnGoodsVO;

/**
 * <p>
 * 退货记录表 Mapper 接口
 * </p>
 *
 * @author pengjie
 * @since 2019-08-06
 * 
 */
public interface ReturnGoodsRecordsMapper extends BaseMapper<ReturnGoodsRecords> {

	/**
	 * 查询退货记录
	 * @param page 分页对象
	 * @param dto 查询条件对象
	 * @return
	 */
	List<ReturnGoodsVO> selectReturnList(Pagination page ,ReturnGoodsDTO dto);
	/**
	 * 查询退货记录数
	 * @param dto 查询条件对象
	 * @return
	 */
	long selectReturnListCount(ReturnGoodsDTO dto);
}
