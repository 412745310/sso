package com.chelsea.sso.security.cas.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 自定义登出成功处理类
 * 
 * @author shevchenko
 *
 */
@Component
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {
    
    private static final Logger LOG = LoggerFactory.getLogger(DefaultLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Authentication authentication) throws IOException, ServletException {
        LOG.info("logout success.........");
    }
    
}
