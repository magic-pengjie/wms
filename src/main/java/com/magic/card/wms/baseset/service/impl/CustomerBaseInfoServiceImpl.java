package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.dto.CustomerBaseInfoDTO;
import com.magic.card.wms.baseset.model.po.CustomerBaseInfo;
import com.magic.card.wms.baseset.mapper.CustomerBaseInfoMapper;
import com.magic.card.wms.baseset.service.ICustomerBaseInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.po.PoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 客户信息表 服务实现类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-18
 */
@Slf4j
@Service
public class CustomerBaseInfoServiceImpl extends ServiceImpl<CustomerBaseInfoMapper, CustomerBaseInfo> implements ICustomerBaseInfoService {

    @Override
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        Page page = loadGrid.page();
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("cbf.state", 1);

        if (MapUtils.isNotEmpty(loadGrid.getSearch())) {
            String address = MapUtils.getString(loadGrid.getSearch(), "address");


            // TODO 设置客户自定义栏位搜索
            if (StringUtils.isNotBlank(address)){
                wrapper.like("cbf.address", address);
            }

        }

        if (MapUtils.isNotEmpty(loadGrid.getOrder())) {
            // TODO 设置客户自定义排序
        } else {
            wrapper.orderBy("cbf.update_time", false);
        }

        loadGrid.finallyResult(page, baseMapper.loadGrid(page, wrapper));
        return loadGrid;
    }

    @Override @Transactional
    public void add(CustomerBaseInfoDTO customerBaseInfoDTO, String operator) {
        CustomerBaseInfo customerBaseInfo = new CustomerBaseInfo();
        BeanUtils.copyProperties(customerBaseInfoDTO, customerBaseInfo);
        PoUtils.add(customerBaseInfo, operator);

        if (this.baseMapper.insert(customerBaseInfo) < 1)
            throw OperationException.DATA_OPERATION_ADD;

    }

    @Override @Transactional
    public void update(CustomerBaseInfoDTO customerBaseInfoDTO, String operator) {
//        Wrapper<CustomerBaseInfo> wrapper = new EntityWrapper<>();
//        wrapper.eq("id", customerBaseInfoDTO.getId());
//        CustomerBaseInfo customerBaseInfo = this.selectOne(wrapper);
//
//        if (customerBaseInfo != null) {
        CustomerBaseInfo customerBaseInfo = new CustomerBaseInfo();
        BeanUtils.copyProperties(customerBaseInfoDTO, customerBaseInfo);
        PoUtils.update(customerBaseInfo, operator);

        if (this.baseMapper.updateById(customerBaseInfo) < 1)
            throw OperationException.DATA_OPERATION_UPDATE;
//        }
    }


    /**
     * 加载商家产品列表（可分页搜索查询）
     *
     * @param loadGrid
     * @param customerId
     * @return
     */
    @Override
    public LoadGrid loadCustomerCommodities(LoadGrid loadGrid, String customerId) {
        Page page = loadGrid.page();
        Wrapper wrapper = new EntityWrapper<>();
        wrapper.eq("1", 1);

        if (!StringUtils.equalsIgnoreCase("all", customerId))
            wrapper.eq("wcbi.id", customerId);

        if (MapUtils.isNotEmpty(loadGrid.getSearch())) {
            // TODO 设置客户商品查询条件
        }

        if (MapUtils.isNotEmpty(loadGrid.getOrder())) {
            // TODO 设置客户商品排序条件
        } else {
            wrapper.orderBy("wcbi.id");
        }

        loadGrid.finallyResult(page, baseMapper.loadCustomerCommodities(page, wrapper));
        return loadGrid;
    }

    @Override @Transactional
    public void delete(Long id, String operator, Boolean pyd) {

        if ((pyd && this.baseMapper.deleteById(id) < 1) || this.baseMapper.updateDelete(id, operator, new Date() ) < 1)
            throw OperationException.DATA_OPERATION_DELETE;

    }

    /**
     * 加载商家产品列表（可分页搜索查询）
     *
     * @param currentPage
     * @param pageSize
     * @param customerId
     * @param searchInfo
     * @return
     */
    @Override
    public LoadGrid loadCustomerCommodities(Integer currentPage, Integer pageSize, String customerId, String searchInfo) {
        Page page = new Page<>(currentPage, pageSize);
        Wrapper wrapper = new EntityWrapper<>();
        wrapper.eq("cbf.state", 1);
        wrapper.orderBy("cbf.id", false);
        wrapper.orderBy("cbf.address", true);

        return LoadGrid.instance(page, baseMapper.loadCustomerCommodities(page, wrapper));
    }

}
