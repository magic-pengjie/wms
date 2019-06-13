package com.magic.card.wms.common.exception;

import lombok.Getter;
import lombok.Setter;

/***
 * 通用业务异常类
 * @author PENGJIE
 * @date 2019年6月13日
 */
public class BusinessException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private int errCode;
	@Getter
	@Setter
	private String errMsg;
	
	public  BusinessException(int errCode,String errMsg) {
		this.errCode = errCode;
		this.errCode = errCode;
		
	}

}
