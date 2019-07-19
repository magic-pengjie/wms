package com.magic.card.wms.baseset.model.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.po
 *  拣货篮商品明细（一个货篮一个快递）
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/17 17:15
 * @since : 1.0.0
 */
@Data
@TableName("wms_mail_picking_detail")
@EqualsAndHashCode(callSuper = false)
public class MailPickingDetail extends BasePo implements Serializable {
    private static final long serialVersionUID = -6413749600019139699L;

    /**
     * 系统订单号
     */
    private String orderNo;
    /**
     * 快递单号（面单号）
     */
    private String mailNo;
    /**
     * 拣货单号
     */
    private String pickNo;
    /**
     * 商品条码
     */
    private String commodityCode;
    /**
     * 打包商品个数
     */
    private Integer packageNums;
    /**
     * 已拣商品数
     */
    private Integer pickNums;
}
