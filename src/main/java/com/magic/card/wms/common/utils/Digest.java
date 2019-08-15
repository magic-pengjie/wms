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
			return base64(md5.digest((str).getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			log.error("MessageDigest.getInstance error:{}",e);
		} catch (UnsupportedEncodingException e) {
			log.error("strin.getBytes error:{}",e);
		}
		return null;
	}
	
	 /*** 
     * MD5加密 生成32位md5码
     * @param 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5_32Encrypt(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
 
        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return base64(hexValue.toString().getBytes("UTF-8"));
    }
    /**
     * 进行Base64编码
     * @param bytes
     * @return
     */
    public static String base64(byte[] bytes ) {
    	Base64Encoder encode = new Base64Encoder();
		return encode.encode(bytes);
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
