package com.magic.card.wms.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.magic.card.wms.common.model.PageInfoArgumentResolver;
import com.magic.card.wms.common.model.PageInfoHttpMessgeConverter;

/***
 * springmvc配置
 * @author pengjie
 *
 */
@Configuration
public class WebMvcConfiguartion extends WebMvcConfigurationSupport{

	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> addArgumentResolvers) {
		addArgumentResolvers.add(new PageInfoArgumentResolver());
	}
	
	/**
	 * 消息内容转换配置
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		PageInfoHttpMessgeConverter c = new PageInfoHttpMessgeConverter();
		List<MediaType> supportMediaTypes = new ArrayList<>();
		supportMediaTypes.add(MediaType.APPLICATION_JSON);
		supportMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		supportMediaTypes.add(MediaType.TEXT_PLAIN);
		supportMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
		c.setSupportedMediaTypes(supportMediaTypes);
		
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setDateFormat("yyyy-MM-dd");
		c.setFastJsonConfig(fastJsonConfig);
		
		//解决中文乱码问题
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		converter.setDefaultCharset(Charset.forName("UTF-8"));
		List<MediaType> mediaTypeList = new ArrayList<MediaType>();
		mediaTypeList.add(MediaType.APPLICATION_JSON);
		converter.setSupportedMediaTypes(mediaTypeList);
		
		converters.add(c);
	}
	
	/**
	 * 静态资源过滤
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	/**
	 * 文件上传配置
	 * @return
	 */
	@Bean(name="multipartResolver")
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setResolveLazily(true);
		resolver.setMaxInMemorySize(20*1024*1024);
		resolver.setMaxUploadSize(20*1024*1024);
		return resolver;
	}
}
