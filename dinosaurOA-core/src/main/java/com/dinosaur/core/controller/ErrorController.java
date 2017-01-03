package com.dinosaur.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author Alcott Hawk
 * @Date 1/3/2017
 */
@Controller
@RequestMapping(value = "error")
public class ErrorController {

    @RequestMapping(method = RequestMethod.GET)
    public String error(String name){
        return "view/error/"+name;
    }

}
