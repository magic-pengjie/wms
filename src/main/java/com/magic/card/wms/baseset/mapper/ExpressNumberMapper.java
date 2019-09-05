package com.magic.card.wms.baseset.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.po.ExpressNumber;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.mapper
 * 快递单号管理Mapper
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/9/4 9:49
 * @since : 1.0.0
 */
public interface ExpressNumberMapper extends BaseMapper<ExpressNumber> {
    /**
     * 获取快递单号
     * @param expressKey 快递标识
     * @param useTimes 使用次数
     * @return
     */
    String getExpressNumber(@Param("expressKey") String expressKey, @Param("useTimes") int useTimes);

    /**
     * 分页查询数据加载
     * @param page 分页数据
     * @param wrapper 查询数据
     * @return
     */
    List<Map> loadGrid(Page page, @Param("ew") EntityWrapper wrapper);
}
