package com.magic.card.wms.baseset.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;

/**
 * <p>
 * 客户信息表
 * </p>
 *
 * @author Mr.Zhang
 * @since 2019-06-18
 */
@Data
@TableName("wms_customer_base_info")
public class CustomerBaseInfo extends BasePo implements Serializable {

    private static final long serialVersionUID = -5112620722452290829L;

    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 品牌id
     */
    private String brandId;
    /**
     * 公司地址
     */
    private String address;
    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String phone;


}
