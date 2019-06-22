package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.mapper.StorehouseConfigMapper;
import com.magic.card.wms.baseset.model.dto.StorehouseConfigDTO;
import com.magic.card.wms.baseset.model.po.StorehouseConfig;
import com.magic.card.wms.baseset.service.IStorehouseConfigService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.po.PoUtils;
import org.springframework.stereotype.Service;

/**
 * com.magic.card.wms.baseset.service.impl
 * 库位配置服务实现类
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/19/019 17:46
 * @since : 1.0.0
 */
@Service
public class StorehouseConfigServiceImpl extends ServiceImpl<StorehouseConfigMapper, StorehouseConfig> implements IStorehouseConfigService {

    /**
     * 添加库位配置
     *
     * @param storehouseConfigDTO
     * @param operator
     * @return
     */
    @Override
    public Boolean addStorehouseConfig(StorehouseConfigDTO storehouseConfigDTO, String operator) {
        checkStorehouseConfig(storehouseConfigDTO, false);
        StorehouseConfig config = new StorehouseConfig();
        PoUtils.add(storehouseConfigDTO, config, operator);

        return this.insert(config);
    }

    /**
     * 修改库位配置
     *
     * @param storehouseConfigDTO
     * @param operator
     * @return
     */
    @Override
    public Boolean updateStorehouseConfig(StorehouseConfigDTO storehouseConfigDTO, String operator) {
        checkStorehouseConfig(storehouseConfigDTO, true);
        StorehouseConfig config = new StorehouseConfig();
        PoUtils.update(storehouseConfigDTO, config, operator);
        return this.updateById(config);
    }

    /**
     * 库位配置添加修改数据检查
     * @param storehouseConfigDTO
     * @param updateOperation
     */
    private void checkStorehouseConfig(StorehouseConfigDTO storehouseConfigDTO, Boolean updateOperation) {

        if (updateOperation && (storehouseConfigDTO.getId() == null || storehouseConfigDTO.getId() == 0l))
            throw new BusinessException(ResultEnum.req_params_error.getCode(), "库位配置信息修改ID异常");

        // 检查当前库位是否已经使用
        EntityWrapper<StorehouseConfig> wrapper = new EntityWrapper<>();
        wrapper.eq("state", 1)
                .eq("storehouse_id", storehouseConfigDTO.getStorehouseId());

        if (updateOperation) {
            wrapper.ne("id", storehouseConfigDTO.getId());
        }

        if (this.selectCount(wrapper) > 0)
            throw new BusinessException(ResultEnum.data_check_exist.getCode(), "库区已经被配置");
    }
}
