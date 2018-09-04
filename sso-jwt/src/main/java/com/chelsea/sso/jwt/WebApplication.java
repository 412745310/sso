package com.chelsea.sso.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.chelsea.sso.jwt.filter.JwtFilter;

@EnableAutoConfiguration
@ComponentScan
@Configuration
public class WebApplication {

    // 过滤器
    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

    public static void main(final String[] args) throws Exception {
        SpringApplication.run(WebApplication.class, args);
    }

}
