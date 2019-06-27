package com.magic.card.wms.user.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-18
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("wms_user_role_mapping")
public class UserRoleMapping extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键ID
     */
    private Long userKey;
    /**
     * 权限主键ID
     */
    private Long roleKey;


}
