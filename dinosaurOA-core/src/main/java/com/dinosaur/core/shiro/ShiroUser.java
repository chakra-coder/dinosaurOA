package com.dinosaur.core.shiro;

import java.io.Serializable;

/**
 * @Author Alcott Hawk
 * @Date 1/2/2017
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
