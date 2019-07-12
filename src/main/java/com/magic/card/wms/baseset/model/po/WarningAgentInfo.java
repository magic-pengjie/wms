package com.magic.card.wms.baseset.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 预警代办表
 * </p>
 *
 * @author pengjie
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wms_warning_agent_info")
public class WarningAgentInfo extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关联外键
     */
    private String fid;
    /**
     * 所属模块
     */
    private String model;
    
    /**
     * 预警类型编码
     */
    private String typeCode;
    /**
     * 预警类型名称
     */
    private String typeName;
    /**
     * 预警任务编码
     */
    private String agentCode;
    /**
     * 预警任务名称
     */
    private String agentName;
    /**
     * 任务状态0:未处理 1::已处理
     */
    private Integer agentState;
    /**
     * 代办任务处理人
     */
    private String agentPerson;
    /**
     * 跳转参数
     */
    private String paramters;
}
