package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.po.MailPickingDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.service
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/17 17:27
 * @since : 1.0.0
 */
public interface IMailPickingDetailService extends IService<MailPickingDetail> {

    /**
     * 添加信息
     * @param mailPickingDetail
     */
    void add(MailPickingDetail mailPickingDetail);

    /**
     * 删除系统订单号拣货篮详情数据
     * @param orderNo
     */
    void delete(String orderNo);

    /**
     * 获取虚拟快递单数据，最终生成拣货单
     *
     * @param customerCode 客户Code
     * @return
     */
    List<Map> virtualMails(String customerCode, Integer executeSize);

    /**
     * 快递包裹预重 （kg）
     * 拣货篮总货物称重
     * @param virtualMail 虚拟快递号（uuid）
     * @return
     */
    BigDecimal mailPickingWeight(String virtualMail);
}
