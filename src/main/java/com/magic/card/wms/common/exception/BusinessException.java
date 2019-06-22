package com.magic.card.wms.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/***
 * 通用业务异常类
 * @author PENGJIE
 * @date 2019年6月13日
 */
public class BusinessException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 4324157153627074882L;
	
	@Getter
	@Setter
	private int errCode;
	@Getter
	@Setter
	private String errMsg;

	public BusinessException() {
		super();
	}
	
	public  BusinessException(int errCode,String errMsg) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
		
	}

}
