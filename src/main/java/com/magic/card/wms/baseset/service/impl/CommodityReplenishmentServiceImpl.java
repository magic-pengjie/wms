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
     * 增加库存不足补货信息
     * @param storeCode 零拣库位编号
     * @param stockoutNums 商品缺少量
     */
    @Override
    public void addReplenishmentNotice(String storeCode, Integer stockoutNums) {
        // 通过 storeCode 获取商品零拣区的基本配置信息
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("wsi.store_code", storeCode);
        List<Map> storehousesConfig = storehouseConfigMapper.storehouseConfig(wrapper);
        // 获取基本信息
        if (CollectionUtils.isNotEmpty(storehousesConfig) && storehousesConfig.size() == 1) {
            Map storehouseConfig = storehousesConfig.get(0);
            String barCode = storehouseConfig.get("barCode").toString();
            String customerCode = storehouseConfig.get("customerCode").toString();
            // 商品
            List<Map> ccStorehousesConfig = storehouseConfigService.replenishmentDataList(customerCode, barCode);
            for (Map ccStorehouseConfig :
                    ccStorehousesConfig) {
                // TODO 补货提示具体库位
                Integer availableNums = (Integer) ccStorehouseConfig.get("availableNums");

            }
        }
    }

    /**
     * 拣货区补货数据录入
     * @param storeCode 库位编号
     * @param commodityCode  商品code
     * @param replenishmentNums 补货数量
     */
    public void processReplenishment(String storeCode, String commodityCode, Integer replenishmentNums) {

    }
}
