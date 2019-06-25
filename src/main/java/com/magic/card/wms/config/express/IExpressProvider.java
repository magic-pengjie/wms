package com.magic.card.wms.config.express;

import java.util.List;

/**
 * com.magic.card.wms.config.express
 * 快递供应商实现接口
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/25/025 11:35
 * @since : 1.0.0
 */
public interface IExpressProvider {

    /**
     * 使用快递单号
     * @return
     */
    String useExpressNo();

    /**
     * 销毁快递单号
     * @param expressNo
     */
    void destroyExpressNo(String expressNo);

    /**
     * 快递跟踪
     * @param expressNos
     * @return
     */
    List traceExpress(String... expressNos);
}
