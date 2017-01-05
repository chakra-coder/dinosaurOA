package com.dinosaur.plat.modeler.web.controller;

import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

/**
 * flowable 设计器资源加载Controller
 * @Author Alcott Hawk
 * @Date 12/2/2016
 */
@RestController
@RequestMapping(value = "/editor/stencilset")
public class StencilsetController {

    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String getStencilset(){
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
        try {
            return IOUtils.toString(stencilsetStream, "utf-8");
        } catch (Exception e) {
            throw new ActivitiException("Error while loading stencil set", e);
        }
    }

}
