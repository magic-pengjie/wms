package com.magic.card.wms.user.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户实体
 * </p>
 *
 * @author pengjie
 * @since 2019-06-13
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("wms_user")
public class User extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String userNo;
    /**
     * 密码
     */
    private String password;
    /**
     * 姓名
     */
    private String name;
   

}
