package com.magic.card.wms;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.magic.card.wms.baseset.service.ILogisticsTrackingInfoService;
import com.magic.card.wms.baseset.service.IMailPickingService;

public class SendOrderTest extends MagicWmsApplicationTests{
	@Autowired
	private IMailPickingService mailPickingService;
	@Autowired
	private ILogisticsTrackingInfoService logisticsTrackingInfoService;
	//@Test
	public void send() throws UnsupportedEncodingException {
		mailPickingService.sendOrder("201908031332161329", null);
	}
	@Test
	public void runLogisticsInfo() throws UnsupportedEncodingException {
		logisticsTrackingInfoService.runLogisticsInfo();
	}
}
