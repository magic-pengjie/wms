package com.magic.card.wms.config.express;

import com.magic.card.wms.baseset.service.IExpressNumberService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * com.magic.card.wms.config.express
 * 提取公用的功能
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/25/025 11:56
 * @since : 1.0.0
 */
public abstract class AbstractExpressProvider implements IExpressProvider {
    @Autowired
    @Getter
    private IExpressNumberService expressNumberService;

    public abstract String expressKey();
}
