package com.magic.card.wms.common.model.po;

import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 持久类基类
 * </p>
 *
 * @author pengjie
 * @since 2019-06-13
 */
@Data
public class BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 数据状态
     */
    private Integer state;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 备注
     */
    private String remark;

}
