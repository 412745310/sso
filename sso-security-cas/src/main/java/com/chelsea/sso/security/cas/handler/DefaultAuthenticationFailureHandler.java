package com.chelsea.sso.security.cas.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * 自定义的登陆失败处理
 * 
 * @author shevchenko
 *
 */
@Component
public class DefaultAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        LOGGER.info("login in failure : " + exception.getMessage());
        request.setAttribute("message", "login in failure");
        response.sendRedirect(request.getContextPath() + "/loginError");
    }

}
