package com.magic.card.wms.baseset.mapper;

import com.magic.card.wms.baseset.model.po.Brand;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * <p>
 * 品牌表 Mapper 接口
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-18
 */
public interface BrandInfoMapper extends BaseMapper<Brand> {

    /**
     * 更新删除操作
     * @param id
     * @param operator 操作员
     * @param operationTime 操作时间
     * @return
     */
    @Update("Update wms_brand_info Set state  = 0, update_user = #{operator}, update_time = #{operationTime} Where id = #{id}")
    int updateDelete(@Param("id") Long id, @Param("operator") String operator, @Param("operationTime") Date operationTime);


}
