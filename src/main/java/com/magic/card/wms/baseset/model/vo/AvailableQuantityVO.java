package com.magic.card.wms.baseset.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.vo
 * 库位商品存储情况
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/9/2 16:33
 * @since : 1.0.0
 */
@Data
public class AvailableQuantityVO implements Serializable {
    private static final long serialVersionUID = 8022078646515086411L;
    /**
     * 配置库位ID
     */
    private Long id;
    /**
     * 库位ID
     */
    private Long storeId;
    /**
     * 商家编码
     */
    private String customerCode;
    /**
     * 商家商品ID
     */
    private Long commodityId;
    /**
     * 商品条码
     */
    private String commodityCode;
    /**
     * 库位类型编码
     */
    private String houseCode;
    /**
     * 库位区域编码
     */
    private String areaCode;
    /**
     * 库位编码
     */
    private String storeCode;
    /**
     * 库位最大容量
     */
    private Integer storeNums;
    /**
     * 库位当前可用量
     */
    private Integer availableNums;
    /**
     * 库位已锁定量
     */
    private Integer lockNums;
    /**
     * 库位顺序值
     */
    private Integer priorityValue;
}
