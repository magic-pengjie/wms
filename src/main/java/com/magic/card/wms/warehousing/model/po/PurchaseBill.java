package com.magic.card.wms.warehousing.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 采购单据表
 * </p>
 *
 * @author pengjie
 * @since 2019-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wms_purchase_bill")
public class PurchaseBill extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;
	 /**
     * 采购单号
     */
	@ApiModelProperty("采购单号")
    private String purchaseNo;
	/**
     * 采购单名称
     */
	@ApiModelProperty("采购单名称")
	private String name;
    /**
     * 商家编码
     */
	@ApiModelProperty("商家编码")
    private String customerCode;
    /**
     * 商家名称
     */
	@ApiModelProperty("商家名称")
    private String customerName;
	 /**
     * 是否食品
     */
	@ApiModelProperty("是否食品")
	private boolean isFood;
    /**
     * 采购日期
     */
	@ApiModelProperty("采购日期")
    private String purchaseDate;
    /**
     * 联系人
     */
	@ApiModelProperty("联系人")
    private String contacts;
    /**
     * 联系电话
     */
	@ApiModelProperty("联系电话")
    private String contactsTel;
    /**
     * 地址
     */
	@ApiModelProperty("地址")
    private String address;
    /**
     * 制单人
     */
	@ApiModelProperty("制单人")
    private String maker;
    /**
     * 制单时间
     */
	@ApiModelProperty("制单时间")
    private String makeDate;
    /**
     * 单据状态(保存:save确认:confirm 作废:cancel )
     */
	@ApiModelProperty("单据状态(保存:save确认:confirm 作废:cancel )")
    private String billState;


}
