package com.magic.card.wms.baseset.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.po.StorehouseInfo;
import com.magic.card.wms.statistic.model.dto.QueryStoreInfoBO;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.magic.card.wms.baseset.model.po.StorehouseConfig;
import com.magic.card.wms.baseset.model.vo.StorehouseConfigVO;
import com.magic.card.wms.check.model.dto.CheckRecordInfoDto;
import com.magic.card.wms.check.model.dto.QueryCheckRecordDto;

/**
 * com.magic.card.wms.baseset.mapper
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/19/019 17:42
 * @since : 1.0.0
 */
public interface StorehouseConfigMapper extends BaseMapper<StorehouseConfig> {
	
	/**
	 * 按商家或商品查询库存
	 */
	List<CheckRecordInfoDto> queryCommoidtyStoreList(@Param("cr") QueryCheckRecordDto params);

	/**
	 * 加载库位配置信息
	 * @param wrapper
	 * @return
	 */
	List<Map> storehouseConfig(@Param("ew")Wrapper wrapper);

	/**
	 * 加载库位配置信息
	 * @param page
	 * @param entityWrapper
	 * @return
	 */
    List<Map> loadGrid(Page page, @Param("ew") EntityWrapper entityWrapper);
    
    /**
     * 入库推荐库位查询
     * @param customerCode
     * @return
     */
    List<StorehouseConfigVO> recommendStore(@Param("type") String type, @Param("customerCode")String customerCode,@Param("barCode") String barCode);

	/**
	 * 查询商家库位信息List
	 * @param available  该商家已用库存
	 * @param unavailable 该商家 未用库存
	 * @param commodityId 该商家，商品占用库存
	 * @param usedStores 该商家 占用库存
	 * @return list
	 */
	List<CheckRecordInfoDto> queryStoreInfoList(@Param("qsi")QueryStoreInfoBO storeInfoBO);

	/**
	 * 增加商品库位的可用量
	 * @param storeConfigId 库位配置ID
	 * @param plusNum 增量
	 * @param operator 操作人
	 */
    Integer plusAvailableQuantity(@Param("storeConfigId") String storeConfigId, @Param("plusNum") Long plusNum, @Param("operator") String operator);

    /**
	 * 减少商品库位的可用量
	 * @param storeConfigId 库位配置ID
	 * @param reduceNum 减少量
	 * @param operator 操作人
	 */
    Integer reduceAvailableQuantity(@Param("storeConfigId") String storeConfigId, @Param("reduceNum") Long reduceNum, @Param("operator") String operator);

	List<Long> queryStoreIdList(@Param("customerId")String customerId, @Param("areaCodeList")List<String> areaCodeList);

	/**
	 * 搜索当前库位中有多少个拣货区库位
	 * @param storehouseIds 库位配置IDS
	 * @param code 拣货区Code
	 * @return
	 */
    Integer JHQCount(@Param("storehouseIds") Long[] storehouseIds, @Param("code") String code);

	/**
	 * 获取当前商家商品在货区配置个数
	 * @param customerId 商家ID
	 * @param commodityId 商家商品ID
	 * @param code 库区类型
	 * @return
	 */
	Integer customerCommodityConfigCount(@Param("customerId") String customerId, @Param("commodityId") String commodityId, @Param("code") String code);

	/**
	 *	获取配置库位信息
	 * @param customerId 客户ID
	 * @param commodityId 客户商品ID
	 * @param code 库位类型Code
	 * @return
	 */
	List<StorehouseInfo> customerCommodityConfig(@Param("customerId") String customerId, @Param("commodityId") String commodityId, @Param("code") String code);
}
