package com.chelsea.sso.security.cas.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 自定义过滤器
 */
public class ValidationCodeFilter extends OncePerRequestFilter {
    
    private static final Logger LOG = LoggerFactory.getLogger(ValidationCodeFilter.class);
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        LOG.info("invoke ValidationCodeFilter.doFilterInternal().......");
        String url = request.getRequestURI();
        String matchUrl = request.getContextPath() + "/login";
        if (matchUrl.equals(url) && StringUtils.startsWithIgnoreCase("post", request.getMethod() )) {
            // 模拟图形验证码校验
            String code = request.getParameter("verifyCode");
            if (!"1234".equals(code)) {
                request.getSession().setAttribute("message", "verifyCode error!");
                response.sendRedirect(request.getContextPath() + "/loginError");
                return;
            }
        }
        // 继续执行下一步
        filterChain.doFilter(request, response);
    }

}
