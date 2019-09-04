package com.magic.card.wms.baseset.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.dto.BatchBindStorehouseDTO;
import com.magic.card.wms.baseset.model.dto.BatchStorehouseConfigDTO;
import com.magic.card.wms.baseset.model.dto.StorehouseConfigDTO;
import com.magic.card.wms.baseset.model.dto.invoice.OmitStokeDTO;
import com.magic.card.wms.baseset.model.po.StorehouseConfig;
import com.magic.card.wms.baseset.model.vo.AvailableQuantityVO;
import com.magic.card.wms.baseset.model.vo.StorehouseConfigVO;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.LoadGrid;

/**
 * com.magic.card.wms.baseset.service
 * 库位配置服务
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/19/019 17:45
 * @since : 1.0.0
 */
public interface IStorehouseConfigService extends IService<StorehouseConfig> {
    /**
     * 查询仓库配置信息
     *
     * @param loadGrid
     */
    void loadGrid(LoadGrid loadGrid);


    /**
     * 添加库位配置
     * @param storehouseConfigDTO
     * @param operator
     * @return
     */
    void add(StorehouseConfigDTO storehouseConfigDTO, String operator);

    /**
     * 修改库位配置
     * @param storehouseConfigDTO
     * @param operator
     * @return
     */
    void update(StorehouseConfigDTO storehouseConfigDTO, String operator);

    /**
     * 删除数据
     * @param id
     */
    void delete(Long id);

    /**
     * 修改库位数量
     * @param commodityId 商品id
     * @param storehouseId 库位id
     * @param numbers 增加值
     */
    void save(String commodityId,String storehouseId,int numbers);
    /**
         * 修改库位数量
     * @param commodityId 商品id
     * @param storehouseId 库位id
     * @param numbers 增加值
     * @param startTime 生产日期
     * @param shilfLife 保质期
     */
    void save(String commodityId,String storehouseId,int numbers,String startTime,Double shilfLife );
    /**
     * 入库时推荐库位
     * @param customerCode 客户编码
     * @param barCode 商品条码
     * @return
     */
    List recommendStore(String type,String customerCode,String barCode);

    /**
     * 客户商品补货推荐数据
     * @param customerCode
     * @param commodityCode
     * @return
     */
    List<Map> replenishmentDataList(String customerCode, String commodityCode);

    /**
     * 库位批量绑定
     * @param batchBindStorehouseDTO
     */
    void batchBind(BatchBindStorehouseDTO batchBindStorehouseDTO);

    /**
     * 推荐存储库位信息
     * @param commodityIds 客户关系商品IDs
     * @return
     */
    List<Map> recommendCustomerCommodityStorage(List<String> commodityIds);

    /**
     * 批量配置库位信息
     * @param batchStorehouseConfig
     */
    void batchConfig(BatchStorehouseConfigDTO batchStorehouseConfig);

    /**
     * 批量清空库位商品
     * @param ids
     */
    void batchClearCommodity(List<String> ids);

    /**
     * 获取缺货商品的库位编码
     * @param omitStokeDTO
     * @return
     */
    Map<String, String> invoiceOmitStoke(OmitStokeDTO omitStokeDTO);

    /**
     * 增加库位商品可用量
     * @param storeConfigId 配置库位ID
     * @param plusNum 增加用量
     * @param operator 操作人
     */
    void plusAvailableQuantity(String storeConfigId, Long plusNum, String operator);

    /**
     * 减少库位商品可用量
     * @param storeConfigId 配置库位ID
     * @param reduceNum 增加用量
     * @param operator 操作人
     */
    void reduceAvailableQuantity(String storeConfigId, Long reduceNum, String operator);

    /**
     * 获取商品库位数据
     * @param customerCode 商家Code
     * @param commodityCodes 多个商品Code
     * @param houseCode 库位类型（存储区、拣货区...）
     * @return
     */
    List<AvailableQuantityVO> availableQuantity(String customerCode, Set<String> commodityCodes, String houseCode);
}
