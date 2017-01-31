package com.dinosaur.web.system.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * IndexController
 * @author Alcott Hawk
 * @date 2016/11/15
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "view/index";
    }

    @GetMapping(value = "/help")
    public String help(){
        return "view/help";
    }

}
