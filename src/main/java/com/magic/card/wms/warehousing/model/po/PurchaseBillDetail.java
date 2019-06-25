package com.magic.card.wms.warehousing.model.po;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 采购单明细表
 * </p>
 *
 * @author pengjie
 * @since 2019-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wms_purchase_bill_detail")
public class PurchaseBillDetail extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 采购id
     */
    @ApiModelProperty("采购id")
    private Long purchaseId;
    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称 not null")
   	@NotBlank(message = "商家名称不能为空")
    private String commodityName;
    /**
     * 商品型号
     */
    @ApiModelProperty("商品型号")
    private String modelNo;
    /**
     * 规格
     */
    @ApiModelProperty("规格")
    private String spec;
    /**
     * 单位
     */
    @ApiModelProperty("单位 not null")
   	@NotBlank(message = "单位不能为空")
    private String unit;
    /**
     * 商品条码
     */
    @ApiModelProperty("商品条码 not null")
   	@NotBlank(message = "商品条码不能为空")
    private String barCode;
    /**
     * 重量KG
     */
    @ApiModelProperty("重量KG not null")
    @NotNull(message = "重量不能为空")
    private Double weight;
    /**
     * 体积m3
     */
    @ApiModelProperty("体积m3 not null")
    @NotNull(message = "体积m3不能为空")
    private Double volume;
    /**
     * 采购数量
     */
    @ApiModelProperty("采购数量 not null")
   	@NotNull(message = "采购数量为空")
    private Integer purchaseNums;
    /**
     * 到货日期
     */
    @ApiModelProperty("到货日期 not null")
   	@NotBlank(message = "到货日期不能为空")
    private String arrivalDate;
    /**
     * 生产日期
     */
    @ApiModelProperty("生产日期")
    private String productionDate;
    /**
     * 保质期值
     */
    @ApiModelProperty("保质期值")
    private Double shilfLife;


}
