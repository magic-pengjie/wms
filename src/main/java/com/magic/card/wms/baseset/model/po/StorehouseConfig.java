package com.magic.card.wms.baseset.model.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 库位关系设置
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-19
 */
@Data
@TableName("wms_storehouse_config")
@EqualsAndHashCode(callSuper = false)
public class StorehouseConfig extends BasePo implements Serializable {

    private static final long serialVersionUID = -8873076960060337223L;

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
     * 入库时间
     */
    private Date entryTime;
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
     * 生产日期
     */
    private String startTime;

    /**
     * 过期时间
     */
    private String endTime;

    /**
     * 操作类型(1:入库 2:出库)
     */
    private Integer oprType;
    /***
     * 保质期
     */
    private Double shilfLife;
}
