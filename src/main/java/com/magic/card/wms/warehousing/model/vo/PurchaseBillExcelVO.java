package com.magic.card.wms.warehousing.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/***
 * 采购单导入excel模型
 * @author PENGJIE
 * @date 2019年6月22日
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PurchaseBillExcelVO extends BaseRowModel {
	 /**
     * 采购单号
     */
	@ExcelProperty(value = "采购订单号", index = 0)
    private String purchaseNo;
    /**
     * 商家名称
     */
	@ExcelProperty(value = "商家名称", index = 1)
    private String customerName;
    
    /**
     * 商品名称
     */
	@ExcelProperty(value = "商品名称", index = 2)
    private String commodityName;
    /**
     * 商品型号
     */
	@ExcelProperty(value = "型号", index = 3)
    private String modelNo;
    /**
     * 规格
     */
	@ExcelProperty(value = "规格", index = 4)
    private String spec;
    /**
     * 单位
     */
	@ExcelProperty(value = "单位", index = 5)
    private String unit;
    /**
     * 商品条码
     */
	@ExcelProperty(value = "商品条码", index = 6)
    private String barCode;
    /**
     * 体积m3
     */
	@ExcelProperty(value = "体积", index = 7)
    private Double volume;
	 /**
     * 重量KG
     */
	@ExcelProperty(value = "重量", index = 8)
    private Double weight;
    /**
     * 采购数量
     */
	@ExcelProperty(value = "采购数量", index = 9)
    private Integer purchaseNums;
	  /**
     * 采购日期
     */
	@ExcelProperty(value = "采购日期", index = 10)
    private String purchaseDate;
    /**
     * 到货日期
     */
	@ExcelProperty(value = "到货日期", index = 11)
    private String arrivalDate;
    /**
     * 生产日期
     */
	@ExcelProperty(value = "生产日期", index = 12)
    private String productionDate;
    /**
     * 保质期值
     */
	@ExcelProperty(value = "保质期", index = 13)
    private Double shilfLife;
    
    /**
     * 备注
     */
	@ExcelProperty(value = "备注", index = 14)
    private String remark;
    
    /**
     * 联系人
     */
	@ExcelProperty(value = "联系人", index = 15)
    private String contacts;
    /**
     * 联系电话
     */
	@ExcelProperty(value = "联系电话", index = 16)
    private String contactsTel;
    /**
     * 地址
     */
	@ExcelProperty(value = "地址", index = 17)
    private String address;
    /**
     * 制单人
     */
	@ExcelProperty(value = "制单人", index = 18)
    private String maker;
    /**
     * 制单时间
     */
	@ExcelProperty(value = "制单时间", index = 19)
    private String makeDate;
}
