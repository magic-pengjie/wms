package com.magic.card.wms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;
import com.magic.card.wms.baseset.model.vo.LogisticsReqHeader;
import com.magic.card.wms.baseset.service.ILogisticsTrackingInfoService;
import com.magic.card.wms.baseset.service.IMailPickingService;
import com.magic.card.wms.common.utils.Digest;
import com.magic.card.wms.common.utils.HttpUtil;
import com.thoughtworks.xstream.core.util.Base64Encoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendOrderTest extends MagicWmsApplicationTests{
	@Autowired
	private IMailPickingService mailPickingService;
	@Autowired
	private ILogisticsTrackingInfoService logisticsTrackingInfoService;
	@Value("${post.query.trackingInfo.url}")
	private String postUrl;
	@Value("${post.query.trackingInfo.secret_key}")
	private String secretKey;
	//@Test
	public void send() throws UnsupportedEncodingException {
		mailPickingService.sendOrder("201908031332161329", null);
	}
	//@Test
	public void runLogisticsInfo() throws UnsupportedEncodingException {
		logisticsTrackingInfoService.runLogisticsInfo();
	}
	@Test
	public void logisticsInfo() throws Exception {
		LogisticsReqHeader header = new LogisticsReqHeader();
		Map map = new HashMap();
		map.put("traceNo", "9891096679709");
		String msgBody = JSONObject.toJSONString(map);
		log.info("msgBody:{}",msgBody);
		log.info("dataDigest:{}",msgBody+secretKey);
		String dataDigest =Digest.md5_32Encrypt(msgBody+secretKey);
		StringBuffer sb = new StringBuffer();
		sb.append("sendID=").append(header.getSendID())
		  .append("&proviceNo=").append(header.getProviceNo())
		  .append("&msgKind=").append(header.getMsgKind())
		  .append("&serialNo=").append(header.getSendID()+header.getSendDate())
		  .append("&sendDate=").append(header.getSendDate())
		  .append("&receiveID=").append(header.getReceiveID())
		  .append("&dataType=").append(header.getDataType())
		  .append("&dataDigest=").append(dataDigest)
		  .append("&msgBody=").append(Digest.urlEncode(msgBody));
		log.info("reuqest url:{}",sb.toString());
		String resultStr = HttpUtil.httpPost(postUrl,sb.toString());
		log.info("response result:{}",resultStr);
	}
	public static void main(String[] args) throws Exception {
		//System.out.println(URLDecoder.decode("%7B%22traceNo%22%3A%229891096679709%22%7D"));
		String str = "{\"traceNo\":\"9891096679709\"}FA0DBD9545C2A08F0540011A675C09D8";
		String s = Digest.md5_32Encrypt(str);
		log.info("dataDigest:{}",s);
		
		Base64Encoder encode = new Base64Encoder();
		String skey = "ZGVkYzJiODI4ODhmYTMyYTQ2NDUzNjQxY2RjZWFhMzk=";
		log.info("解密后:{}",new String(encode.decode(skey),"utf-8"));
		
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
        return hexValue.toString();
    }

}
