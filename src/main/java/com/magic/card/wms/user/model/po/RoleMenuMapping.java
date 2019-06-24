package com.magic.card.wms.user.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 	角色菜单关系映射表实体
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_role_menu_mapping")
public class RoleMenuMapping extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限主键ID
     */
    private Long roleKey;
    /**
     * 菜单主键ID
     */
    private Long menuKey;

}
