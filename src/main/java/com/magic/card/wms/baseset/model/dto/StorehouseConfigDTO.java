package com.magic.card.wms.baseset.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.dto
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/19/019 17:40
 * @since : 1.0.0
 */
@Data
public class StorehouseConfigDTO implements Serializable {
    private static final long serialVersionUID = 7720689663733069388L;

    private Long id;
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
    /**
     * 可用数量
     */
    private Integer availableNums;
    /**
     * 冻结数量
     */
    private Integer lockNums;

    /**
     * 操作类型(1:入库 2:出库)
     */
    private Integer oprType;

    private String remark;
}
