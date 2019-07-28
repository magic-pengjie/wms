package com.magic.card.wms.baseset.service;

import com.magic.card.wms.baseset.model.dto.BatchBindCommodityDTO;
import com.magic.card.wms.baseset.model.dto.CustomerBaseInfoDTO;
import com.magic.card.wms.baseset.model.po.CustomerBaseInfo;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.common.model.LoadGrid;

import java.util.List;

/**
 * <p>
 * 客户信息表 服务类
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-18
 */
public interface ICustomerBaseInfoService extends IService<CustomerBaseInfo> {

    /**
     * 加载用户档案列表
     * @param page
     * @param searchInfo
     * @return
     */
    LoadGrid loadGrid(LoadGrid loadGrid);

    /**
     * 添加客户档案
     * @param customerBaseInfoDTO 客户基本信息
     * @param operator 操作人员
     */
    void add(CustomerBaseInfoDTO customerBaseInfoDTO, String operator);

    /**
     * 更新用户档案
     * @param customerBaseInfoDTO
     * @param operator 操作人员
     */
    void update(CustomerBaseInfoDTO customerBaseInfoDTO, String operator);

    /**
     * 更新删除（非物理删除）用户档案
     * @param id
     * @param operator
     * @param pyd
     */
    void delete(Long id, String operator, Boolean pyd);

    /**
     * 加载商家产品列表（可分页搜索查询）
     * @param currentPage
     * @param pageSize
     * @param customerId
     * @param searchInfo
     * @return
     */
    LoadGrid loadCustomerCommodities(Integer currentPage, Integer pageSize, String customerId, String searchInfo);

    /**
     * 加载商家产品列表（可分页搜索查询）
     * @param loadGrid 分页信息
     * @param customerId 商家ID
     * @return LoadGrid
     */
    LoadGrid loadCustomerCommodities(LoadGrid loadGrid, String customerId);

    /**
     * 检出客户是否存在，存在则返回客户信息
     * @param customerCode 客户编码
     * @return CustomerBaseInfo
     */
    CustomerBaseInfo checkoutCustomer(String customerCode);

    /**
     * 获取商家未关联的商品信息
     * @param customerCode 商家CODE
     * @param loadGrid 分页信息
     * @return LoadGrid
     */
    LoadGrid comboGridNotBindCommodities(String customerCode, LoadGrid loadGrid);

    /**
     * 批量绑定商品
     * @param batchBindCommodity
     */
    void batchBindCommodity(BatchBindCommodityDTO batchBindCommodity);

    /**
     * 批量解绑
     * @param ids
     */
    void batchUnbindCommodity(List<String> ids);

    /**
     * 检出客户ID是否存在
     * @param customerId
     */
    CustomerBaseInfo checkoutCustomerById(String customerId);
}
