package com.magic.card.wms.baseset.model.po;

import com.baomidou.mybatisplus.enums.IdType;
import com.magic.card.wms.common.model.po.BasePo;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 物流跟踪信息表
 * </p>
 *
 * @author pengjie
 * @since 2019-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wms_logistics_tracking_info")
public class LogisticsTrackingInfo extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息唯一序列号
     */
    private String serialNo;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 运单号
     */
    private String traceNo;
    /**
     * 操作时间
     */
    private Date opTime;
    /**
     * 操作码
     */
    private String opCode;
    /**
     * 操作名称
     */
    private String opName;
    /**
     * 操作描述
     */
    private String opDesc;
    /**
     * 操作网点省名
     */
    private String opOrgProvName;
    /**
     * 操作网点城市
     */
    private String opOrgCity;
    /**
     * 操作网点编码
     */
    private String opOrgCode;
    /**
     * 操作网点名称
     */
    private String opOrgName;
    /**
     * 操作员工号
     */
    private String operatorNo;
    /**
     * 单操作员工名称
     */
    private String operatorName;
    /**
     * 执行结果1:成功 0:失败
     */
    private Integer responseState;
    /**
     * 错误描述信息
     */
    private String errorDesc;
  
}
