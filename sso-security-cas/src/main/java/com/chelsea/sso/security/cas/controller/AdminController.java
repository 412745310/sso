package com.chelsea.sso.security.cas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    
    @RequestMapping("/list")
    public String list() {
        return "admin_list";
    }

}
