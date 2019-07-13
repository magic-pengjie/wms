package com.magic.card.wms.baseset.model.xml;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * 邮政小包xml订单模型
 * @author PENGJIE
 * @date 2019年7月10日
 */
@Data
@XStreamAlias("RequestOrder")
public class RequestOrderXml implements Serializable {
    private static final long serialVersionUID = -5050831342653951057L;
    /**
     * 电商标识
     */
    private String ecCompanyId;
    /**
     * 客户标识
     */
    private String customerId;
    /**
     * 物流公司
     */
    private String logisticProviderID;
    /**
     * 物流订单号
     */
    private String txLogisticID;
    /**
     * 业务交易号（新业务类型待定：252国内小包）
     */
    private String tradeNo;
    /**
     * 物流运单号
     */
    private String mailNo;
    
    /**
     * 订单类型(0-COD 1-普通订单 
		3 - 退货单),标准接口默认设置为1
     */
    private int orderType;
    /**
     * 服务类型(0-自己联系 1-在线下单（上门揽收）4-限时物流 8-快捷COD 16-快递保障)，标准接口默认设置为0
     */
    private int serviceType;
    /**
     * 上门取货时间段
     */
    private Date sendStartTime;
    /**
     * 上门取货时间段
     */
    private Date sendEndTime;
    /**
     * 商品类型（保留字段，暂时不用）
     */
    private int special;
    /**
     * 订单备注
     */
    private String remark;
   
    
    /**
     * 总服务费[COD]：（单位：分
     */
    private Long totalServiceFee;
    /**
     * 买家服务费[COD] ：（单位：分）
     */
    private Long buyServiceFee;
    /**
     * 物流公司分润[COD] ：（单位：分）
     */
    private Long codSplitFee;
    /**
     * 商品金额
     */
    private Long goodsValue=0l;
    /**
     * 商品重量（单位：克）
     */
    private Long weight;
    
    /**
     * 收件人员信息
     */
    @XStreamAlias("sender")
    private PersionXml sender;
    @XStreamAlias("items")
    List<OrderCommodityXml> commodityList;
    
}
