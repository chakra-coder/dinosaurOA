package com.dinosaur.plat.modeler.web;

import com.dinosaur.module.activiti.modeler.ModelerService;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * modeler Controller
 * @Author Alcott Hawk
 * @Date 12/2/2016
 */
@Controller
@RequestMapping(value = "/modeler")
public class ModelRestController implements ModelDataJsonConstants{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ModelerService modelerService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestParam("name") String name, @RequestParam("key") String key, @RequestParam("description") String description,
                       HttpServletRequest request, HttpServletResponse response){
        String id = modelerService.create(name,key,description);
        if (id != null){
            try {
                response.sendRedirect(request.getContextPath() + "/modeler/modeler.html?modelId=" + id);
            } catch (IOException e) {
                logger.error("资源访问错误："+e.getMessage());
            }
        }
    }

}
