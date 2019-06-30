package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.mapper.StorehouseConfigMapper;
import com.magic.card.wms.baseset.model.dto.StorehouseConfigDTO;
import com.magic.card.wms.baseset.model.po.StorehouseConfig;
import com.magic.card.wms.baseset.model.po.StorehouseInfo;
import com.magic.card.wms.baseset.service.IStorehouseConfigService;
import com.magic.card.wms.baseset.service.IStorehouseInfoService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.utils.PoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private IStorehouseInfoService storehouseInfoService;
    /**
     * 查询仓库配置信息
     *
     * @param loadGrid
     * @return
     */
    @Override
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        // TODO 仓库信息配置查询 待确定

        return loadGrid;
    }

    /**
     * 添加库位配置
     *
     * @param storehouseConfigDTO
     * @param operator
     * @return
     */
    @Override @Transactional
    public void add(StorehouseConfigDTO storehouseConfigDTO, String operator) {
        checkStorehouseConfig(storehouseConfigDTO, false);
        StorehouseConfig config = new StorehouseConfig();
        PoUtil.add(storehouseConfigDTO, config, operator);

        if (this.baseMapper.insert(config) < 1) {
            throw OperationException.DATA_OPERATION_ADD;
        }

    }

    /**
     * 修改库位配置
     *
     * @param storehouseConfigDTO
     * @param operator
     * @return
     */
    @Override @Transactional
    public void update(StorehouseConfigDTO storehouseConfigDTO, String operator) {
        checkStorehouseConfig(storehouseConfigDTO, true);
        StorehouseConfig config = new StorehouseConfig();
        PoUtil.update(storehouseConfigDTO, config, operator);

        if (this.baseMapper.updateById(config) < 1) {
            throw OperationException.DATA_OPERATION_UPDATE;
        }

    }

    /**
     * 删除数据
     *
     * @param id
     */
    @Override @Transactional
    public void delete(Long id) {

        if (this.baseMapper.deleteById(id) < 1) {
            throw OperationException.DATA_OPERATION_DELETE;
        }

    }

    /**
     * 库位配置添加修改数据检查
     * @param storehouseConfigDTO
     * @param updateOperation
     */
    private void checkStorehouseConfig(StorehouseConfigDTO storehouseConfigDTO, Boolean updateOperation) {

        if (updateOperation) {
            PoUtil.checkId(storehouseConfigDTO.getId());
        }

        // 检查当前库位是否已经使用
        EntityWrapper<StorehouseConfig> wrapper = new EntityWrapper<>();
        wrapper.eq("state", 1)
                .eq("storehouse_id", storehouseConfigDTO.getStorehouseId());

        if (updateOperation) {
            wrapper.ne("id", storehouseConfigDTO.getId());
        }

        if (this.selectCount(wrapper) > 0) {
            throw OperationException.customException(ResultEnum.data_check_exist, "库区已经被配置");
        }

        if (StringUtils.isBlank(storehouseConfigDTO.getCommodityId())) return;

        // TODO 判断当前库位是否为拣货区且已近存放该商品
        StorehouseInfo storehouseInfo = this.storehouseInfoService.selectById(storehouseConfigDTO.getStorehouseId());

        if (storehouseInfo != null && StringUtils.equalsAnyIgnoreCase(Constants.PICKING_AREA_CODE, storehouseInfo.getHouseCode())) {
            wrapper.eq("commodity_id", storehouseConfigDTO.getCommodityId());

            if (this.selectCount(wrapper) > 0) {
                throw OperationException.customException(ResultEnum.data_check_exist, "该商品在拣货区已经有库位了！");
            }

        }


    }
}
