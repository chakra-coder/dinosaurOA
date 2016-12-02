package com.dinosaur.plat.modeler.web;

import com.dinosaur.module.activiti.modeler.ModelerService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 保存流程定义数据
     * @param modelId 流程定义id
     * @param values 流程定义的数据集合
     */
    @RequestMapping(value="/{modelId}/save", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void save(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values){

    }

    /**
     * 获取编辑流程图是需要的数据
     * @param modelId 流程定义id
     * @return 流程定义的ObjectNode对象
     */
    @RequestMapping(value="/model/{modelId}/json", method = RequestMethod.GET, produces = "application/json")
    public ObjectNode edit(@PathVariable String modelId){
        return null;
    }

}
