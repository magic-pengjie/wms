package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.dto.DictInfoDTO;
import com.magic.card.wms.baseset.model.po.DictInfo;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.LoadGrid;

import java.util.List;

/**
 * <p>
 * 字典表（维护商品类型、商品属性等） 服务类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-18
 */
public interface IDictInfoService extends IService<DictInfo> {
    /**
     * 加载字典常量
     * @param page
     * @param searchInfo
     * @return
     */
    LoadGrid loadGrid(LoadGrid loadGrid);

    /**
     * 获取子常量
     * @param dictCode
     * @return
     */
    LoadGrid loadChild(String dictCode);

    /**
     * 添加字典常量
     * @param dictInfoDTO
     * @param operator
     * @return
     */
    void add(DictInfoDTO dictInfoDTO, String operator);

    /**
     * 更新字典常量
     * @param dictInfoDTO
     * @param operator
     * @return
     */
    void update(DictInfoDTO dictInfoDTO, String operator);

    /**
     * 删除数据
     * @param id
     */
    void delete(Long id);
}
