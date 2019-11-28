package com.chelsea.sso.security.cas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/user")
public class UserController {
    
    @RequestMapping("/list")
    public String list() {
        return "user_list";
    }

}
