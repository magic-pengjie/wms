package com.magic.card.wms.baseset.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.magic.card.wms.baseset.model.po.MailPickingDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.mapper
 * 拣货篮商品明细 Mapper
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/17 17:24
 * @since : 1.0.0
 */
public interface MailPickingDetailMapper extends BaseMapper<MailPickingDetail> {

    /**
     * 获取虚拟快递单号
     * @param page
     * @param entityWrapper
     * @return
     */
    List<Map> virtualMails(Page page, @Param("ew") EntityWrapper entityWrapper);

    /**
     * 获取包裹商品对应的耗材清单
     * @param virtualMail
     * @return
     */
    List<Map> mailPickingCommodityInfo(@Param("virtualMail") String virtualMail);
}
