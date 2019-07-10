package com.magic.card.wms.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.StringUtils;

import com.thoughtworks.xstream.core.util.Base64Encoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Digest {
	
	/**
	 * 生成MD5base64编码
	 * @param str
	 * @return
	 */
	public static String Md5Base64(String str) {
		if(StringUtils.isEmpty(str)) {
			return null;
		}
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			Base64Encoder encode = new Base64Encoder();
			return encode.encode(md5.digest((str).getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			log.error("MessageDigest.getInstance error:{}",e);
		} catch (UnsupportedEncodingException e) {
			log.error("strin.getBytes error:{}",e);
		}
		return null;
	}
	/**
	 * utf-8 url编码
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String urlEncode(String str) throws UnsupportedEncodingException {
		return URLEncoder.encode(str, "UTF-8" );
	}

}
