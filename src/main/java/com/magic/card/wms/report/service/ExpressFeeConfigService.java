package com.magic.card.wms.report.service;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.model.po.Order;
import com.magic.card.wms.baseset.service.IOrderService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.utils.CommodityUtil;
import com.magic.card.wms.common.utils.PoUtil;
import com.magic.card.wms.common.utils.WrapperUtil;
import com.magic.card.wms.report.mapper.ExpressFeeConfigMapper;
import com.magic.card.wms.report.model.dto.ExpressFeeConfigDTO;
import com.magic.card.wms.report.model.po.ExpressFeeConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.report.service
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/11 17:15
 * @since : 1.0.0
 */
@Service
public class ExpressFeeConfigService extends ServiceImpl<ExpressFeeConfigMapper, ExpressFeeConfig> {
    private Map<String, String> defaultColumns = Maps.newConcurrentMap();
    @Autowired
    private IOrderService orderService;
    {
        defaultColumns.put("id", "wefc.id");
        defaultColumns.put("customerCode", "wefc.customer_code");
        defaultColumns.put("customerName", "wcbi.customer_name");
        defaultColumns.put("areaCode", "wefc.area_code");
        defaultColumns.put("areaName", "wdi.dict_name");
        defaultColumns.put("rangeId", "wefc.range_id");
        defaultColumns.put("rangeLeftValue", "wewr.left_value");
        defaultColumns.put("rangeRightValue", "wewr.right_value");
        defaultColumns.put("feeValue", "wefc.fee_value");
    }



    /**
     * 加载快递费配置信息列表
     * @param loadGrid
     * @return
     */
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        Page page = loadGrid.generatorPage();
        EntityWrapper wrapper = new EntityWrapper();
        WrapperUtil.searchSet(wrapper, defaultColumns, loadGrid.getSearch());
        WrapperUtil.orderSet(wrapper, defaultColumns, loadGrid.getOrder());
        loadGrid.finallyResult(page, baseMapper.loadGrid(page, wrapper));
        return loadGrid;
    }
    /**
     * 添加配置信息
     * @param expressFeeConfigDTO
     * @param operator
     */
    @Transactional
    public void add(ExpressFeeConfigDTO expressFeeConfigDTO, String operator) {
        checkExpressFeeConfig(expressFeeConfigDTO, false);

        for (String areaCode : expressFeeConfigDTO.getAreaCodes()) {
            ExpressFeeConfig expressFeeConfig = new ExpressFeeConfig();
            PoUtil.add(expressFeeConfigDTO, expressFeeConfig, operator);
            expressFeeConfig.setAreaCode(areaCode);
            insert(expressFeeConfig);
        }

    }

    /**
     * 更新
     * @param expressFeeConfigDTO
     * @param operator
     */
    @Transactional
    public void update(ExpressFeeConfigDTO expressFeeConfigDTO, String operator) {
        checkExpressFeeConfig(expressFeeConfigDTO, true);

        ExpressFeeConfig expressFeeConfig = new ExpressFeeConfig();

        if (expressFeeConfigDTO.getAreaCodes().size() == 1) {
            expressFeeConfig.setAreaCode(expressFeeConfigDTO.getAreaCodes().get(0));
            PoUtil.update(expressFeeConfigDTO, expressFeeConfig, operator);
            updateById(expressFeeConfig);
        }

    }

    /**
     * 核算订单运费
     * @param orderNo 订单号
     * @param realWeight 称重重量
     * @param weightUnit 称重单位
     * @return
     */
    public BigDecimal orderExpressFree(String orderNo, BigDecimal realWeight, String weightUnit) {
        Order order = orderService.checkOrder(orderNo);
        BigDecimal realWeigh_g = CommodityUtil.unitConversion_G(realWeight, weightUnit);
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("state", StateEnum.normal.getCode())
                .ge("wewr.right_value", realWeigh_g)
                .le("wewr.left_value", realWeigh_g)
                .like("wdi.dict_name", order.getProv(), SqlLike.RIGHT)
                .eq("wefc.customer_code", order.getCustomerCode());
        List<Map> fees = baseMapper.loadGrid(null, wrapper);

        if (CollectionUtils.isNotEmpty(fees) && fees.size() == 1) {
            BigDecimal feeValue = (BigDecimal) fees.get(0).get("feeValue");
            return realWeigh_g.multiply(feeValue);
        }

        return null;
    }

    /**
     * 检查是否已经存在配置信息
     * @param expressFeeConfigDTO
     * @param updateOperation
     * @return
     */
    private void checkExpressFeeConfig(ExpressFeeConfigDTO expressFeeConfigDTO, Boolean updateOperation) {
        EntityWrapper wrapper = new EntityWrapper();

        if (updateOperation) {
            PoUtil.checkId(expressFeeConfigDTO.getId());
            wrapper.ne("id", expressFeeConfigDTO.getId());
        }

        wrapper.eq("state", StateEnum.normal.getCode()).
                eq("customer_code", expressFeeConfigDTO.getCustomerCode()).
                in("area_code", expressFeeConfigDTO.getAreaCodes()).
                eq("range_id", expressFeeConfigDTO.getRangeId());

        if (selectCount(wrapper) > 0) throw OperationException.customException(ResultEnum.express_fee_config_exist);
    }
}
