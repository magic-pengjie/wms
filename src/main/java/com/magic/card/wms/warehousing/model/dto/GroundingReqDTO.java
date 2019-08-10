package com.magic.card.wms.warehousing.model.dto;

import javax.validation.constraints.NotNull;

import com.magic.card.wms.warehousing.model.po.PurchaseBillDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 确认收货模型DTO
 * @author PENGJIE
 * @date 2019年6月22日
 */
@Data
@ApiModel("上架模型")
public class GroundingReqDTO {
	
	 /**
     * 采购单id
     */
	@ApiModelProperty("采购单id")
	@NotNull(message = "采购单id不能为空")
	private Long id;
	 /**
	   * 商家编码
     */
	@ApiModelProperty("商家编码")
	@NotNull(message = "商家编码不能为空")
	private String customerCode;
	
	/**
     * 商品明细
     */
	@ApiModelProperty("采购单商品明细 not null")
    private PurchaseBillDetail detail;

}
