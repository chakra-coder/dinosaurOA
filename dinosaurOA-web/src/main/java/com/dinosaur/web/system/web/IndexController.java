package com.dinosaur.web.system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * IndexController
 * @author Alcott Hawk
 * @date 2016/11/15
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        System.out.println("没来呢吧！");
        return "view/index";
    }

}
