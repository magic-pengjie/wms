package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.baseset.mapper.CommodityReplenishmentMapper;
import com.magic.card.wms.baseset.mapper.StorehouseConfigMapper;
import com.magic.card.wms.baseset.model.po.CommodityReplenishment;
import com.magic.card.wms.baseset.service.ICommodityReplenishmentService;
import com.magic.card.wms.baseset.service.IStorehouseConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.service.impl
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/12 15:48
 * @since : 1.0.0
 */
@Slf4j
@Service
public class CommodityReplenishmentServiceImpl extends ServiceImpl<CommodityReplenishmentMapper, CommodityReplenishment> implements ICommodityReplenishmentService {
    @Resource
    private StorehouseConfigMapper storehouseConfigMapper;
    @Autowired
    private IStorehouseConfigService storehouseConfigService;


    /**
     * 处理相关商品零拣区库存不足
     */
    @Override
    public void handleReplenishment(String storeCode, Integer stockoutNums) {
        // 通过 storeCode 获取商品零拣区的基本配置信息
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("wsi.store_code", storeCode);
        List<Map> storehouseConfig = storehouseConfigMapper.storehouseConfig(wrapper);
        // TODO 处理相关商品零拣区库存不足
//        Integer availableNums

        // 获取基本信息
        if (CollectionUtils.isNotEmpty(storehouseConfig) && storehouseConfig.size() == 1) {
//            storehouseConfigService.
        }
    }
}
