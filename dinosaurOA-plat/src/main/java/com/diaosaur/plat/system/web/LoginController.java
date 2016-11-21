package com.diaosaur.plat.system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录Controller
 * @Author Alcott Hawk
 * @Date 2016/11/15
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    public String login(){
        return "";
    }

}
