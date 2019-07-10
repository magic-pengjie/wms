package com.magic.card.wms.foreign.model.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/***
 * 邮政数据格式
 * @author PENGJIE
 * @date 2019年7月5日
 */
@XStreamAlias("orderXml")
@XmlType(propOrder ="logistics_interface,data_digest,msg_type,ecCompanyId")
@Data
public class OrderXml {

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
	
	private RequestOrder order;
}
