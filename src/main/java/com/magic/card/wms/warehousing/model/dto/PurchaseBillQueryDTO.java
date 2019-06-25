package com.magic.card.wms.warehousing.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 采购单据查询dto
 * @author PENGJIE
 * @date 2019年6月22日
 */
@Data
@ApiModel("采购单据查询模型")
public class PurchaseBillQueryDTO {
	/**
     * 采购单id
     */
	@ApiModelProperty("采购单id")
    private Integer id;

	 /**
     * 采购单号
     */
	@ApiModelProperty("采购单号")
    private String purchaseNo;
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
     * 商品名称
     */
	@ApiModelProperty("商品名称")
    private String commodityName;
    /**
     * 采购日期
     */
	@ApiModelProperty("采购日期")
    private String purchaseDate;
    
    /**
     * 单据状态(保存:save确认:confirm 作废:cancel )
     */
	@ApiModelProperty("单据状态(保存:save确认:confirm 作废:cancel )")
    private String billState;
}
