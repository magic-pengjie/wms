package com.magic.card.wms.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据库连接池配置类
 * 
 * @author pengjie
 *
 */
@Configuration
@Slf4j
public class DataSourceConfig {

	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public DataSource dataSource(DataSourceProperties properties) {
		log.info("数据库连接池创建中.......");
		
		DataSource	dataSource = DataSourceBuilder.create(properties.getClassLoader())
					.type(HikariDataSource.class)
					.driverClassName(properties.getDriverClassName())
					.url(properties.determineUrl())
					.username(properties.determineUsername())
					.password(properties.determinePassword())
					.build();
		log.info("数据库连接池创建完成.......");
		return dataSource;
	}

}
