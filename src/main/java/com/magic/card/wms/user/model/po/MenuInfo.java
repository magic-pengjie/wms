package com.magic.card.wms.user.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单实体
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_menu_info")
public class MenuInfo extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 父级菜单ID
     */
    private Integer parentKey;
    /**
     * 菜单编码
     */
    private String menuCode;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单描述
     */
    private String menuDesc;
    /**
     * 根目录
     */
    private String rootMenuCode;
    /**
     * 菜单级别1~5
     */
    private String menuGrade;
    /**
     * 接口URL
     */
    private String apiUrl;


}
