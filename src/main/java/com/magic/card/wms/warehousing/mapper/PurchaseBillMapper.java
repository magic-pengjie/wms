package com.magic.card.wms.warehousing.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.magic.card.wms.statistic.model.dto.ParchaseBillResponseDto;
import com.magic.card.wms.warehousing.model.dto.BillQueryDTO;
import com.magic.card.wms.warehousing.model.po.PurchaseBill;
import com.magic.card.wms.warehousing.model.vo.PurchaseBillVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 采购单据表 Mapper 接口
 * </p>
 *
 * @author pengjie
 * @since 2019-06-22
 */
public interface PurchaseBillMapper extends BaseMapper<PurchaseBill> {
	/**
	 * 查询采购单列表
	 * @param dto 请求参数
	 * @param page 分页对象
	 * @return
	 */
	List<PurchaseBillVO> selectPurchaseBillList(Pagination page ,BillQueryDTO dto);
	/**
	 * 查询采购单总条数
	 * @param dto 请求参数
	 * @return
	 */
	Long selectPurchaseBillListCount(BillQueryDTO dto);
	/**
	 * 采购单验重
	 * @return 大于0表示重复
	 */
	int checkRepeat(Map map);
	
	/***
	 * 获取需要预警的采购单数据
	 * @param page
	 * @return
	 */
	List<PurchaseBill> getFoodWarningList(Pagination page);


	/**
	 * 入库报表统计查询
	 * @param customerCode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<ParchaseBillResponseDto> queryPurchaseBillCountList(@Param("customerCode")String customerCode,@Param("startDate")String startDate,@Param("endDate")String endDate);

}
