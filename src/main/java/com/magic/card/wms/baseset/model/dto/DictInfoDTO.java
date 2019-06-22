package com.magic.card.wms.baseset.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.dto
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/18/018 17:52
 * @since : 1.0.0
 */
@Data
public class DictInfoDTO implements Serializable {
    private static final long serialVersionUID = 4449360579979110353L;

    private Long id;
    /**
     * 编码类型
     */
    private String dictType;
    /**
     * 编码类型名称
     */
    private String dictTypeName;
    /**
     * 编码值
     */
    private String dictCode;
    /**
     * 编码名称
     */
    private String dictName;
    /**
     * 父级编码类型
     */
    private String dictTypeP;
    /**
     * 编码类型名称
     */
    private String dictTypeNameP;
    /**
     * 父级编码值
     */
    private String dictCodeP;
    /**
     * 编码名称
     */
    private String dictNameP;

    private String remark;
}
