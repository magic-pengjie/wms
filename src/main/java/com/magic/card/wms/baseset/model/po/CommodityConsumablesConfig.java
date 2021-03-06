package com.magic.card.wms.baseset.model.po;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;

import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品耗材设置表
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-21
 */
@Data
@TableName("wms_commodity_consumables_config")
@EqualsAndHashCode(callSuper = false)
public class CommodityConsumablesConfig extends BasePo implements Serializable {

    private static final long serialVersionUID = -2984424458683252346L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 商品id
     */
    private String commodityId;
    /**
     * sku id
     */
    private String skuId;
    /**
     * 消耗商品id
     */
    private String useCommodityId;
    /**
     * 消耗商品 sku id
     */
    private String useSkuId;
    /**
     * 左区间值
     */
    private Integer leftValue;
    /**
     * 右区间值
     */
    private Integer rightValue;
    /**
     * 消耗数量
     */
    private Integer useNums;
    /**
     * 数据状态
     */
    private Integer state;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 备注
     */
    private String remark;


}
