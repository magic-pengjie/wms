package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.model.dto.CommodityInfoDTO;
import com.magic.card.wms.baseset.model.po.CommodityInfo;
import com.magic.card.wms.baseset.mapper.CommodityInfoMapper;
import com.magic.card.wms.baseset.service.ICommodityInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.po.PoUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-19
 */
@Service
public class CommodityInfoServiceImpl extends ServiceImpl<CommodityInfoMapper, CommodityInfo> implements ICommodityInfoService {

    /**
     *
     */
    private static Map<String, String> defaultColumns = Maps.newConcurrentMap();

    static {

    }

    /**
     * 添加商品关联信息
     *
     * @param commodityInfoDTO
     * @param operator
     * @return
     */
    @Override
    public Boolean addCommodityInfo(CommodityInfoDTO commodityInfoDTO, String operator) {
        checkCommodityInfo(commodityInfoDTO, false);
        CommodityInfo commodityInfo = new CommodityInfo();
        PoUtils.add(commodityInfoDTO, commodityInfo, operator);
        return this.insert(commodityInfo);
    }

    /**
     * 修改商品关联信息
     *
     * @param commodityInfoDTO
     * @param operator
     * @return
     */
    @Override
    public Boolean updateCommodityInfo(CommodityInfoDTO commodityInfoDTO, String operator) {
        checkCommodityInfo(commodityInfoDTO, true);
        CommodityInfo commodityInfo = new CommodityInfo();
        PoUtils.update(commodityInfoDTO, commodityInfo, operator);
        return this.updateById(commodityInfo);
    }

    private void checkCommodityInfo(CommodityInfoDTO commodityInfoDTO, Boolean updateOperator) {

        if (updateOperator && (commodityInfoDTO.getId() == null || commodityInfoDTO.getId() == 0l))
            throw new BusinessException(ResultEnum.req_params_error.getCode(), "修改商品关联信息没有ID");

        EntityWrapper<CommodityInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("state", 1)
                .eq("customer_id", commodityInfoDTO.getCustomerId())
                .eq("commodity_code", commodityInfoDTO.getCommodityCode());

        if (updateOperator) wrapper.ne("id", commodityInfoDTO.getId());

        if (this.selectCount(wrapper) > 0)
            throw new BusinessException(ResultEnum.data_check_exist.getCode(), "当前商品已关联客户");
    }
}
