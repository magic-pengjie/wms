package com.magic.card.wms.common.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志切面
 * 
 * @author PENGJIE
 * @date 2019年6月12日
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

	@Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
	public void apiOperation() {

	}

	@Around("apiOperation()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		StopWatch sw = new StopWatch();
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			String ip = getIpAddress(request);
			log.info("around start target = {},ip = {},args = {}", pjp.getTarget(), ip,pjp.getArgs());
			sw.start();
			Object proceed = pjp.proceed();
			return proceed;
		} catch (Throwable t) {
			log.error("around error:{}", t);
			throw new Throwable();
		} finally {
			sw.stop();
			log.info("request end cost :{}ms--{}", System.currentTimeMillis() - startTime,
					pjp.getSignature().getName());
		}

	}

	@Before("@annotation(api)")
	public void before(JoinPoint point, ApiOperation api) {
		log.info("before:{}", api.value());
	}

	@After("@annotation(api)")
	public void after(JoinPoint point, ApiOperation api) {
		log.info("after:{}", api.value());
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
