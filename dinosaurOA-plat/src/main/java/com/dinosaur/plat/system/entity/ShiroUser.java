package com.dinosaur.plat.system.entity;

/**
 * shiro 用户实体类
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
public class ShiroUser {

    public String name;
    public String ip;
    public String id;

    public ShiroUser(String name,String ip,String id){
        this.name = name;
        this.ip = ip;
        this.id = id;
    }

}
