package com.dinosaur.plat.menu.web;

import com.dinosaur.module.menu.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 菜单controller
 * <p>菜单的基本创建及维护</p>
 * @Author Alcott Hawk
 * @Date 1/2/2017
 */
@Controller
public class MenuControlelr {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "view/menu/list";
    }

}
