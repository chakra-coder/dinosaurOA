package com.dinosaur.web.classroom.controller;

import com.dinosaur.module.classroom.ClassroomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(){
        return "view/classroom/list";
    }

}
