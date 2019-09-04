package com.magic.card.wms.baseset.model.po;

import com.baomidou.mybatisplus.annotations.TableName;
import com.magic.card.wms.common.model.po.BasePo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * com.magic.card.wms.baseset.model.po
 * 快递单号
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/9/4 9:39
 * @since : 1.0.0
 */
@Data
@TableName("wms_express_number")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ExpressNumber extends BasePo {
    private static final long serialVersionUID = 3494883265379237815L;
    /**
     * 快递标识
     */
    private String expressKey;
    /**
     * 快递单号
     */
    private String expressNum;
    /**
     * 使用次数
     */
    private Integer useTimes;
}
