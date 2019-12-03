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
        request.getSession().setAttribute("message", "login in failure");
        return "redirect:/page/login.shtml";
    }
    
    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/page/login.shtml";
    }
    
    @RequestMapping("/main")
    public String main() {
        return "redirect:/page/main.shtml";
    }

}
