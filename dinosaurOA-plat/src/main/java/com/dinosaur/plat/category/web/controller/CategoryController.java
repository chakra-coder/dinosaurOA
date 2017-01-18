package com.dinosaur.plat.category.web.controller;

import com.dinosaur.core.util.JsonResultUtil;
import com.dinosaur.core.util.entity.JsonObject;
import com.dinosaur.module.category.CategoryService;
import com.dinosaur.module.category.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 分类controller
 * @Author Alcott Hawk
 * @Date 1/13/2017
 */
@Controller
@RequestMapping(value = "category/manager")
public class CategoryController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("list",categoryService.getAll());
        return "view/category/list";
    }

    @RequestMapping(value = "/add",method = RequestMethod.PUT)
    @ResponseBody
    public JsonObject add(Category category,Integer parentId){
        if (categoryService.create(category,parentId)){
            return JsonResultUtil.getSuccessJson("添加成功");
        } else {
            return JsonResultUtil.getErrorJson("添加失败");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonObject delete(Integer id){
        if (categoryService.delete(id)){
            return JsonResultUtil.getSuccessJson("删除失败");
        } else {
            return JsonResultUtil.getErrorJson("删除失败");
        }
    }

}
