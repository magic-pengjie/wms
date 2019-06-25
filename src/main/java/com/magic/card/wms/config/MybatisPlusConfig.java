package com.magic.card.wms.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * MybatisPlusConfig配置类
 * 
 * @author pengjie
 *
 */
@Configuration
@MapperScan({"com.magic.card.wms.user.mapper",
	"com.magic.card.wms.baseset.mapper",
	"com.magic.card.wms.warehousing.mapper",
	"com.magic.card.wms.common.mapper"})
@Slf4j
public class MybatisPlusConfig {

	/**
	 * Mybatis-Plus 执行效率插件
	 * @return
	 */
	@Bean
	public PerformanceInterceptor performanceInterceptor() {
		log.info("Mybatis-Plus：开启sql执行效率插件");
		PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
		performanceInterceptor.isFormat();
		return performanceInterceptor;
	}
	
	/**
	 * Mybatis-Plus 分页插件
	 * @return
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		log.info("Mybatis-Plus：开启PageHelper 的支持");
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setLocalPage(true);
		return paginationInterceptor;
	}
	
}
