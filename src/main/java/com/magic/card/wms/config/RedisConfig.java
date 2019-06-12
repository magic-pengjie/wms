package com.magic.card.wms.config;

import java.io.Serializable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置类
 * @author PENGJIE
 * @date 2019年6月12日
 */
@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<Serializable, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate<Serializable, Object> template = new RedisTemplate<Serializable, Object>();
		template.setConnectionFactory(redisConnectionFactory);
		template.afterPropertiesSet();
		template.setKeySerializer(new StringRedisSerializer());
		return template;
	}
	
}
