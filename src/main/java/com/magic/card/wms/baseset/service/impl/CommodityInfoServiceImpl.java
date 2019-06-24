package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.model.dto.CommodityInfoDTO;
import com.magic.card.wms.baseset.model.po.CommodityInfo;
import com.magic.card.wms.baseset.mapper.CommodityInfoMapper;
import com.magic.card.wms.baseset.service.ICommodityInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.po.PoUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Override @Transactional
    public void add(CommodityInfoDTO commodityInfoDTO, String operator) {
        checkCommodityInfo(commodityInfoDTO, false);
        CommodityInfo commodityInfo = new CommodityInfo();
        PoUtils.add(commodityInfoDTO, commodityInfo, operator);

        if (this.baseMapper.insert(commodityInfo) < 1)
            throw OperationException.DATA_OPERATION_ADD;

    }

    /**
     * 修改商品关联信息
     *
     * @param commodityInfoDTO
     * @param operator
     * @return
     */
    @Override @Transactional
    public void update(CommodityInfoDTO commodityInfoDTO, String operator) {
        checkCommodityInfo(commodityInfoDTO, true);
        CommodityInfo commodityInfo = new CommodityInfo();
        PoUtils.update(commodityInfoDTO, commodityInfo, operator);

        if (this.baseMapper.updateById(commodityInfo) < 1)
            throw OperationException.DATA_OPERATION_UPDATE;

    }

    /**
     * 删除数据
     *
     * @param id
     */
    @Override @Transactional
    public void delete(Long id) {

        if (this.baseMapper.deleteById(id) < 1) throw OperationException.DATA_OPERATION_DELETE;

    }

    private void checkCommodityInfo(CommodityInfoDTO commodityInfoDTO, Boolean updateOperator) {

        if (updateOperator && (commodityInfoDTO.getId() == null || commodityInfoDTO.getId() == 0l))
            throw OperationException.DATA_ID;

        EntityWrapper<CommodityInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("state", 1)
                .eq("customer_id", commodityInfoDTO.getCustomerId())
                .eq("commodity_code", commodityInfoDTO.getCommodityCode());

        if (updateOperator) wrapper.ne("id", commodityInfoDTO.getId());

        if (this.selectCount(wrapper) > 0)
            throw new BusinessException(ResultEnum.data_check_exist.getCode(), "当前商品已关联客户");
    }
}
