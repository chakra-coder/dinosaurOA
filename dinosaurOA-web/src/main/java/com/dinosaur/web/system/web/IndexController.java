package com.dinosaur.web.system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * IndexController
 * @author Alcott Hawk
 * @date 2016/11/15
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController {

    public String index(){
        return "index";
    }

}
