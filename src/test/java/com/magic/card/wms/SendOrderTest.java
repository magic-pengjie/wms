package com.magic.card.wms;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.magic.card.wms.baseset.service.IMailPickingService;

public class SendOrderTest extends MagicWmsApplicationTests{
	@Autowired
	private IMailPickingService mailPickingService;
	@Test
	public void send() throws UnsupportedEncodingException {
		mailPickingService.sendOrder("1562748928230", null);
	}

}
