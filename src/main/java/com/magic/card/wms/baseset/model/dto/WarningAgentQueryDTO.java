package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WarningAgentQueryDTO {
	/**
     * 所属模块
     */
	@ApiModelProperty("所属模块")
    private String model;
	/**
     * 预警类型编码
     */
	@ApiModelProperty("仓库功能编码")
    private String typeCode;
    /**
     * 预警类型名称
     */
	@ApiModelProperty("预警类型名称")
    private String typeName;
    /**
     * 预警任务编码
     */
	@ApiModelProperty("预警任务编码")
    private String agentCode;
    /**
     * 预警任务名称
     */
	@ApiModelProperty("预警任务名称")
    private String agentName;
    /**
     * 任务状态0:未处理 1::已处理
     */
	@ApiModelProperty("务状态0:未处理 1::已处理")
    private Integer agentState;
    /**
     * 代办任务处理人
     */
	@ApiModelProperty("代办任务处理人")
    private String agentPerson;
}
