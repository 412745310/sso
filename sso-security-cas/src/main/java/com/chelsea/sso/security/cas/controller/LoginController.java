package com.chelsea.sso.security.cas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    
    @RequestMapping("/login")
    public String login() {
        return "redirect:/page/login.shtml";
    }
    
    @RequestMapping("/loginError")
    public String loginError() {
        return "redirect:/page/login.shtml";
    }
    
    @RequestMapping("/main")
    public String main() {
        return "redirect:/page/main.shtml";
    }

}
