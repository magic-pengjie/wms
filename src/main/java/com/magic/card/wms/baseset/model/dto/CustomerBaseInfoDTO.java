package com.magic.card.wms.baseset.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.dto
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/18/018 10:46
 * @since : 1.0.0
 */
@Data
@ApiModel("客户信息")
public class CustomerBaseInfoDTO implements Serializable {
    private static final long serialVersionUID = -1319052759550689658L;

    @ApiModelProperty("客户ID， 修改时提供数据，否则验证不通过")
    private Long id;

    /**
     * 客户编码
     */
    @NotNull(message = "客户编码不可为空")
    @Length(max = 32, message = "客户编码最多包含32个字符")
    @ApiModelProperty("客户编码不可为空，否则验证不通过")
    private String customerCode;

    /**
     * 客户名称
     */
    @NotBlank(message = "客户名称不可为空")
    @Length(max = 200, message = "客户名称最多包含200个字符")
    private String customerName;

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 公司地址
     */
    @Length(max = 2000, message = "公司地址最多包含2000个字符")
    private String address;
    /**
     * 联系人
     */
    @Length(max = 100, message = "联系人最多包含100个字符")
    private String contactPerson;

    /**
     * 备注
     */
    @Length(max = 2000, message = "联系人最多包含2000个字符")
    private String remark;

    /**
     * 联系电话
     */
    @Length(max = 50, message = "联系电话最多包含50个字符")
    private String phone;
}
