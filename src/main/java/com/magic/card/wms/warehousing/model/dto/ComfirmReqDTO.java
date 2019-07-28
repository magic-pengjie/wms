package com.magic.card.wms.warehousing.model.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
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
@ApiModel("确认收货模型")
public class ComfirmReqDTO {
	
	 /**
     * 采购单id
     */
	@ApiModelProperty("采购单id")
	@NotNull(message = "采购单id不能为空")
	private Long id;
	/**
     * 采购单状态
     */
	@ApiModelProperty("采购单状态save(保存):显示开始收货按钮、删除按钮;recevieing(待收货)显示收货完成按钮;recevied(已收货):显示入库按钮;stored(已入库):显示审核按钮")
	@NotBlank(message = "采购单状态不能为空")
	private String billState;
	/**
     * 审批结果1-通过 0-不通过
     */
	private String approveResult;
	/**
     * 审批意见
     */
	@ApiModelProperty("审批意见")
	private String approveDesc;
	
	/**
     * 商品明细
     */
	@ApiModelProperty("采购单商品明细 not null")
    private List<PurchaseBillDetail> detailList;

}
