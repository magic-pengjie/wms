package com.magic.card.wms.report.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.report.model.po.ExpressFeeConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.report.mapper
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/11 17:13
 * @since : 1.0.0
 */
public interface ExpressFeeConfigMapper extends BaseMapper<ExpressFeeConfig> {

    /**
     * 加载快递费配置数据列表
     * @param page
     * @param entityWrapper
     * @return
     */
    List<Map> loadGrid(Page<Object> page, @Param("ew") EntityWrapper entityWrapper);
}
