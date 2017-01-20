package com.dinosaur.plat.category.web.controller;

import com.dinosaur.core.util.JsonResultUtil;
import com.dinosaur.core.util.entity.JsonObject;
import com.dinosaur.module.category.CategoryService;
import com.dinosaur.module.category.entity.Category;
import com.dinosaur.module.user.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("groups",groupService.getAll());
        return "view/category/list";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public JsonObject add(String groupId, String name, String parentId){
        if (categoryService.create(groupId, name, parentId)){
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

    @RequestMapping(value = "/list-json",method = RequestMethod.GET)
    @ResponseBody
    public JsonObject getCategory(){
        List<Category> categories = categoryService.getAll();
        return JsonResultUtil.getObjectJson(categories);
    }

}
