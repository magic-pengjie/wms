package com.magic.card.wms.warehousing.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单据查询dto
 * @author PENGJIE
 * @date 2019年6月22日
 */
@Data
@ApiModel("单据查询模型")
public class BillQueryDTO {
	/**
     * id
     */
	@ApiModelProperty("id")
    private Integer id;

	 /**
     * 采购单据编号
     */
	@ApiModelProperty("采购单编号")
    private String purchaseNo;
	 /**
     * 收货单编号
     */
	@ApiModelProperty("收货单编号")
    private String receivNo;
	 /**
     * 入库单编号
     */
	@ApiModelProperty("入库单编号")
    private String warehousingNo;
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
     * 收货日期
     */
	@ApiModelProperty("收货日期")
    private String receivDate;
	 /**
     * 入库日期
     */
	@ApiModelProperty("入库日期")
    private String warehousingDate;
	 /**
     * 制单日期
     */
	@ApiModelProperty("制单日期")
    private String makeDate;
    
    /**
     * 单据状态(待收货recevieing已收货:recevied ,已审核approved;审批失败 approve_fail 作废:cancel )
     */
	@ApiModelProperty("单据状态(保存 save 已收货:recevied ,已审核approved;审批失败 approve_fail 作废:cancel )")
    private String billState;
	/**
	 * 
     * 单据类型1:采购  2:上架
     */
	@ApiModelProperty("单据类型1:采购  2:上架")
	private Integer billType;
}
