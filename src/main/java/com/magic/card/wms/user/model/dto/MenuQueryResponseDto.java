package com.magic.card.wms.user.model.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *	菜单treeList查詢返回实体类
 * </p>
 * @author zhouhao
 * @since 2019-06-22
 */
@Data
@ApiModel(description = "菜单 TreeList查詢 返回实体类", value="菜单 TreeList查詢返回实体类")
public class MenuQueryResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="菜单ID")
    private Long id;
    /**
     * 	父级菜单ID
     */
    @ApiModelProperty(value="父级菜单ID")
    private Integer parentKey;
    /**
     * 	菜单编码
     */
    @ApiModelProperty(value="菜单编码")
    private String menuCode;
    /**
     * 	菜单名称
     */
    @ApiModelProperty(value="菜单名称")
    private String menuName;
    /**
     * 	菜单描述
     */
    @ApiModelProperty(value="菜单描述")
    private String menuDesc;
    /**
     * 	根目录
     */
    @ApiModelProperty(value="根目录")
    private String rootMenuCode;
    /**
     * 	菜单级别1~5
     */
    @ApiModelProperty(value="菜单级别1~5")
    private String menuGrade;
    /**
     * 	路由URL
     */
    @ApiModelProperty(value="路由URL")
    private String routeUrl;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 	目录或按钮；0:页面，1:按钮
     */
    @ApiModelProperty(value="目录或按钮标志：0:页面，1:按钮")
    private String pageBtnFlag;
    /**
     * 	接口URL
     */
    @ApiModelProperty(value="接口URL")
    private String apiUrl;
    
    /**
     * 	接口URL
     */
    @ApiModelProperty(value="子级目录")
    private List<MenuQueryResponseDto> childMenu;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MenuQueryResponseDto [");
		if (parentKey != null)
			builder.append("parentKey=").append(parentKey).append(", ");
		if (menuCode != null)
			builder.append("menuCode=").append(menuCode).append(", ");
		if (menuName != null)
			builder.append("menuName=").append(menuName).append(", ");
		if (menuDesc != null)
			builder.append("menuDesc=").append(menuDesc).append(", ");
		if (rootMenuCode != null)
			builder.append("rootMenuCode=").append(rootMenuCode).append(", ");
		if (menuGrade != null)
			builder.append("menuGrade=").append(menuGrade).append(", ");
		if (routeUrl != null)
			builder.append("routeUrl=").append(routeUrl).append(", ");
		if (icon != null)
			builder.append("icon=").append(icon).append(", ");
		if (pageBtnFlag != null)
			builder.append("pageBtnFlag=").append(pageBtnFlag).append(", ");
		if (apiUrl != null)
			builder.append("apiUrl=").append(apiUrl).append(", ");
		if (childMenu != null)
			builder.append("childMenu=").append(childMenu);
		builder.append("]");
		return builder.toString();
	}
	
}
