package com.magic.card.wms.warehousing.model.vo;

import java.util.List;

import com.magic.card.wms.warehousing.model.po.PurchaseBillDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 采购单vo
 * @author PENGJIE
 * @date 2019年6月22日
 */
@Data
@ApiModel("采购单返回模型")
public class PurchaseBillVO{
	/**
	 * 主键
	 */
	@ApiModelProperty("主键")
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
    
    /**
     * 采购单商品明细
     */
	@ApiModelProperty("采购单商品明细")
    private List<PurchaseBillDetail> detailList;
}
