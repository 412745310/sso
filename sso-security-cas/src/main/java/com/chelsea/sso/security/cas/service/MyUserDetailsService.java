package com.chelsea.sso.security.cas.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.chelsea.sso.security.cas.domain.MyUser;

/**
 * 自定义登录校验器
 */
@Component
public class MyUserDetailsService implements UserDetailsService {
    
    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    //@Autowired
    //private XXService xxService;
    
    private List<MyUser> myUserList = Arrays.asList(
                new MyUser("admin", "123456", new String[]{"ROLE_ADMIN", "ROLE_EMPLOYEE"}, 1),
                new MyUser("employee", "123456", new String[]{"ROLE_EMPLOYEE"}, 1),
                new MyUser("visitor", "123456", new String[]{"ROLE_VISITOR"}, 0),
                new MyUser("zhangsan", "123456", new String[]{"ROLE_VISITOR"}, 1)
            );
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("根据用户名查询用户信息"+username);
        //数据库查询用户信息
        //xxService.query(username);
        
        /******* 模拟数据库查询用户信息 ***********/
        MyUser mu = null;
        for (MyUser myUser : myUserList) {
            String name = myUser.getName();
            if (name.equals(username)) {
                mu = myUser;
                break;
            }
        }
        if (mu == null) {
            throw new UsernameNotFoundException(username);
        }
        String password = passwordEncoder.encode(mu.getPassword());
        // 数据库中的角色名必须有ROLE_前缀，否则无法匹配类SecurtyConfig里设置的角色校验hasRole
        List<GrantedAuthority> role = AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.arrayToDelimitedString(mu.getRoles(), ","));
        /****************************************/
        
        return new User(username, password, role);
    }

}
