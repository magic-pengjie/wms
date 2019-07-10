package com.magic.card.wms.baseset.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.po.PickingBill;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.mapper
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/24/024 19:04
 * @since : 1.0.0
 */
public interface PickingBillMapper extends BaseMapper<PickingBill> {

    /**
     * 加载拣货单基本数据
     * @param page
     * @param entityWrapper
     * @return
     */
    List<Map> loadGrid(Page page, @Param("ew") EntityWrapper entityWrapper);

    /**
     * 加载订单 客户code
     * @param wrapper
     * @return
     */
    List<String> customerCodes(@Param("ew") EntityWrapper wrapper);
}
