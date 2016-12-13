package com.dinosaur.web.workflow.web;

import org.activiti.engine.FormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 表单Controller
 * @Author Alcott Hawk
 * @Date 12/8/2016
 */
@Controller
@RequestMapping(value = "/form")
public class FormController {

    private Logger logger = LoggerFactory.getLogger(getClass());

}
