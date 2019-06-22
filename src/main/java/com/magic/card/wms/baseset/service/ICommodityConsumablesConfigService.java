package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.po.CommodityConsumablesConfig;
import com.magic.card.wms.common.model.LoadGrid;

/**
 * com.magic.card.wms.baseset.service
 * 耗材配置服务接口
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/21/021 17:12
 * @since : 1.0.0
 */
public interface ICommodityConsumablesConfigService extends IService<CommodityConsumablesConfig> {

    /**
     * 数据加载（分页搜索排序）
     * @param loadGrid
     * @return
     */
    LoadGrid loadGrid(LoadGrid loadGrid);
}
