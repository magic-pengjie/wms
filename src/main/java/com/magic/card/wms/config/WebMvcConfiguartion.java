package com.magic.card.wms.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/***
 * springmvc配置
 * @author pengjie
 *
 */
@Configuration
public class WebMvcConfiguartion extends WebMvcConfigurationSupport{

	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> addArgumentResolvers) {
		addArgumentResolvers.add(null);
	}
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		
	}
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources");
		registry.addResourceHandler("webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars");
	}
}
