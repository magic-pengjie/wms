package com.magic.card.wms.baseset.model.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.magic.card.wms.baseset.model.dto.order
 * 出库订单包裹（记录快递单号）
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/9/2 10:16
 * @since : 1.0.0
 */
@Data
public class OutboundOrderPackageDTO implements Serializable {
    private static final long serialVersionUID = 8775203243817277092L;
    private String mailNO;
    /**
     * 订单包裹数据
     */
    private List<OrderCommodityDTO> commodities;

}
