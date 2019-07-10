package com.magic.card.wms;

import java.util.ArrayList;
import java.util.List;

import com.magic.card.wms.baseset.model.po.OrderCommodity;
import com.magic.card.wms.foreign.model.dto.OrderXml;
import com.magic.card.wms.foreign.model.dto.Persion;
import com.magic.card.wms.foreign.model.dto.RequestOrder;
import com.magic.card.wms.foreign.util.DateConverter;
import com.thoughtworks.xstream.XStream;

public class SendOrderTest {

	public static void main(String[] args) {
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		OrderXml xml = new OrderXml();
		xml.setData_digest("data_digest");
		xml.setLogistics_interface("logistics_interface");
		RequestOrder order = new RequestOrder();
		RequestOrder order1 = new RequestOrder();
		order1.setCustomerId("customerId1");
		order1.setEcCompanyId("ecCompanyId1");
		Persion r1 = new Persion();
		r1.setName("zhangsan");
		r1.setPhone("111111");
		order1.setReceiver(r1);
		Persion r2 = new Persion();
		r2.setName("lishi");
		r2.setPhone("222222");
		order1.setSender(r2);
		List<OrderCommodity> list = new ArrayList();
		OrderCommodity c1 = new OrderCommodity();
		c1.setBarCode("111111");
		OrderCommodity c2 = new OrderCommodity();
		c2.setBarCode("222222");
		list.add(c1);
		list.add(c2);
		order1.setOrderCommodity(list);
		xml.setOrder(order1);
		xstream.registerConverter(new DateConverter());
		System.out.println(xstream.toXML(xml));

	}
}
