package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.model.dto.BrandInfoDTO;
import com.magic.card.wms.baseset.model.po.BrandInfo;
import com.magic.card.wms.baseset.mapper.BrandInfoMapper;
import com.magic.card.wms.baseset.service.IBrandInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.po.PoUtils;
import com.magic.card.wms.common.utils.WrapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class BrandInfoServiceImpl extends ServiceImpl<BrandInfoMapper, BrandInfo> implements IBrandInfoService {
    /**
     * 默认提供的Columns
     */
    private static Map<String, String> defaultColumns = Maps.newConcurrentMap();
    static {
        defaultColumns.put("id", "id");
        defaultColumns.put("name", "name");
    }


    @Override
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        Page page = loadGrid.page();
        EntityWrapper<BrandInfo> brandInfo = new EntityWrapper<>();
        // 获取状态正常数据
        brandInfo.eq("state", 1);
        WrapperUtil.searchSet(brandInfo, defaultColumns, loadGrid.getSearch());

        if (MapUtils.isNotEmpty(loadGrid.getOrder())) {
            WrapperUtil.orderSet(brandInfo, defaultColumns, loadGrid.getOrder());
        } else {
            brandInfo.orderBy("id");
        }

        List<BrandInfo> brandInfos = this.baseMapper.selectPage(page, brandInfo);

        return LoadGrid.instance(page, brandInfos);
    }

    @Override
    public Boolean addBrandInfo(BrandInfoDTO brandInfoDTO, String operator) {
        BrandInfo brandInfo = new BrandInfo();
        BeanUtils.copyProperties(brandInfoDTO, brandInfo);
        PoUtils.add(brandInfo, operator);

        return this.insert(brandInfo);
    }

    @Override
    public Boolean updateBrandInfo(BrandInfoDTO brandInfoDTO, String operator) {

        if (brandInfoDTO.getId() == null || brandInfoDTO.getId() == 0l) return false;

        BrandInfo brandInfo = new BrandInfo();
        BeanUtils.copyProperties(brandInfoDTO, brandInfo);
        PoUtils.update(brandInfo, operator);
        Boolean flag = this.updateById(brandInfo);
        log.info(" 数据更新情况： {}“", flag);
        return flag;
    }

    @Override
    public Boolean deleteBrandInfo(Long id, String operator, Boolean pyd) {

        if (pyd) return this.deleteById(id);

        return baseMapper.updateDelete(id, operator, new Date()) > 1;
    }
}
