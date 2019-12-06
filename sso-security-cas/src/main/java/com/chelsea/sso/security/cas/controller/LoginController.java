package com.chelsea.sso.security.cas.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    
    @RequestMapping("/login")
    public String login() {
        return "redirect:/page/login.shtml";
    }
    
    @RequestMapping("/loginError")
    public String loginError(HttpServletRequest request) {
        return "redirect:/page/login.shtml";
    }
    
    @RequestMapping("/accessError")
    public String accessError(HttpServletRequest request) {
        return "redirect:/page/accessError.shtml";
    }
    
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/page/login.shtml";
    }
    
    @RequestMapping("/main")
    public String main() {
        return "main";
    }
    
}
