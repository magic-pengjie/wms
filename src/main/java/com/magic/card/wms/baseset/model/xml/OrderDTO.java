package com.magic.card.wms.baseset.model.xml;

import javax.xml.bind.annotation.XmlType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/***
 * 邮政数据格式
 * @author PENGJIE
 * @date 2019年7月5日
 */
@Data
public class OrderDTO {

	/**
	 * 消息内容
	 */
	private String logistics_interface;
	/**
	 * 消息签名
	 */
	private String data_digest;
	/**
	 * 消息类型
	 */
	private String msg_type;
	/**
	 * 电商标识
	 */
	private String ecCompanyId ;
	
	private RequestOrderXml order;
}
