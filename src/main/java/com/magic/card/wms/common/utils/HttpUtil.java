package com.magic.card.wms.common.utils;

import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * http请求工具类
 * @author PENGJIE
 * @date 2019年7月12日
 */
@Slf4j
public class HttpUtil {

	public static String json_format = "application/json";
	public static String encode_format = "UTF-8";
	
	/**
	 * get 请求
	 * @param url
	 * @return
	 */
	public static String restGet(String url) {
		long startTime = System.currentTimeMillis();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.add("Accept",json_format);
		HttpEntity<String> entity = new HttpEntity<String>(header);
		String data = restTemplate.exchange(url, HttpMethod.GET,entity,String.class).getBody();
		log.info("reqest url:{},cost:{} ms",url,(System.currentTimeMillis()-startTime));
		return data;
	}
	/**
	 * post 请求
	 * @param url  路径
	 * @param params 参数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String restPost(String url,Object params) throws UnsupportedEncodingException {
		long startTime = System.currentTimeMillis();
		String reqJson = JSONObject.toJSONString(params);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		MediaType type = MediaType.APPLICATION_JSON_UTF8;
		header.setContentType(type);
		header.add("Accept",json_format);
		header.setContentLength(reqJson.getBytes(encode_format).length);
		HttpEntity<String> entity = new HttpEntity<String>(reqJson,header);
		String data = restTemplate.postForObject(url, entity, String.class);
		log.info("reqest url:{},cost:{} ms",url,(System.currentTimeMillis()-startTime));
		return data;
	}
	
}
