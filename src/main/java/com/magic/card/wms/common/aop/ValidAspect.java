package com.magic.card.wms.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.magic.card.wms.common.model.ResponseData;
import com.magic.card.wms.common.model.enums.ResultEnum;

import lombok.extern.slf4j.Slf4j;

/***
 * 必输字段校验
 * @author PENGJIE
 * @date 2019年6月12日
 */
@Slf4j
@Component
@Aspect
public class ValidAspect {
	
	@Around("(execution(public * com.magic.card.wms.*.controller..*.*(..)) && args(..,bindingResult))")
	public Object doAround(ProceedingJoinPoint pjp,BindingResult bindingResult) throws Throwable {
		if(bindingResult.hasErrors()) {
			String msg = bindingResult.getFieldError().getDefaultMessage();
			log.error("Method name:{} and error={}",pjp.getSignature().getName(),msg);
			return ResponseData.error(ResultEnum.req_params_null.getCode(), msg);
		}else {
			return pjp.proceed();
		}
	}

}
