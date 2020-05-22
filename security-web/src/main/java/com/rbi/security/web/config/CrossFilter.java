package com.rbi.security.web.config;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "authFilter", urlPatterns = "/*")
//测试好像这个参数不生效，实际生效的是Filter扫描到的顺序（所以起名很重要）
public class CrossFilter implements Filter {
    public void destroy() {

    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "accept,content-type,accessToken,access-control-allow-origin");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST,DELETE,PUT");
        response.setContentType("application/json; charset=utf-8");//这两行设置输出的中文不会是乱码
        response.setCharacterEncoding("UTF-8");
        if(!HttpMethod.OPTIONS.name().equals(httpRequest.getMethod())){
            chain.doFilter(request, httpServletResponse);
        }
    }
    public void init(FilterConfig arg0) throws ServletException {

    }
}
