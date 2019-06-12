package com.magic.card.wms.common.model;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/***
 * 识别PageInfo参数
 * @author PENGJIE
 * @date 2019年6月12日
 */
public class PageInfoHttpMessgeConverter extends FastJsonHttpMessageConverter {

	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage ) throws IOException, HttpMessageNotReadableException {
		return readType(getType(type, contextClass), inputMessage);
	}
	
	@Override
    protected Object readInternal(Class<? extends Object> clazz,HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return readType(getType(clazz, null), inputMessage);
    }
	
	private Object readType(Type type, HttpInputMessage inputMessage) {

        try {
            InputStream in = inputMessage.getBody();
            String body = IOUtils.toString(in, Charset.forName("UTF-8"));
            if(type.getTypeName().contains("List")) {
            	return JSON.parseObject(body,type,getFastJsonConfig().getFeatures());
            }
            PageInfo pageInfo = JSON.parseObject(body,PageInfo.class,getFastJsonConfig().getFeatures());
            if(null != pageInfo) {
            	RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            	requestAttributes.setAttribute(PageInfo.REQUEST_ATTRIBUTE, JSON.toJSONString(pageInfo), RequestAttributes.SCOPE_REQUEST);
            }
            return JSON.parseObject(body,type,getFastJsonConfig().getFeatures());
        } catch (JSONException ex) {
            throw new HttpMessageNotReadableException("JSON parse error: " + ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new HttpMessageNotReadableException("I/O error while reading input message", ex);
        }
    }
}
