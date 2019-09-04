package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.po.MailPickingDetail;

import java.math.BigDecimal;
import java.util.ArrayList;
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
//    List<Map> virtualMails(String customerCode, Integer executeSize);

    /**
     * 获取商户订单不同区域的快递单数据
     * @param customerCode 客户CODE
     * @param executeSize 允许操作数
     * @return
     */
    Map<String, List<List<Map>>> areaVirtualMails(String customerCode, Integer executeSize);

    /**
     * 快递包裹预重 （kg）
     * 拣货篮总货物称重
     * @param virtualMail 虚拟快递号（uuid）
     * @param systemOrderNo 系统订单号
     * @return
     */
    BigDecimal mailPickingWeight(String virtualMail, String systemOrderNo);

    /**
     * 获取拣货单复核商品数据列表
     * @param pickNo 拣货单号
     * @param commodityCode 商品二维码
     * @return
     */
    List<Map> invoiceCheckCommodityList(String pickNo, String commodityCode);
    /**
     * 获取拣货完成包裹的清单
     * @param mailNo 快递单号
     * @return
     */
    List<Map> packageFinishedList(String mailNo);

    /**
     * 获取拣货未完成包裹清单
     * @param mailNo 快递单号
     * @return
     */
    List<Map> packageUnfinishedList(String mailNo);

    /**
     * 批量获取快递单商品
     * @param mails 快递单号（多个）
     * @return
     */
    Map<String, List> loadBatchPackageCommodity(List<String> mails);

    /**
     * 拣货单商品数据对应库存不足时通知处理
     * @param pickNo
     */
    void needNoticeReplenishment(String pickNo);

    /**
     * 包裹数据漏检记录
     * @param pickNo 拣货单号
     * @param mailNo 快递单号
     * @param commodityCode 商品编号
     * @param omitNums 漏检数量
     */
    void packageOmit(String pickNo, String mailNo, String commodityCode, int omitNums);

    /**
     * 更新拣货单包裹数据
     * @param pickNo 拣货单号
     * @param mailNo 快递单号
     * @param excludeCommodities 排除产品条形码
     */
    void updatePackageCommodity(String pickNo, String mailNo, ArrayList<String> excludeCommodities);
}
