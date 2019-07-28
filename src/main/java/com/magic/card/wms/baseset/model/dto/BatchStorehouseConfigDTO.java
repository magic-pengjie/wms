package com.magic.card.wms.baseset.model.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.dto
 * 批量添加库位配置信息
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/27 17:47
 * @since : 1.0.0
 */
@Data
public class BatchStorehouseConfigDTO implements Serializable {
    private static final long serialVersionUID = -8665956761450644196L;
    /**
     * 库位id
     */
    @Size(min = 1)
    private String[] storehouseIds;
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
     * 备注
     */
    private String remark;
}
