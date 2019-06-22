package com.magic.card.wms.baseset.model.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-18
 */
@Data
@TableName("wms_brand_info")
public class BrandInfo extends BasePo implements Serializable {

    private static final long serialVersionUID = 8449935019393752005L;

    /**
     * 品牌名称
     */
    private String name;
}
