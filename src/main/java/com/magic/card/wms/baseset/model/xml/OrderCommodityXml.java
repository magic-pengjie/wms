package com.magic.card.wms.baseset.model.xml;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * 订单商品明细信息
 * @author PENGJIE
 * @date 2019年7月10日
 */
@Data
@XStreamAlias("item")
public class OrderCommodityXml implements Serializable {
    private static final long serialVersionUID = 615564015575473823L;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商品数量
     */
    private long number;

    /**
     * 商品单价
     */
    private long itemValue = 0l;
}
