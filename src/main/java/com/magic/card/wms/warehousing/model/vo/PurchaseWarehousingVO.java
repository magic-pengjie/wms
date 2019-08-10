package com.magic.card.wms.warehousing.model.vo;

import com.magic.card.wms.warehousing.model.po.PurchaseBillDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 采购单商品上架vo
 * @author PENGJIE
 * @date 2019年8月4日
 */
@Data
@ApiModel("采购单商品上架vo")
public class PurchaseWarehousingVO{
	 /**
     * 主键id
     */
	@ApiModelProperty("主键id")
	private long id;
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
     * 收货单号
     */
	@ApiModelProperty("收货单号")
    private String receivNo;
	/**
     * 入库单号
     */
	@ApiModelProperty("入库单号")
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
     * 是否食品
     */
	@ApiModelProperty("是否食品")
	private Integer isFood;
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
     * 收货人
     */
	@ApiModelProperty("收货人")
    private String receivUser;
    /**
     * 收货时间
     */
	@ApiModelProperty("收货时间")
    private String receivDate;
    
	/**
	 * 备注
	 */
	@ApiModelProperty("remark")
    private String remark;
	
	 /**
     *单据状态(保存:save ;已收货:recevied ;已入库:stored;已审核:approved;审核失败:approve_fail 作废:cancel )
     */
	@ApiModelProperty("单据状态(保存:save ;已收货:recevied ;已入库:stored;已审核:approved;审核失败:approve_fail 作废:cancel )")
	private String billState;
    /**
     * 采购商品明细
     */
	@ApiModelProperty("采购商品明细")
    private PurchaseBillDetail detail;
}
