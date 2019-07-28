package com.magic.card.wms.baseset.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;

/**
 * <p>
 * 仓库表
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-19
 */
@Data
@TableName("wms_storehouse_info")
public class StorehouseInfo extends BasePo implements Serializable {

    private static final long serialVersionUID = -32017103645816153L;

    /**
     * 库位编码
     */
    private String storeCode;
    /**
     * 仓库编码
     */
    private String houseCode;
    /**
     * 仓库类型
     */
    private String areaCode;
    /**
     * 优先值
     */
    private Integer priorityValue;
    /**
     * 是否冻结库存(否:N 是:Y)
     */
    private int isFrozen;

}
