package com.dinosaur.plat.system.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author Alcott Hawk
 * @Date 11/28/2016
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "view/index";
    }

}
