package com.magic.card.wms;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.magic.card.wms.baseset.model.xml.ResponsesXml;
import com.magic.card.wms.baseset.service.IMailPickingService;
import com.magic.card.wms.common.utils.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SendOrderTest extends MagicWmsApplicationTests{
	@Autowired
	private IMailPickingService mailPickingService;
	@Test
	public void send() throws UnsupportedEncodingException {
		mailPickingService.sendOrder("1562748928230", null);
	}
	//@Test
	public void revice() {
		String xml = "<responses>" + 
		"<logisticProviderID>POSTB</logisticProviderID>" + 
		"<responseItems>" + 
		"<response>" + 
		"<txLogisticID>LP05082300225709000</txLogisticID>" + 
		"<success>true</success>" + 
		"</response>" + 
		"<response>" + 
		"<txLogisticID>LP05082300225709001</txLogisticID>" + 
		"<success>false</success>" + 
		"</response>" + 
		"</responseItems>" + 
		"</responses>";
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(ResponsesXml.class);
		ResponsesXml r = new ResponsesXml();
		//ResponsesXml r = (ResponsesXml) xstream.fromXML(xml);
		r = (ResponsesXml) XmlUtil.parseXml(r,xml);
		System.out.println("ResponsesXml:"+r);
		System.out.println("success:"+r.getResponseItems().get(0).getSuccess());
		System.out.println("success:"+r.getResponseItems().get(1).getSuccess());
	}
	
}
