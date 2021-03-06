package com.magic.card.wms.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.magic.card.wms.common.exception.BusinessException;
import com.magic.card.wms.common.model.enums.SessionKeyConstants;
import com.magic.card.wms.common.model.po.UserSessionUo;

import io.netty.util.internal.StringUtil;

@Component
public class WebUtil {
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@Autowired
	private HttpSession session;
	
	public void clearSession() {
		session.invalidate();
	}
	
	public <T> void setSession(String key, T value) {
		session.setAttribute(key, value);
	}
	
	public Object getSession(String key) {
		return session.getAttribute(key);
	}
	
	public UserSessionUo getUserSession() throws BusinessException {
		Object user = getSession(SessionKeyConstants.USER_INFO);
		if(null == user) {
			throw new BusinessException(400001, "用户未登录,或登录超时");
		}
		return (UserSessionUo)user;
	}
	
	public void setUserSession(UserSessionUo user) {
		setSession(SessionKeyConstants.USER_INFO, user);
	}
	
	public String getHeaderValue(String name) {
		if(StringUtil.isNullOrEmpty(name)) {
			return null;
		}
		return httpServletRequest.getHeader(name);
	}

}







