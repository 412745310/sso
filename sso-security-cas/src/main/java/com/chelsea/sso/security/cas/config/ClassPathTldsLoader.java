package com.chelsea.sso.security.cas.config;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * 自定义spring security taglib标签加载类
 * 
 * @author shevchenko
 *
 */
public class ClassPathTldsLoader {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 指定路径，我们通过pom引入的 security.tld 中存放标签
     */
    private static final String SECURITY_TLD = "META-INF/security.tld";

    final private List<String> classPathTlds;

    public ClassPathTldsLoader(String... classPathTlds) {
        super();
        if (classPathTlds == null || classPathTlds.length <= 0) {
            this.classPathTlds = Arrays.asList(SECURITY_TLD);
        } else {
            this.classPathTlds = Arrays.asList(classPathTlds);
        }
    }

    @PostConstruct
    public void loadClassPathTlds() {
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(classPathTlds);
    }

}
