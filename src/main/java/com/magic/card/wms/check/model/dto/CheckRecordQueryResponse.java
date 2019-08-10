package com.magic.card.wms.check.model.dto;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.check.model.po.CheckRecord;
import com.magic.card.wms.common.model.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *	 盘点记录 查询返回实体
 * </p>
 *
 * @author zhouhao
 * @since 2019-06-26
 */
@Data
public class CheckRecordQueryResponse extends CheckRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商家名称
     */
    private String customerName;
    /**
     * sku编码
     */
    private String skuCode;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 商品编码
     */
    private String barCode;
    /**
     * 规格
     */
    private String spec;
    /**
     * 型号
     */
    private String modelNo;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CheckRecordQueryResponse{");
        sb.append(", customerName='").append(customerName).append('\'');
        sb.append(", skuCode='").append(skuCode).append('\'');
        sb.append(", skuName='").append(skuName).append('\'');
        sb.append(", barCode='").append(barCode).append('\'');
        sb.append(", spec='").append(spec).append('\'');
        sb.append(", modelNo='").append(modelNo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
