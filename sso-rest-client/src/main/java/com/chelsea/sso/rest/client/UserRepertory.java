/*
 * 版权所有.(c)2008-2017. 卡尔科技工作室
 */

package com.chelsea.sso.rest.client;

import java.util.HashMap;
import java.util.Map;

public class UserRepertory {
    private Map<String, SysUser> users = new HashMap<>();

    public UserRepertory(Map<String, SysUser> users) {
        this.users = users;
    }

    public UserRepertory(SysUser... users) {
        for (SysUser user : users) {
            this.users.put(user.getUsername(), user);
        }
    }

    /**
     * 根据id获取对应的用户数据
     *
     * @param id 用户id
     * @return
     */
    public SysUser getUser(String id) {
        return users.get(id);
    }
}
