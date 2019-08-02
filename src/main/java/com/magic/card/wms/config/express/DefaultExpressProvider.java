package com.magic.card.wms.config.express;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * com.magic.card.wms.config.express
 * 系统暂时默认提供快递提供(邮政小包)
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/25/025 11:45
 * @since : 1.0.0
 */
@Slf4j
@Component("defaultExpressProvider")
public class DefaultExpressProvider implements IExpressProvider {
    /**
     * 使用快递单号
     *
     * @return
     */
    @Override
    public String useExpressNo() {
        return "" + System.currentTimeMillis();
    }

    /**
     * 销毁快递单号
     *
     * @param expressNo
     */
    @Override
    public void destroyExpressNo(String expressNo) {
        log.info("默认快递提供商并未提供单号销毁功能： 单号：{}", expressNo);
    }

    /**
     * 快递跟踪
     *
     * @param expressNos
     * @return
     */
    @Override
    public List traceExpress(String... expressNos) {
        log.info("默认快递提供商并未提供快递跟踪功能： 单号：{}", expressNos);
        return Lists.newArrayList(expressNos);
    }
}
