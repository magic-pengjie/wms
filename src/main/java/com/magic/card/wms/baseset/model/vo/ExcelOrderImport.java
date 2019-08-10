package com.magic.card.wms.baseset.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.magic.card.wms.common.annotation.ExcelExportDataHandle;
import com.magic.card.wms.common.annotation.ExcelImportDataHandle;
import com.magic.card.wms.common.model.enums.BillState;
import com.magic.card.wms.common.utils.DataConvertUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * com.magic.card.wms.baseset.model.vo
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/2 15:26
 * @since : 1.0.0
 */
@Data
@Slf4j
public class ExcelOrderImport extends BaseRowModel implements Serializable {
    private static final long serialVersionUID = 6503001774939872543L;

    //region 订单商品基本信息
    /**
     * 订单号
     */
    @ExcelProperty(value = "订单号", index = 0)
    private String orderNo;
    /**
     * 商家编码
     */
    @ExcelProperty(value = "商家编码", index = 1)
    private String customerCode;
    /**
     * 商家名称
     */
    @ExcelProperty(value = "商家名称", index = 2)
    private String customerName;

    /**
     * 收件人姓名
     */
    @ExcelProperty(value = "收件人姓名", index = 7)
    private String reciptName;

    /**
     * 电话
     */
    @ExcelProperty(value = "电话", index = 8)
    private String reciptPhone;


    /**
     * 是否是批量订单
     */
    @ExcelProperty(value = "是否是批量订单", index = 9)
    private String targetIsBatch;

    /**
     * 是否B2B
     */
    @ExcelProperty(value = "是否B2B", index = 10)
    private String targetIsB2b;

    private int isBatch;
    private int isB2b;
    /**
     * 商品金额
     */
    @ExcelProperty(value = "商品金额", index = 11)
    private BigDecimal goodsValue;
    /**
     * 快递公司标识
     */
    @ExcelProperty(value = "快递公司标识", index = 12)
    private String expressKey;

//    /**
//     * 收货人邮编
//     */
//    @ExcelProperty(value = "收货人邮编", index = 4)
//    private String postCode;
    /**
     * 用户所在省
     */
    @ExcelProperty(value = "用户所在省", index = 13)
    private String prov;
    /**
     * 户所在市县（区）
     */
    @ExcelProperty(value = "户所在市县", index = 14)
    private String city;

    /**
     * 地址
     */
    @ExcelProperty(value = "地址", index = 15)
    private String reciptAddr;
    /**
     * 单据状态(保存:save确认:confirm 作废及退单:cancel )
     */
    @ExcelProperty(value = "单据状态", index = 16)
    private String billState;

    /**
     * 订单备注
     */
    @ExcelProperty(value = "订单备注", index = 17)
    private String remark;
    // endregion

    // region 订单商品基本信息
    /**
     * 商品条形码
     */
    @ExcelProperty(value = "商品条形码", index = 3)
    private String barCode;

    /**
     * 商品型号
     */
    @ExcelProperty(value = "商品型号", index = 4)
    private String modelNo;
    /**
     * 商品规格
     */
    @ExcelProperty(value = "商品规格", index = 5)
    private String spec;
    /**
     * 商品数量
     */
    @ExcelProperty(value = "商品数量", index = 6)
    private Integer numbers;
    // endregion

    @ExcelImportDataHandle
    public void excelDataConvertor() {
        isB2b = DataConvertUtil.isValue(targetIsB2b);
        isBatch = DataConvertUtil.isValue(targetIsBatch);
        billState = BillState.orderCode(billState);
    }

    @ExcelExportDataHandle
    public void excelExportDataHandle() {
        targetIsB2b = DataConvertUtil.isValue(isB2b);
        targetIsBatch = DataConvertUtil.isValue(isBatch);
        billState = BillState.orderDesc(billState);
    }

}
