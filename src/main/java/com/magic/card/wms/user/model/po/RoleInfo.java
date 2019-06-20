package com.magic.card.wms.user.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限实体
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_role_info")
public class RoleInfo extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限类型
     */
    private String roleType;
    /**
     * 权限编码
     */
    private String roleCode;
    /**
     * 权限名称
     */
    private String roleName;
    /**
     * 权限描述
     */
    private String roleDesc;
    /**
     * 1:展示,0:不展示
     */
    private Integer displayFlag;


}
