package com.magic.card.wms.common.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * com.magic.card.wms.common.security
 * 数据安全操作加锁
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/6/28 17:01
 * @since : 1.0.0
 */
@Slf4j
@Component
public class OperationLock {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 操作加锁
     * @param key
     * @param operationTime 系统当前时间 + 最长处理时间 ms
     * @return true(加锁成功) / false(加锁失败)
     */
    public Boolean lock(String key, String operationTime) {

        if (redisTemplate.opsForValue().setIfAbsent(key, "" + operationTime)) {
            return true;    // 成功获取锁
        }

        // 操作异常，没有释放锁处理
        String oldValue = redisTemplate.opsForValue().get(key);

        // 判断旧的加锁值是否已经超时
        if (StringUtils.isNotBlank(oldValue) && Long.valueOf(oldValue) < System.currentTimeMillis()) {
            // 并发处理拿锁
            String flagValue = redisTemplate.opsForValue().getAndSet(key, operationTime);

            if (StringUtils.isNotBlank(flagValue) && oldValue.equalsIgnoreCase(flagValue)) {
                return true;
            }

        }

        return false;
    }

    /**
     * 操作解锁
     * @param key
     * @param operationTime 系统当前时间 + 最长处理时间 ms
     */
    public void unLock(String key, String operationTime) {
        try{
            String value = redisTemplate.opsForValue().get(key);

            if (StringUtils.isNotBlank(value) && value.equalsIgnoreCase(operationTime)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("解锁产生异常--- key: {}, e: {}", key, e);
        }
    }
}
