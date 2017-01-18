package com.dinosaur.plat.menu.web.controller;

import com.dinosaur.core.util.JsonResultUtil;
import com.dinosaur.core.util.entity.JsonObject;
import com.dinosaur.module.menu.MenuService;
import com.dinosaur.module.menu.entity.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String list(Model model){
        model.addAttribute("menus",menuService.getAll());
        return "view/menu/list";
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public boolean create(Menu menu,Integer pid){
        if (menuService.add(menu,pid)){
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public JsonObject update(Menu menu){
        menuService.update(menu);
        return JsonResultUtil.getSuccessJson("更新成功");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonObject delete(Integer id){
        if (menuService.delete(id)){
            return JsonResultUtil.getSuccessJson("删除成功！");
        } else {
            return JsonResultUtil.getErrorJson("菜单删除失败！");
        }
    }

}
