package com.magic.card.wms.baseset.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.po.StorehouseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 仓库表 Mapper 接口
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-19
 */
public interface StorehouseInfoMapper extends BaseMapper<StorehouseInfo> {

    /**
     *
     * @param page
     * @param wrapper
     * @return
     */
    List<Map> loadGrid(Page page, @Param("ew") Wrapper wrapper);

    /**
     * 库位的信息
     * @param page
     * @param entityWrapper
     * @return
     */
    List<Map> comboGrid(Page page, @Param("ew") EntityWrapper entityWrapper);

    /**
     * 加载商家未绑定商品的库位数据
     * @param page 分页
     * @param entityWrapper 查询信息
     * @return
     */
    List<Map> comboGridBind(Page page, @Param("ew") EntityWrapper entityWrapper);

    /**
     * 加载所有库区
     * @return
     */
    List<String> queryAreaCodeList();
}
