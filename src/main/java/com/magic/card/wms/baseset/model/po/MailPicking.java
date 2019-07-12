package com.magic.card.wms.baseset.model.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 快递篮拣货表
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-24
 */
@Data
@TableName("wms_mail_picking_info")
@EqualsAndHashCode(callSuper = false)
public class MailPicking extends BasePo implements Serializable {

    private static final long serialVersionUID = -2952266098310418038L;

    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 快递单号
     */
    private String mailNo;
    /**
     * 拣货单号
     */
    private String pickNo;
    /**
     * 拣货篮号
     */
    private Integer basketNum;
    /**
     * 预测重量
     */
    private BigDecimal presetWeight;
    /**
     * 秤重重量
     */
    private BigDecimal realWeight;
    /**
     * 快递费
     */
    private BigDecimal expressFee;
    /**
     * 重量单位
     */
    private String weightUnit;
    /**
     * 是否拣货完成(是:Y否:N)
     */
    private Integer isFinish;
    /**
     * 合并状态0::未合并 1:被合并 3:合并
     */
    private String mergeState;
    /**
     * 被合并后快递单号
     */
    private String mergeMailNo;
    /**
     * 发送邮政状态(未发送:0;已发送:1;发送失败:2;终止发送3)
     */
    private String sendState;
    /**
     * 发送次数
     */
    private Integer sendNums;
}
