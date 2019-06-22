package com.magic.card.wms.baseset.mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.po.DictInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表（维护商品类型、商品属性等） Mapper 接口
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-18
 */
public interface DictInfoMapper extends BaseMapper<DictInfo> {

    List<Map> loadGrid(Page<Object> page, @Param("ew") EntityWrapper<Object> objectEntityWrapper);
}
