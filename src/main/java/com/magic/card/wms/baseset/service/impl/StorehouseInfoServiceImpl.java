package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.mapper.StorehouseInfoMapper;
import com.magic.card.wms.baseset.model.dto.StorehouseInfoDTO;
import com.magic.card.wms.baseset.model.po.StorehouseInfo;
import com.magic.card.wms.baseset.service.IStorehouseInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.utils.PoUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 仓库表 服务实现类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-19
 */
@Service
public class StorehouseInfoServiceImpl extends ServiceImpl<StorehouseInfoMapper, StorehouseInfo> implements IStorehouseInfoService {

    /**
     * 加载库位信息列表
     *
     * @param loadGrid
     * @return
     */
    @Override
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        Page page = loadGrid.page();
        EntityWrapper wrapper = new EntityWrapper<>();
        wrapper.eq("wsi.state", Constants.ACTIVITY_STATE);

        if (MapUtils.isNotEmpty(loadGrid.getSearch())) {

        }

        if (MapUtils.isNotEmpty(loadGrid.getOrder())) {

        } else {
            wrapper.orderBy("wdi.dict_name").orderBy("wsi.area_code").orderBy("wsi.store_code");
        }

        loadGrid.finallyResult(page, this.baseMapper.loadGrid(page, wrapper));
        return loadGrid;
    }

    @Override @Transactional
    public void add(StorehouseInfoDTO storehouseInfoDTO, String operator) {
        checkStorehouse(storehouseInfoDTO, false);
        StorehouseInfo storehouseInfo = new StorehouseInfo();
        PoUtil.add(storehouseInfoDTO, storehouseInfo, operator);

        if (this.baseMapper.insert(storehouseInfo) < 1)
            throw OperationException.DATA_OPERATION_ADD;

    }

    @Override @Transactional
    public void update(StorehouseInfoDTO storehouseInfoDTO, String operator) {
        checkStorehouse(storehouseInfoDTO, true);
        StorehouseInfo storehouseInfo = new StorehouseInfo();
        PoUtil.update(storehouseInfoDTO, storehouseInfo, operator);

        if (this.baseMapper.updateById(storehouseInfo) < 1)
            throw OperationException.DATA_OPERATION_UPDATE;

    }

    /**
     * 数据删除
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
     * 仓储信息添加修改数据检查
     * @param storehouseInfoDTO
     * @param updateOperation
     * @throws BusinessException
     */
    private void checkStorehouse(StorehouseInfoDTO storehouseInfoDTO, Boolean updateOperation) {

        if (updateOperation) {
            PoUtil.checkId(storehouseInfoDTO.getId());
        }


        storehouseInfoDTO.getHouseCode(); // 拣货区/存储区...
        storehouseInfoDTO.getAreaCode(); // A/B/C 区域编号
        storehouseInfoDTO.getStoreCode(); // PA-12-CK10001 库位编号

        // 判断功能区 区域编号是否被占用
        EntityWrapper<StorehouseInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("area_code", storehouseInfoDTO.getAreaCode());
        wrapper.ne("house_code", storehouseInfoDTO.getHouseCode());
        // 确保数据是可用状态
        wrapper.eq("state", 1);

        if (this.selectCount(wrapper) > 0) {
            throw OperationException.customException(ResultEnum.store_house_error, "区域编号已被其他功能区占用");
        }

        // 判断库位编号是否
        wrapper = new EntityWrapper<>();
        wrapper.eq("store_code", storehouseInfoDTO.getStoreCode());

        if (updateOperation) {
            wrapper.ne("id", storehouseInfoDTO.getId());
        }

        // 确保数据是可用状态
        wrapper.eq("state", 1);

        if (this.selectCount(wrapper) > 0) {
            throw OperationException.customException(ResultEnum.store_house_error, "库位编号已被占用");
         }

    }
}
