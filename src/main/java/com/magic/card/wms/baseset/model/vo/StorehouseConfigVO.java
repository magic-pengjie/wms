package com.magic.card.wms.baseset.model.vo;

import lombok.Data;

/***
 * 推荐库位vo
 * @author PENGJIE
 * @date 2019年7月10日
 */
@Data
public class StorehouseConfigVO {
	 /**
     * 库位id
     */
    private String storehouseId;
    /**
     * 客户id
     */
    private String customerId;
    /**
     * 商品id
     */
    private String commodityId;
    /**
     * 库存量
     */
    private Integer storeNums;
}
