package com.magic.card.wms.common.model;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/***
 * 处理分页参数
 * @author PENGJIE
 * @date 2019年6月12日
 */
@Slf4j
public class PageInfoArgumentResolver implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return PageInfo.class.equals(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String body = (String) RequestContextHolder.getRequestAttributes().getAttribute(PageInfo.REQUEST_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
		PageInfo result = JSON.parseObject(body,PageInfo.class);
		if(null == result) {
			log.info("PageInfo is null,create new object..");
			result = new PageInfo();
		}
		return result;
	}

}
