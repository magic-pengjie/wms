package com.magic.card.wms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 * 
 * @author pengjie
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${app.version}")
	private String version;

	/**
	 * api生成扫描地址
	 * 
	 * @return
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(SwaggerConfig.basePackage("com.magic.card.wms.user.controller"))
				.paths(PathSelectors.any()).build();

	}

	/**
	 * 描述信息
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("库存管理系统").description("库存管理系统接口文档")
				.termsOfServiceUrl("http://localhost:8080/wms/swagger-ui.html").version(version).build();
	}
	
	public static Predicate<RequestHandler> basePackage(final String basePackage){
		return new Predicate<RequestHandler>() {

			@Override
			public boolean apply(RequestHandler input) {
				return declaringClass(input).transform(handlerPackage(basePackage)).or(true);
			}
			
		};
	}
	
	/***
	 * 处理包路径配置规则，多个路径以逗号隔开
	 * @param basePackage
	 * @return
	 */
	private static Function<Class<?>,Boolean> handlerPackage(final String basePackage){
		return new Function<Class<?>,Boolean>(){

			@Override
			public Boolean apply(Class<?> input) {
				for (String s : basePackage.split(",")) {
					if(input.getPackage().getName().startsWith(s)) {
						return true;
					}
				}
				return false;
			}
			
		};
	}

	private static Optional<? extends Class<?>> declaringClass(RequestHandler input){
		return Optional.fromNullable(input.declaringClass());
	}
	
}
