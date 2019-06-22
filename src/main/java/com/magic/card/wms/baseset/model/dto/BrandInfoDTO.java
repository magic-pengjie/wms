package com.magic.card.wms.baseset.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * com.magic.card.wms.baseset.model.dto
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/18/018 15:19
 * @since : 1.0.0
 */
@Data
public class BrandInfoDTO implements Serializable {
    private static final long serialVersionUID = 8000506748152916937L;

    private Long id;

    /**
     * 品牌名称
     */
    @NotNull(message = "品牌名称不可为空")
    @Length(max = 200, message = "品牌名称最多包含200个字符")
    private String name;

    private String remark;

}
