package com.magic.card.wms.baseset.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.dto.StorehouseConfigDTO;
import com.magic.card.wms.baseset.model.po.StorehouseConfig;
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
     * @param loadGrid
     * @return
     */
    LoadGrid loadGrid(LoadGrid loadGrid);

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
     * 入库时推荐库位：优先推荐已使用过的库位
     * @param customerId
     * @param commodityId
     * @return
     */
    List<StorehouseConfigVO> recommendStore(String customerId,String  commodityId);

    /**
     * 客户商品补货推荐数据
     * @param customerCode
     * @param commodityCode
     * @return
     */
    List<Map> replenishmentDataList(String customerCode, String commodityCode);
    
}
