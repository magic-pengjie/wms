package com.magic.card.wms.common.security;

import com.magic.card.wms.common.exception.OperationException;
import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.model.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * com.magic.card.wms.common.security
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/1 10:17
 * @since : 1.0.0
 */
@Slf4j
@Component
public class CommodityStockSecurity {
    /**
     * 商品库存基本KEY值
     */
    public static final String STOCK_ROOT_KEY = "WMS_Commodity_Stock";
    /**
     * 操作执行超时时间 默认 2000ms
     */
    public static final Long EXECUTE_OUT_TIME = 2L*1000;

    @Autowired
    private OperationLock operationLock;

    /**
     * 获取操作TOKEN
     * @param customerCode
     * @param commodityCode
     * @return
     */
    public String accessToken(String customerCode, String commodityCode) {
        String key = StringUtils.join(STOCK_ROOT_KEY, customerCode, commodityCode);
        Long outTime = System.currentTimeMillis() + EXECUTE_OUT_TIME;

        if (operationLock.lock(key, "" + outTime)) {
            return StringUtils.joinWith(Constants.DEFAULT_JOINT_MARK, key, outTime);
        }

        return null;
    }

    /**
     * 带有延迟时间获取操作TOKEN
     * @param customerCode
     * @param commodityCode
     * @return
     */
    public String accessLazyToken(String customerCode, String commodityCode) {
        return accessLazyToken(customerCode, commodityCode, EXECUTE_OUT_TIME);
    }

    /**
     * 带有延迟时间获取操作TOKEN
     * @param customerCode
     * @param commodityCode
     * @param lazyTimes 延迟时间（）
     * @return
     */
    public String accessLazyToken(String customerCode, String commodityCode, Long lazyTimes) {
        Long outTime = System.currentTimeMillis() + lazyTimes;

        while (outTime >= System.currentTimeMillis()) {
            String accessToke = accessToken(customerCode, commodityCode);

            if (StringUtils.isNotBlank(accessToke)) {
                return accessToke;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        throw OperationException.customException(ResultEnum.system_busy);
    }

    /**
     * 销毁TOKEN
     * @param destroyToken
     */
    public void destroyToken(String destroyToken) {
        String[] tokens = StringUtils.split(destroyToken, Constants.DEFAULT_JOINT_MARK);

        if (tokens != null && tokens.length == 2) {
            operationLock.unLock(tokens[0], tokens[1]);
        }

    }
}
