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
     * @param page 分页信息
     * @param entityWrapper 查询信息
     * @return
     */
    List<Map> virtualMails(Page page, @Param("ew") EntityWrapper entityWrapper);

    /**
     * 获取包裹商品对应的耗材清单
     * @param virtualMail 虚拟快递单号 UUID
     * @return
     */
    List<Map> mailPickingCommodityInfo(@Param("virtualMail") String virtualMail);

    /**
     * 获取拣货单复检商品清单列表
     * @param pickNo 拣货单号
     * @param commodityCode 商品条形码
     * @return
     */
    List<Map> invoiceCheckCommodityList(@Param("pickNo") String pickNo, @Param("commodityCode") String commodityCode);

    /**
     * 批量加载包裹商品数据
     * @param entityWrapper  查询、排序条件
     * @return
     */
    List<Map> batchLoadMailCommodity(@Param("ew") EntityWrapper entityWrapper);
}
