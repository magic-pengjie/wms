package com.magic.card.wms.user.model.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *	菜单 List查詢請求实体类
 * </p>
 * @author zhouhao
 * @since 2019-06-22
 */
@Data
@ApiModel(description = "菜单 List查詢請求实体类")
public class MenuQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 	父级菜单ID
     */
    @ApiModelProperty("父级菜单ID")
    private Integer parentKey;
    /**
     * 	菜单编码
     */
    @ApiModelProperty("菜单编码")
    private String menuCode;
    /**
     * 	菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String menuName;
    /**
     * 	菜单描述
     */
    @ApiModelProperty("菜单描述")
    private String menuDesc;
    /**
     * 	根目录
     */
    @ApiModelProperty("根目录")
    private String rootMenuCode;
    /**
     * 	菜单级别1~5
     */
    @ApiModelProperty("菜单级别1~5")
    private String menuGrade;
    /**
     * 	路由URL
     */
    @ApiModelProperty("路由URL")
    private String routeUrl;
    /**
     * 	目录或按钮；0:页面，1:按钮
     */
    @ApiModelProperty("目录或按钮标志：0:页面，1:按钮")
    private String pageBtnFlag;
    /**
     * 	接口URL
     */
    @ApiModelProperty("接口URL")
    private String apiUrl;
    
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MenuInfo [");
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
		if (pageBtnFlag != null)
			builder.append("pageBtnFlag=").append(pageBtnFlag).append(", ");
		if (apiUrl != null)
			builder.append("apiUrl=").append(apiUrl);
		builder.append("]");
		return builder.toString();
	}
   
}
