package com.magic.card.wms.baseset.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;

/**
 * <p>
 * 字典表（维护商品类型、商品属性等）
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-18
 */
@Data
@TableName("wms_dict_info")
public class DictInfo extends BasePo implements Serializable {

    private static final long serialVersionUID = -1244153701462937309L;

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
}
