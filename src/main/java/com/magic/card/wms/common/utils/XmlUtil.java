package com.magic.card.wms.common.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * xml工具类
 * @author PENGJIE
 * @date 2019年7月12日
 */
public class XmlUtil {

	/**
	 * 解析xml
	 * @param object 目标对象
	 * @param xml xml字符串
	 * @return
	 */
	public static Object parseXml(Object object,String xml) {
		xml = xml.substring(xml.indexOf(">")+1);
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(object.getClass());
		object = xstream.fromXML(xml);
		return object;
	}
	
	/**
	 * 生成xml字符串
	 * @param object
	 * @return
	 */
	public static String toXml(Object object) {
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		//xstream.registerConverter(new DateConverter());
		String xml = xstream.toXML(object);
		return xml;
	}
}
