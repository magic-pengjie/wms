package com.magic.card.wms.common.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 编号生成表
 * </p>
 *
 * @author pengjie
 * @since 2019-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wms_code_product")
public class CodeProduct extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    private String type;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 编码
     */
    private String code;
    /**
     * 序号
     */
    private Long seq;
    /**
     * 日期
     */
    private String productDate;
    /**
     * 其他规则
     */
    private String otherKey;
    /**
     * 序号长度
     */
    @TableField(exist = false)
    private int length;


}
