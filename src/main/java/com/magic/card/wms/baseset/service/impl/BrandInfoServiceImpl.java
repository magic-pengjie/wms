package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.model.dto.BrandInfoDTO;
import com.magic.card.wms.baseset.model.po.Brand;
import com.magic.card.wms.baseset.mapper.BrandInfoMapper;
import com.magic.card.wms.baseset.service.IBrandInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.po.PoUtils;
import com.magic.card.wms.common.utils.WrapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-18
 */
@Slf4j
@Service
public class BrandInfoServiceImpl extends ServiceImpl<BrandInfoMapper, Brand> implements IBrandInfoService {
    /**
     * 默认提供的Columns
     */
    private static Map<String, String> defaultColumns = Maps.newConcurrentMap();
    static {
        defaultColumns.put("id", "id");
        defaultColumns.put("name", "name");
    }


    @Override
    @Transactional
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        Page page = loadGrid.page();
        EntityWrapper<Brand> brandInfo = new EntityWrapper<>();
        // 获取状态正常数据
        brandInfo.eq("state", 1);
        WrapperUtil.searchSet(brandInfo, defaultColumns, loadGrid.getSearch());

        if (MapUtils.isNotEmpty(loadGrid.getOrder())) {
            WrapperUtil.orderSet(brandInfo, defaultColumns, loadGrid.getOrder());
        } else {
            brandInfo.orderBy("id");
        }

        List<Brand> brands = this.baseMapper.selectPage(page, brandInfo);

        return LoadGrid.instance(page, brands);
    }

    @Override
    @Transactional
    public void addBrandInfo(BrandInfoDTO brandInfoDTO, String operator) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandInfoDTO, brand);
        PoUtils.add(brand, operator);

        if (this.baseMapper.insert(brand) < 1)
            throw OperationException.DATA_OPERATION_ADD;

    }

    @Override
    @Transactional
    public void updateBrandInfo(BrandInfoDTO brandInfoDTO, String operator) {

        if (brandInfoDTO.getId() == null || brandInfoDTO.getId() == 0l)
            throw OperationException.DATA_ID;

        Brand brand = new Brand();
        BeanUtils.copyProperties(brandInfoDTO, brand);
        PoUtils.update(brand, operator);

        if (this.baseMapper.updateById(brand) <1 )
            throw OperationException.DATA_OPERATION_UPDATE;

    }

    @Override
    @Transactional
    public void deleteBrandInfo(Long id, String operator, Boolean pyd) {

        if ((pyd && this.baseMapper.deleteById(id) < 1)
                || baseMapper.updateDelete(id, operator, new Date()) < 1)
            throw OperationException.DATA_OPERATION_DELETE;

    }
}
