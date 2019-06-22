package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.dto.StorehouseConfigDTO;
import com.magic.card.wms.baseset.model.po.StorehouseConfig;
import com.magic.card.wms.common.exception.BusinessException;

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
     * 添加库位配置
     * @param storehouseConfigDTO
     * @param operator
     * @return
     */
    Boolean addStorehouseConfig(StorehouseConfigDTO storehouseConfigDTO, String operator);

    /**
     * 修改库位配置
     * @param storehouseConfigDTO
     * @param operator
     * @return
     */
    Boolean updateStorehouseConfig(StorehouseConfigDTO storehouseConfigDTO, String operator);

}
