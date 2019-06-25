package com.magic.card.wms.baseset.service;

import com.magic.card.wms.baseset.model.dto.BrandInfoDTO;
import com.magic.card.wms.baseset.model.po.Brand;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.common.model.LoadGrid;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-18
 */
public interface IBrandInfoService extends IService<Brand> {

    /**
     * 加载品牌数据列表
     * @param loadGrid
     * @return
     */
    LoadGrid loadGrid(LoadGrid loadGrid);

    /**
     * 添加品牌数据
     * @param brandInfoDTO
     * @param operator 操作人
     * @return
     */
    void addBrandInfo(BrandInfoDTO brandInfoDTO, String operator);

    /**
     * 更新品牌数据
     * @param brandInfoDTO
     * @param operator 操作人
     * @return
     */
    void updateBrandInfo(BrandInfoDTO brandInfoDTO, String operator);

    /**
     * 更新删除品牌数据
     * @param id 数据ID
     * @param operator 操作人
     * @param pyd 是否物理删除
     * @return
     */
    void deleteBrandInfo(Long id, String operator, Boolean pyd);
}
