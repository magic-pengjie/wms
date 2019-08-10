package com.magic.card.wms.common.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

	public static String json_format = "application/x-www-form-urlencoded; charset=UTF-8";
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
		MediaType type = MediaType.APPLICATION_FORM_URLENCODED;
		header.setContentType(type);
		header.add("Accept",json_format);
		header.setContentLength(reqJson.getBytes(encode_format).length);
		HttpEntity<String> entity = new HttpEntity<String>(reqJson,header);
		String data = restTemplate.postForObject(url, entity, String.class);
		log.info("reqest url:{},cost:{} ms",url,(System.currentTimeMillis()-startTime));
		return data;
	}
	
	/***
	 * post 请求
	 * @param url 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String httpPost(String url,String data){
		try {
			    PostMethod  postMethod = new PostMethod(url) ;
			    postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8") ;
			    postMethod.setRequestBody(data);
			    HttpClient httpClient = new HttpClient();
			    int response = httpClient.executeMethod(postMethod); // 执行POST方法
			    log.info("response :{}",response);
			    String result = postMethod.getResponseBodyAsString() ;
			    return result;
			} catch (Exception e) {
			    log.info("请求异常"+e.getMessage(),e);
			    throw new RuntimeException(e.getMessage());
			}
			
	}
	
}
