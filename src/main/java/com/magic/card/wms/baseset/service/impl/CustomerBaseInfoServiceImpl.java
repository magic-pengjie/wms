package com.magic.card.wms.baseset.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.magic.card.wms.baseset.model.dto.CustomerBaseInfoDTO;
import com.magic.card.wms.baseset.model.po.CustomerBaseInfo;
import com.magic.card.wms.baseset.mapper.CustomerBaseInfoMapper;
import com.magic.card.wms.baseset.service.ICustomerBaseInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.LoadGrid;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import com.magic.card.wms.common.utils.PoUtil;
import com.magic.card.wms.common.utils.WrapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

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

    /**
     * 默认提供的Columns
     */
    private static Map<String, String> defaultColumns = Maps.newConcurrentMap();
    static {
        defaultColumns.put("id", "cbf.id");
        defaultColumns.put("customerName", "cbf.customer_name");
        defaultColumns.put("customerCode", "cbf.customer_code");
        defaultColumns.put("address", "cbf.address");
        defaultColumns.put("phone", "cbf.phone");
        defaultColumns.put("contactPerson", "cbf.contact_person");
        defaultColumns.put("brandId", "cbf.brand_id");
        defaultColumns.put("brandName", "name");
        defaultColumns.put("brandName", "bi.`name`");
    }

    @Override
    public LoadGrid loadGrid(LoadGrid loadGrid) {
        Page page = loadGrid.page();
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("cbf.state", 1);
        WrapperUtil.searchSet(wrapper, defaultColumns, loadGrid.getSearch());

        if (MapUtils.isNotEmpty(loadGrid.getOrder())) {
            WrapperUtil.orderSet(wrapper, defaultColumns, loadGrid.getOrder());
        } else {
            wrapper.orderBy("cbf.update_time", false);
        }

        loadGrid.finallyResult(page, baseMapper.loadGrid(page, wrapper));
        return loadGrid;
    }

    @Override @Transactional
    public void add(CustomerBaseInfoDTO customerBaseInfoDTO, String operator) {
        checkCustomer(customerBaseInfoDTO, false);
        CustomerBaseInfo customerBaseInfo = new CustomerBaseInfo();
        BeanUtils.copyProperties(customerBaseInfoDTO, customerBaseInfo);
        PoUtil.add(customerBaseInfo, operator);

        if (this.baseMapper.insert(customerBaseInfo) < 1)
            throw OperationException.DATA_OPERATION_ADD;

    }

    @Override @Transactional
    public void update(CustomerBaseInfoDTO customerBaseInfoDTO, String operator) {
        checkCustomer(customerBaseInfoDTO, true);
        CustomerBaseInfo customerBaseInfo = new CustomerBaseInfo();
        BeanUtils.copyProperties(customerBaseInfoDTO, customerBaseInfo);
        PoUtil.update(customerBaseInfo, operator);

        if (this.baseMapper.updateById(customerBaseInfo) < 1) {
            throw OperationException.DATA_OPERATION_UPDATE;
        }

    }


    /**
     * 检测客户是否存在，存在则返回客户信息
     *
     * @param wrapper
     * @return
     */
    @Override
    public CustomerBaseInfo checkCustomer(String customerCode) {
        if (StringUtils.isNotBlank(customerCode)) {
            EntityWrapper wrapper = new EntityWrapper();
            wrapper.eq("state", Constants.ACTIVITY_STATE);
            wrapper.eq("customer_code", customerCode);


            CustomerBaseInfo customerBaseInfo = this.selectOne(wrapper);

            if (customerBaseInfo == null) {
                throw OperationException.addException("未知商家， 请提供明确的CODE");
            }

            return customerBaseInfo;
        }

        throw OperationException.addException("CustomerCode 不可为空");
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

    /**
     * 检查客户信息
     * @param customerBaseInfoDTO
     * @param updateOperation
     */
    private void checkCustomer(CustomerBaseInfoDTO customerBaseInfoDTO, Boolean updateOperation) {
        EntityWrapper wrapper = new EntityWrapper();

        if (updateOperation) {
            PoUtil.checkId(customerBaseInfoDTO.getId());
            wrapper.ne("id", customerBaseInfoDTO.getId());
        }

        // 检查 Customer Code 是否已经存在数据库
        wrapper.eq("state", Constants.ACTIVITY_STATE)
                .eq("customer_code", customerBaseInfoDTO.getCustomerCode());

        if (this.selectCount(wrapper) > 0) {
            throw OperationException.customException(ResultEnum.data_check_exist, "客户Code已存在");
        }

    }
}
