package com.dinosaur.plat.classroom.web.controller;

import com.dinosaur.core.util.JsonResultUtil;
import com.dinosaur.core.util.entity.JsonObject;
import com.dinosaur.module.classroom.ClassroomService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 班级Controller
 * @Author Alcott Hawk
 * @Date 1/23/2017
 */
@Controller
@RequestMapping(value = "/classroom")
public class ClassroomController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClassroomService classroomService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String index(){
        return "view/classroom/list";
    }

    /**
     * 激活或者挂起一个别拿己
     * @param id 班级id
     * @param type 操作类型<br/>操作类型为"<b>active</b>"或者"<b>suspend</b>"
     * @return
     */
    @RequestMapping(value = "/suspendOrActive/{type}")
    public JsonObject suspendOrActive(String id, @PathVariable(value = "type") String type){
        if (StringUtils.isBlank(type)){
            return JsonResultUtil.getErrorJson("参数错误");
        }
        boolean isSuspend = type == "active"?false:true;
        if (classroomService.SuspendOrActive(id,isSuspend)){
            return JsonResultUtil.getSuccessJson("班级挂起成功");
        }
        return JsonResultUtil.getErrorJson("班级挂起失败");
    }

}
