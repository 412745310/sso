package com.chelsea.sso.security.cas.config;

import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.chelsea.sso.security.cas.handler.DefaultAccessDeniedHandler;
import com.chelsea.sso.security.cas.handler.DefaultLogoutSuccessHandler;
import com.chelsea.sso.security.cas.service.MyUserDetailsService;

/**
 * spring security配置类
 */
@EnableWebSecurity
public class SecurtyConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private AuthenticationSuccessHandler defaultAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler defaultAuthenticationFailureHandler;
    
    @Autowired
    private CasProperties casProperties;
    
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    
    /**定义认证用户信息获取来源，密码校验规则等*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(casAuthenticationProvider());
    }

    /**
     * 自定义配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
//      .addFilterBefore(new ValidationCodeFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilter(casAuthenticationFilter()) // CAS单点登录时设置
        .addFilterBefore(casLogoutFilter(), LogoutFilter.class); // CAS单点登录时设置
        
        http.formLogin() // Form表单登录配置
        .loginPage("/login"); // 自定义登录页面
//        .defaultSuccessUrl("/main") // 自定义登录成功页面
//        .failureUrl("/loginError") // 自定义登录失败界面
//        .usernameParameter("name") // 自定义登录用户名参数名
//        .passwordParameter("password") // 自定义登录密码参数名
//                .successHandler(defaultAuthenticationSuccessHandler) // 自定义认证成功处理类（此设置会让defaultSuccessUrl失效）
//        .failureHandler(defaultAuthenticationFailureHandler); // 自定义认证失败处理类
        
        http.authorizeRequests() // 授权配置  
        .antMatchers("/css/**", "/js/**", "/fonts/**", "/login", "/loginError", "/**/login.shtml", "/logout", "/accessError", "/**/accessError.shtml").permitAll() // 不需要登录都可以访问
        .antMatchers("/admin/**").hasRole("ADMIN") // 不仅需要登录而且需要相应的角色才能访问
        .anyRequest().authenticated(); // 其他资源都需要登录才能访问
        
        http.exceptionHandling() // 异常处理
        .accessDeniedHandler(new DefaultAccessDeniedHandler()) // 自定义访问受限处理类
        .authenticationEntryPoint(casAuthenticationEntryPoint());
        
        http.logout() // 登录退出配置
        .logoutUrl("logout") // 自定义退出配置页面（此设置会让addLogoutHandler和logoutSuccessHandler失效）
//                .addLogoutHandler(new DefaultLogoutHandler()) // 自定义退出处理类
        .logoutSuccessHandler(new DefaultLogoutSuccessHandler()) // 自定义退出成功处理类
//                .logoutSuccessUrl("/logout") // 退出成功后跳转的页面
        .invalidateHttpSession(true).deleteCookies("JSESSIONID");    // 退出时要删除的Cookies的名字
        
        http.csrf().disable(); // 取消CSRF
    }
    
    /**指定service相关信息*/
    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(casProperties.getAppServerUrl() + casProperties.getAppLoginUrl());
        serviceProperties.setAuthenticateAllArtifacts(true);
        return serviceProperties;
    }
    
    /**认证的入口*/
    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
        casAuthenticationEntryPoint.setLoginUrl(casProperties.getCasServerLoginUrl());
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
        return casAuthenticationEntryPoint;
    }
    
    /**CAS认证过滤器*/
    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
        casAuthenticationFilter.setServiceProperties(serviceProperties());
        casAuthenticationFilter.setFilterProcessesUrl(casProperties.getAppLoginUrl());
        casAuthenticationFilter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/main"));
        return casAuthenticationFilter;
    }
    
    @Bean
    public Cas30ServiceTicketValidator cas30ServiceTicketValidator() {
        return new Cas30ServiceTicketValidator(casProperties.getCasServerUrl());
    }
    
    /**cas 认证 Provider*/
    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
//        casAuthenticationProvider.setAuthenticationUserDetailsService(customUserDetailsService());
        casAuthenticationProvider.setUserDetailsService(myUserDetailsService); //这里只是接口类型，实现的接口不一样，都可以的。
        casAuthenticationProvider.setServiceProperties(serviceProperties());
        casAuthenticationProvider.setTicketValidator(cas30ServiceTicketValidator());
        casAuthenticationProvider.setKey("casAuthenticationProviderKey");
        return casAuthenticationProvider;
    }
    
    /**请求单点退出过滤器*/
    @Bean
    public LogoutFilter casLogoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter(casProperties.getCasServerLogoutUrl(), new SecurityContextLogoutHandler());
        logoutFilter.setFilterProcessesUrl(casProperties.getAppLogoutUrl());
        return logoutFilter;
    }
    
    @Bean
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();
        servletListenerRegistrationBean.setListener(new SingleSignOutHttpSessionListener());
        return servletListenerRegistrationBean;
    }

    /**
     * 认证信息管理 spring5中摒弃了原有的密码存储格式，官方把spring security的密码存储格式改了
     *
     * @param auth
     * @throws Exception
     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                // 认证信息存储到内存中
//                .passwordEncoder(passwordEncoder()).withUser("admin").password(passwordEncoder().encode("123456"))
//                .roles("ADMIN");
//    }

    /**
     * 加密方法
     * 常规情况下，passwordEncoder.encode("123123")是注册时需要做的方法
     * 而登录的时候则只需要传递password即可
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 加载 ClassPathTldsLoader
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ClassPathTldsLoader.class)
    public ClassPathTldsLoader classPathTldsLoader(){
        return new ClassPathTldsLoader();
    }

}
