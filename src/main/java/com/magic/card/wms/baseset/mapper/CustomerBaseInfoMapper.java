package com.magic.card.wms.baseset.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.magic.card.wms.baseset.model.po.CustomerBaseInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户信息表 Mapper 接口
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-18
 */
public interface CustomerBaseInfoMapper extends BaseMapper<CustomerBaseInfo> {

    /**
     * 更新删除操作
     * @param id
     * @param operator 操作员
     * @param operationTime 操作时间
     * @return
     */
    @Update("Update wms_customer_base_info Set state  = 0, update_user = #{operator}, update_time = #{operationTime} Where id = #{id}")
    int updateDelete(@Param("id") Long id, @Param("operator") String operator, @Param("operationTime") Date operationTime);

    /**
     *
     * @param page
     * @param wrapper
     * @return
     */
    List<Map> loadGrid(Pagination page, @Param("ew") Wrapper wrapper);

    /**
     *
     * @param page
     * @param wrapper
     * @return
     */
    List<Map> loadCustomerCommodities(Page<CustomerBaseInfo> page, @Param("ew") Wrapper wrapper);
}
