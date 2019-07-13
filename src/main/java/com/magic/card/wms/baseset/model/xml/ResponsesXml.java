package com.magic.card.wms.baseset.model.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * xml报文同步应答返回
 * @author PENGJIE
 * @date 2019年7月12日
 */
@XStreamAlias("responses")
@Data
public class ResponsesXml {

	private String logisticProviderID;
	
	@XStreamAlias("responseItems")
	//@XStreamImplicit(itemFieldName = "response")
	private List<Response> responseItems;
	
	
}
