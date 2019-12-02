package com.chelsea.sso.security.cas.domain;

import java.io.Serializable;

public class MyUser implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String name;
    private String password;
    private String[] roles;
    private Integer status;

    public MyUser() {}

    public MyUser(String name, String password, String[] roles, Integer status) {
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

}
