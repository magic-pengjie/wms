package com.magic.card.wms.warehousing.model.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.magic.card.wms.warehousing.model.po.PurchaseBillDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 采购单DTO
 * @author PENGJIE
 * @date 2019年6月22日
 */
@Data
@ApiModel("采购单修改模型")
public class PurchaseBillDTO {
	 /**
     * 操作类型暂存save;提交submit
     */
	@ApiModelProperty("操作类型暂存save;提交submit")
	private String billState;
	 /**
     * 主键id
     */
	@ApiModelProperty("主键id")
	private Long id;
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
	@ApiModelProperty("商家名称 not null")
    private String customerName;
	 /**
     * 是否为食品
     */
	@ApiModelProperty("是否为食品")
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
     * 备注
     */
	@ApiModelProperty("备注")
    private String remark;
	/**
     * 采购单商品明细
     */
	@ApiModelProperty("采购单商品明细 not null")
    private List<PurchaseBillDetail> detailList;
}
