package com.chelsea.sso.security.cas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {
    
    @RequestMapping("/list")
    public String list() {
        return "employee_list";
    }

}
