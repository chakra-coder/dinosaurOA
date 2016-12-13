package com.dinosaur.web.system.entity;

import java.io.Serializable;

/**
 * shiro 用户实体类
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
public class ShiroUser implements Serializable{

    public String loginNmae;
    public String ip;
    public String id;

    public ShiroUser(String name, String ip, String id){
        this.loginNmae = name;
        this.ip = ip;
        this.id = id;
    }

    public String getLoginNmae() {
        return loginNmae;
    }

    @Override
    public String toString() {
        return loginNmae;
    }
}
