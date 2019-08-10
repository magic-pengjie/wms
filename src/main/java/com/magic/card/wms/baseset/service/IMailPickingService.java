package com.magic.card.wms.baseset.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.magic.card.wms.baseset.model.po.MailPicking;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.baseset.service
 * 快递篮拣货服务接口
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/25/025 9:57
 * @since : 1.0.0
 */
public interface IMailPickingService extends IService<MailPicking> {

    /**
     * 自动生成快递篮拣货单服务
     * @param mailPicking
     * @param operator
     */
    void generatorMailPicking(MailPicking mailPicking, String operator);

    /**
     * 自动生成配货单清单
     * @return
     */
    List<Map> generatorInvoiceList(String pickNo);

    /**
     * 配货单清单检查数据
     * @param pickNo
     * @param commodityCode
     * @return
     */
    List<Map> invoiceCheckList(String pickNo, String commodityCode);

    /**
     * 自动更新拣货篮所有订单完成状态
     * @param pickNo 拣货单号
     */
    void autoUpdatePickingFinishState(String pickNo);

    /**
     * 更新拣货篮指定订单 -> 完成状态
     * @param mailNo 快递单号
     */
    void updatePickingFinishState(String mailNo);

    /**
     * 获取拣货单各个拣货篮的完成状态
     * @param pickNo
     * @return
     */
    List<Map> obtainPickingFinishState(String pickNo);

    /**
     * 获取拣货单所有漏检商品数据信息
     * @param pickNo
     * @param state
     * @return
     */
    List<Map> omitOrderCommodityList(String pickNo, Integer state);


    /**
     * 加载拣货单所有拣货篮数据
     * @param pickNo
     * @return
     */
    List<Map> loadMailPickings(String pickNo);
   /**
    * 快递单发送邮政
    * @param pickNo 拣货单号
    * @param orderNo 订单号
    */
    void sendOrder(String pickNo,String orderNo) throws UnsupportedEncodingException;

    /**
     * 包裹称重
     * @param mailNo 快递单号
     * @param realWeight 称重重量（kg）
     * @param ignore 是否忽略称重异常
     */
    void packageWeigh(String mailNo, BigDecimal realWeight, Boolean ignore);
}
