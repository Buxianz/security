package com.rbi.security.web.config;

import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "authFilter", urlPatterns = "/*")
public class CrossFilter implements Filter {
    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "accept,content-type,accessToken");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST,DELETE,PUT");
        response.setContentType("application/json; charset=utf-8");//这两行设置输出的中文不会是乱码
        response.setCharacterEncoding("UTF-8");
        if(!HttpMethod.OPTIONS.name().equals(httpRequest.getMethod())){
            StringBuffer requestUrl=httpRequest.getRequestURL();
            String method = httpRequest.getMethod();
            System.out.println("requestUrl"+requestUrl + "----method:" + method);
            chain.doFilter(request, httpServletResponse);
        }
    }


    public void init(FilterConfig arg0) throws ServletException {

    }
}
