package com.magic.card.wms.baseset.service;

import com.magic.card.wms.baseset.model.dto.StorehouseInfoDTO;
import com.magic.card.wms.baseset.model.po.StorehouseInfo;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.LoadGrid;

/**
 * <p>
 * 仓库表 服务类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-19
 */
public interface IStorehouseInfoService extends IService<StorehouseInfo> {

    /**
     * 加载库位信息列表
     * @return
     */
    LoadGrid loadGrid(LoadGrid loadGrid);

    /**
     * 添加仓库库位信息
     * @param storehouseInfoDTO
     * @param operator
     * @return
     */
    void add(StorehouseInfoDTO storehouseInfoDTO, String operator);

    /**
     * 修改仓库库位信息
     * @param storehouseInfoDTO
     * @param operator
     * @return
     */
    void update(StorehouseInfoDTO storehouseInfoDTO, String operator);

    /**
     * 数据删除
     * @param id
     */
    void delete(Long id);
}
