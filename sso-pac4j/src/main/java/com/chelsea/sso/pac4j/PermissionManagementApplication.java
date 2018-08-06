package com.chelsea.sso.pac4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class PermissionManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(PermissionManagementApplication.class, args);
    }
}
