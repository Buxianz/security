package com.rbi.security.web.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class MyShiroSessionManager extends DefaultWebSessionManager {
    private static final Logger log = LoggerFactory.getLogger(MyShiroSessionManager.class);
    private String authorization = "accessToken";

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        return this.getReferencedSessionId(request, response);
    }

    /**
<<<<<<< HEAD
     * 获取sessionId从请求中
     *
=======
     *
     *获取sessionId从请求中
>>>>>>> 82120b910539b79753e116f7a5134c27630b94f6
     * @param request
     * @param response
     * @return
     */
    private Serializable getReferencedSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpRequest=(HttpServletRequest) request;
        StringBuffer requestUrl=httpRequest.getRequestURL();
        String method = httpRequest.getMethod();
        System.out.println("requestUrl"+requestUrl + "----method:" + method);
        String id = this.getSessionIdCookieValue(request, response);
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "cookie");
        } else {
            id = this.getUriPathSegmentParamValue(request, "JSESSIONID");
            if (id == null) {
                // 获取请求头中的session
                //id = this.getUriPathSegmentParamValue(request, this.authorization);

                id = WebUtils.toHttp(request).getHeader(this.authorization);
                if (id == null) {
                    String name = this.getSessionIdName();
                    id = request.getParameter(name);
                    if (id == null) {
                        id = request.getParameter(name.toLowerCase());
                    }
                }
            }
            if (id != null) {
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "url");
            }
        }
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        }

        return id;
    }

    private String getSessionIdCookieValue(ServletRequest request, ServletResponse response) {
        if (!isSessionIdCookieEnabled()) {
            log.debug("Session ID cookie is disabled - session id will not be acquired from a request cookie.");
            return null;
        }
        if (!(request instanceof HttpServletRequest)) {
            log.debug("Current request is not an HttpServletRequest - cannot get session ID cookie.  Returning null.");
            return null;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return getSessionIdCookie().readValue(httpRequest, WebUtils.toHttp(response));
    }

    private String getUriPathSegmentParamValue(ServletRequest servletRequest, String paramName) {
        if (!(servletRequest instanceof HttpServletRequest)) {
            return null;
        }
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String uri = request.getRequestURI();
        if (uri == null) {
            return null;
        }
        int queryStartIndex = uri.indexOf('?');
        if (queryStartIndex >= 0) {
            uri = uri.substring(0, queryStartIndex);
        }
        int index = uri.indexOf(';');
        if (index < 0) {
            return null;
        }
        final String TOKEN = paramName + "=";
        uri = uri.substring(index+1);
        index = uri.lastIndexOf(TOKEN);
        if (index < 0) {
            return null;
        }
        uri = uri.substring(index + TOKEN.length());
        index = uri.indexOf(';');
        if(index >= 0) {
            uri = uri.substring(0, index);
        }
        return uri;
    }

    private String getSessionIdName() {
        String name = this.getSessionIdCookie() != null ? this.getSessionIdCookie().getName() : null;
        if (name == null) {
            name = ShiroHttpSession.DEFAULT_SESSION_ID_NAME;
        }
        return name;
    }
}
