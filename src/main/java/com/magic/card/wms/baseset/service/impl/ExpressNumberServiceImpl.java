package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.mapper.ExpressNumberMapper;
import com.magic.card.wms.baseset.model.po.ExpressNumber;
import com.magic.card.wms.baseset.service.IExpressNumberService;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.ExpressKeyEnum;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.model.enums.StateEnum;
import com.magic.card.wms.common.utils.*;
import lombok.Synchronized;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.service.impl
 * 快递单号管理 Service Implement
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/9/4 10:02
 * @since : 1.0.0
 */
@Service
public class ExpressNumberServiceImpl extends ServiceImpl<ExpressNumberMapper, ExpressNumber> implements IExpressNumberService {
    private final Map<String, String> DEFAULT_COLUMNS = Maps.newConcurrentMap();
    @Autowired
    private WebUtil webUtil;

    {
        DEFAULT_COLUMNS.put("id", "id");
        DEFAULT_COLUMNS.put("expressKey", "express_key");
        DEFAULT_COLUMNS.put("expressNum", "express_num");
        DEFAULT_COLUMNS.put("useTimes", "use_times");
    }

    /**
     * 快递单号分页数据
     *
     * @param loadGrid 查询排序条件
     */
    @Override
    public void loadGrid(LoadGrid loadGrid) {
        Page page = loadGrid.generatorPage();
        EntityWrapper wrapper = new EntityWrapper();
        WrapperUtil.autoSettingSearch(wrapper, DEFAULT_COLUMNS, loadGrid.getSearch());
        WrapperUtil.autoSettingOrder(wrapper, DEFAULT_COLUMNS, loadGrid.getOrder(), defaultSetOrder -> {
            defaultSetOrder.orderBy("use_times, express_num");
        });

        loadGrid.finallyResult(page, baseMapper.loadGrid(page, wrapper));
    }

    /**
     * 获取快递单号
     *
     * @param expressKey 快递标识
     * @return
     */
    @Override @Transactional @Synchronized
    public String getExpressNumber(String expressKey) {
        final String mailNo =  baseMapper.getExpressNumber(expressKey, 0);

        if (StringUtils.isBlank(mailNo)) {
            throw OperationException.customException(ResultEnum.express_number_exhaust);
        }

        // 标记完成
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("express_key", expressKey).
                eq("express_num", mailNo).
                eq("state", StateEnum.normal.getCode());
        baseMapper.updateForSet(String.format("use_times = use_times + 1, update_time = '%s'", DateUtil.getStringDate()), wrapper);
        return mailNo;
    }

    /**
     * 批量添加快递单号
     *
     * @param expressKey  快递标识
     * @param startNumber 开始单号
     * @param endNumber   结束单号
     */
    @Override @Transactional
    public void batchAddExpressNumber(String expressKey, Long startNumber, Long endNumber) {

        if (startNumber.toString().length() != 13 || endNumber.toString().length() != 13) {
            throw OperationException.customException(ResultEnum.express_number_size_err);
        }

        if (startNumber > endNumber) {
            Long maxNumber = startNumber;
            startNumber = endNumber;
            endNumber = maxNumber;
        }

        // 判断快递单号是否有重复
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("express_key", expressKey).ge("express_num",startNumber).le("express_num", endNumber);

        if (selectCount(wrapper) > 0) {
           throw OperationException.customException(ResultEnum.express_number_exist);
        }

        ArrayList<ExpressNumber> expressNumbers = Lists.newArrayList();

        for (long expressNum = startNumber; expressNum <= endNumber; expressNum++) {
            expressNumbers.add(new ExpressNumber(expressKey, "" + expressNum, 0));

            if ((expressNum - startNumber) > 0 && (expressNum - startNumber) % 10000 == 0) {
                PoUtil.batchAdd(webUtil.operator(), expressNumbers);
                final ArrayList<ExpressNumber> batchExpressNumbers = expressNumbers;
                WmsThreadPool.executor(() -> insertBatch(batchExpressNumbers, 1000));
                expressNumbers = Lists.newArrayList();
            }
        }

        if (CollectionUtils.isNotEmpty(expressNumbers)) {
            PoUtil.batchAdd(webUtil.operator(), expressNumbers);
            insertBatch(expressNumbers);
        }

    }

    /**
     * 批量修改快递单使用次数
     *
     * @param expressKey     快递标识
     * @param expressNumbers 多个快递单号
     */
    @Override @Transactional
    public void batchEditUseTimes(String expressKey, List expressNumbers) {

    }

    /**
     * 快递单号可用量
     *
     * @return
     */
    @Override
    public int expressNumberAvailable() {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("express_key", ExpressKeyEnum.YZPK.getCode()).
                eq("use_times", 0).
                eq("state", StateEnum.normal.getCode());
        return selectCount(wrapper);
    }
}
