package com.dinosaur.plat.user.web;

import com.dinosaur.module.system.construction.Construction;
import com.dinosaur.module.user.GroupService;
import com.dinosaur.module.user.UserService;
import com.dinosaur.module.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 用户管理控制器
 * @Author Alcott Hawk
 * @Date 11/28/2016
 */
@Controller
@RequestMapping(value = "/user/manager")
public class UserManagerController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    /**
     * 用户列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(@RequestParam(value = "pageSize",defaultValue = Construction.PAGE_SIZE_STR) int pageSize,
                       @RequestParam(value = "paheNo", defaultValue = Construction.PAGE_NO_STR) int pageNo,
                       Model model){
        model.addAttribute("users",userService.getUserByPage(pageSize,pageNo));
        model.addAttribute("groups",groupService.getGroupByPage(pageSize,pageNo));
        return "user/list";
    }

    /**
     * 加入添加用户页面
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(){
        return  "user/create";
    }

    /**
     * 保存添加一个用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(User user){
        String id = null;
        try {
            id = userService.addUser(user).getId();
        }catch (Exception e){
            logger.error("用户创建失败："+e.getMessage());
            return "error";
        }
        return id;
    }

    /**
     * 加入添加用户关系页面
     * @return
     */
    @RequestMapping(value = "/relationship",method = RequestMethod.GET)
    public String addRelationship(Model model){
        model.addAttribute("users",userService.getUserByPage(Construction.PAGE_SIZE,Construction.PAGE_NO));
        model.addAttribute("groups",groupService.getGroupByPage(Construction.PAGE_SIZE,Construction.PAGE_NO));
        return "user/relationship";
    }

    /**
     * 保存添加的用户关系
     * @param userId
     * @param groupId
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/relationship",method = RequestMethod.POST)
    public String addRelationship(String userId, String groupId, RedirectAttributes redirectAttributes){
        if (userService.addRelationship(userId,groupId)){
            return "index";
        } else {
            redirectAttributes.addFlashAttribute("message","关系添加失败");
            return "user/relationship";
        }
    }

}
