package com.dinosaur.plat.user.web.controller;

import com.dinosaur.core.util.DateUtil;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;

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
                       @RequestParam(value = "pageNo", defaultValue = Construction.PAGE_NO_STR) int pageNo,
                       Model model){
        model.addAttribute("users",userService.getUserByPage(pageSize,pageNo).getContent());
        model.addAttribute("groups",groupService.getGroupByPage(pageSize,pageNo).getContent());
        model.addAttribute("pageCount",userService.getCount());
        return "view/user/list";
    }

    /**
     * 加入添加用户页面
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(){
        return  "view/user/create";
    }

    /**
     * 保存添加一个用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(User user,Model model){
        String id = null;
        try {
            id = userService.addUser(user).getId();
        }catch (Exception e){
            logger.error("用户创建失败："+e.getMessage());
            model.addAttribute("message","用户创建失败"+e.getMessage());
            return "error";
        }
        model.addAttribute("message","用户添加成功！");
        return "redirect:/user/manager/list";
    }

    /**
     * 导出用户信息到excel
     * @param userId
     * @param response
     */
    public void export(String[] userId, HttpServletResponse response){
        try {
            byte[] content = userService.exportUser(Arrays.asList(userId));
            InputStream is = new ByteArrayInputStream(content);
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition","attachment;filename="+new String((DateUtil.getCurrentTime()+".xlsx").getBytes(),"utf-8"));
            ServletOutputStream servletOutputStream = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(servletOutputStream);
            byte[] buff = new byte[1024];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))){
                bos.write(buff, 0, bytesRead);
            }
        } catch (IOException e) {
            logger.error("用户信息导出失败："+e.getMessage());
        }
    }

    /**
     * 加入添加用户关系页面
     * @return
     */
    @RequestMapping(value = "/relationship",method = RequestMethod.GET)
    public String addRelationship(Model model){
        model.addAttribute("users",userService.getUserByPage(Construction.PAGE_SIZE,Construction.PAGE_NO).getContent());
        model.addAttribute("groups",groupService.getGroupByPage(Construction.PAGE_SIZE,Construction.PAGE_NO).getContent());
        return "view/user/relationship";
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
            return "view/index";
        } else {
            redirectAttributes.addFlashAttribute("message","关系添加失败");
            return "view/user/relationship";
        }
    }

}
