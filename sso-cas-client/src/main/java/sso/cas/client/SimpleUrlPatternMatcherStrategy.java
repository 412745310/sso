package sso.cas.client;

import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;

/**
 * 自定义路径鉴权类
 * 
 * @author shevchenko
 *
 */
public class SimpleUrlPatternMatcherStrategy implements UrlPatternMatcherStrategy {

    @Override
    public boolean matches(String url) {
        return url.contains("hello.jsp");
    }

    @Override
    public void setPattern(String pattern) {

    }

}
