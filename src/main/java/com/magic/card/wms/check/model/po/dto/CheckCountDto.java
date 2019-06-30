package com.magic.card.wms.check.model.po.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 盘点统计查询
 * @author Zhouhao
 *
 */
@Data
@ApiModel(description = "盘点查询统计 請求实体类")
public class CheckCountDto implements Serializable{
	
	private static final long serialVersionUID = 1727581510222482592L;
	
    /**
     * 	盘点类型
     */
    @ApiModelProperty("盘点类型(M:月盘，Q:季盘，Y:年盘)")
	private String checkType;

    /**
     * 	盘点维度
     */
    @ApiModelProperty("盘点维度(商家，商品，库位，库区，全部)")
	private String checkValue;

}
