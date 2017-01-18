package com.dinosaur.core.shiro;

import java.io.Serializable;

/**
 * @Author Alcott Hawk
 * @Date 1/2/2017
 */
public class ShiroUser implements Serializable{

    public String loginNmae;    //登录用户名，昵称，系统唯一
    public String name;         //用户名，真是名称
    public String ip;           //用户登录id
    public String id;           //用户id

    public ShiroUser(String loginNmae, String name, String ip, String id){
        this.loginNmae = loginNmae;
        this.name = name;
        this.ip = ip;
        this.id = id;
    }

    @Override
    public String toString() {
        return loginNmae;
    }

}
