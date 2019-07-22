package com.magic.card.wms.config;

import com.magic.card.wms.common.model.enums.Constants;
import com.magic.card.wms.common.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * com.magic.card.wms.config
 * 基础控制器
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/7/22 10:08
 * @since : 1.0.0
 */
public class WmsBaseController {
    public static final String DEFAULT_USER = Constants.DEFAULT_USER;
    @Autowired
    public HttpServletRequest request;
    @Autowired
    public HttpSession session;
    @Autowired
    public WebUtil webUtil;

    /**
     * 获取当前线程的 HttpServletResponse 对象
     * @return
     */
    public HttpServletResponse response() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getResponse();
    }
}
