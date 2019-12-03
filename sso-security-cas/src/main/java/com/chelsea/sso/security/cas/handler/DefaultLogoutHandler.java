package com.chelsea.sso.security.cas.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 * 自定义登出处理类
 * 
 * @author shevchenko
 *
 */
@Component
public class DefaultLogoutHandler implements LogoutHandler {
    
    private static final Logger LOG = LoggerFactory.getLogger(DefaultLogoutHandler.class);

    @Override
    public void logout(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse,
            Authentication authentication) {
        LOG.info("logout .........");
    }

}
