package com.magic.card.wms.baseset.model.xml;

import lombok.Data;

/***
 * 收件人
 * @author PENGJIE
 * @date 2019年7月5日
 */
@Data
public class PersionXml {
	/**
	 * 用户姓名
	 */
	private String name;
	/**
	 * 用户邮编
	 */
	private String postCode;
	/**
	 * 用户电话
	 */
	private String phone;
	/**
	 * 用户移动电话手机和电话两者必需提供一个
	 */
	private String mobile;
	/**
	 * 用户所在省
	 */
	private String prov;
	/**
	 * 用户所在市县（区），市区中间用“,”分隔
	 */
	private String city;
	/**
	 * 用户详细地址
	 */
	private String address;
}
