package com.chelsea.sso.security.cas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class SecurtyConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private AuthenticationSuccessHandler defaultAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler defaultAuthenticationFailureHandler;

    /**
     * 自定义配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().formLogin() // 基于Form表单登录验证
                .loginPage("/login") // 自定义登录页面
                .defaultSuccessUrl("/main") // 自定义登录成功页面
                .failureUrl("/loginError") // 自定义登录失败界面
                .usernameParameter("name") // 自定义登录用户名参数名
                .passwordParameter("password") // 自定义登录密码参数名
//                .successHandler(defaultAuthenticationSuccessHandler) // 自定义认证成功处理类（此设置会让defaultSuccessUrl失效）
                .failureHandler(defaultAuthenticationFailureHandler) // 自定义认证失败处理类
                .and().authorizeRequests() 
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/login", "/loginError", "/**/login.shtml").permitAll() // 不需要登录都可以访问
                .antMatchers("/admin/**").hasRole("ADMIN") // 不仅需要登录而且需要相应的角色才能访问
                .anyRequest().authenticated(); // 其他资源都需要登录才能访问
    }

    /**
     * 认证信息管理 spring5中摒弃了原有的密码存储格式，官方把spring security的密码存储格式改了
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                // 认证信息存储到内存中
                .passwordEncoder(passwordEncoder()).withUser("admin").password(passwordEncoder().encode("123456"))
                .roles("ADMIN");
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
