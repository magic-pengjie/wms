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
    /***
     * 仓库类型
     */
    private String areaCode;
    /**
     * 仓库编码
     */
    private String houseCode;
    /**
     * 库位编码
     */
    private String storeCode;
   
    /**
     * 可用库存量
     */
    private Integer availableNums;
}
