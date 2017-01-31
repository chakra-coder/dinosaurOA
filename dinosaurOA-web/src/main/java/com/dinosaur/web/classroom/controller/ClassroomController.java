package com.dinosaur.web.classroom.controller;

import com.dinosaur.core.util.DateUtil;
import com.dinosaur.core.util.FileUtil;
import com.dinosaur.core.util.JsonResultUtil;
import com.dinosaur.core.util.ShiroUtil;
import com.dinosaur.core.util.entity.JsonObject;
import com.dinosaur.module.classroom.ClassroomService;
import com.dinosaur.module.classroom.entity.Classroom;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/** 教室controller
 * @Author Alcott Hawk
 * @Date 1/12/2017
 */
@Controller
@RequestMapping(value = "/classroom")
public class ClassroomController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClassroomService classroomService;

    @RequestMapping(value = {"/in/list", "/create/list"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model){
        String uri = request.getRequestURI();
        List<Classroom> classrooms = null;
        if (StringUtils.containsOnly(uri,"in")){
            classrooms = classroomService.getByCurrentUser();
        } else {
            classrooms = classroomService.getByCreateUser(ShiroUtil.getCurrentUserId().toString());
        }
        model.addAttribute("classs",classrooms);
        return "view/class/list";
    }

    /**
     * 添加班级信息
     * @param classroom
     * @param file
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Classroom classroom, @RequestParam("file") MultipartFile file, Model model){
        if (null != file){
            String logoPath = null;
            try {
                logoPath = FileUtil.uploadImg(file,"/themes/default/static/image/");
                classroom.setThemePic(logoPath);
            } catch (IOException e) {
                // TODO 主题图上传待处理
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        classroom.setCreateTime(DateUtil.getCurrentTime());
        if (classroomService.create(classroom)){
            model.addAttribute("messgae","班级创建成功");
        } else {
            model.addAttribute("messgae","班级创建失败");
        }
        return "redirect:/classroom/list";
    }

    /**
     * 删除一个班级信息
     * @param classId 班级id
     * @return
     */
    public JsonObject delete(String classId){
        boolean falg = classroomService.delete(classId);
        if (falg){
            return JsonResultUtil.getSuccessJson("班级删除成功");
        } else {
            return JsonResultUtil.getErrorJson("班级删除失败");
        }
    }

}
