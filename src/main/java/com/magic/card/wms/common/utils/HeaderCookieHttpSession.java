package com.magic.card.wms.common.utils;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.util.StringUtils;

public class HeaderCookieHttpSession implements HttpSessionIdResolver {

	private static final String COOKIE_AUTH_TOKEN = "Wms-Token";
	
	private final String headerName ;
	
	public static HeaderCookieHttpSession xAuthToken() {
		return new HeaderCookieHttpSession(COOKIE_AUTH_TOKEN);
	}
	
	public HeaderCookieHttpSession(String headerName) {
		if(null == headerName) {
			throw new  IllegalArgumentException("请求头Token不能为空");
		}
		this.headerName = headerName;
	}
	
	private String getTokenFromCookie(HttpServletRequest request, String headerValue) {
		Cookie[] cookies = request.getCookies();
		if(!StringUtils.isEmpty(cookies)) {
			for (Cookie cookie : cookies) {
				headerValue = cookie.getValue();
				break;
			}
		}
		return headerValue;
	}
	
	
	@Override
	public List<String> resolveSessionIds(HttpServletRequest request) {
		
		String headerValue = request.getHeader(this.headerName);
		if(StringUtils.isEmpty(headerValue)) {
			if("/user/login".equals(request.getRequestURI())) {
				return Collections.emptyList();
			}
			if(COOKIE_AUTH_TOKEN.equals(this.headerName)) {
				headerValue = getTokenFromCookie(request, headerValue);
			}
		}
		return (headerValue!=null) ? Collections.singletonList(headerValue) : Collections.emptyList();
	}

	@Override
	public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
		response.setHeader(this.headerName, sessionId);
	}

	@Override
	public void expireSession(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader(this.headerName, "");
	}

}
