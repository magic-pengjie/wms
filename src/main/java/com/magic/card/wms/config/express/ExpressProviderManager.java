package com.magic.card.wms.config.express;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * com.magic.card.wms.config.express
 * 快递供应商管理器
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/25/025 11:32
 * @since : 1.0.0
 */
@Slf4j
@Component
public class ExpressProviderManager {
    @Autowired(required = false)
    private Map<String, IExpressProvider> expressProviders;

    /**
     *
     * @param providerKey sf(顺风)、ems(邮政)
     * @return
     */
    private IExpressProvider provider(String providerKey) {

        if (StringUtils.isBlank(providerKey)) return expressProviders.get("defaultExpressProvider");

        IExpressProvider expressProvider = null;

        switch (providerKey.toLowerCase()) {
            default: expressProvider = expressProviders.get("defaultExpressProvider");
        }

        return expressProvider;
    }


    /**
     * 使用快递单号
     *
     * @return
     */
    public String useExpressNo(String providerKey) {
        return provider(providerKey).useExpressNo();
    }

    /**
     * 销毁快递单号
     *
     * @param expressNo
     */
    public void destroyExpressNo(String providerKey, String expressNo) {
        provider(providerKey).destroyExpressNo(expressNo);
    }

    /**
     * 快递跟踪
     *
     * @param expressNos
     * @return
     */
    public List traceExpress(String providerKey,String... expressNos) {
        return provider(providerKey).traceExpress(expressNos);
    }
}
