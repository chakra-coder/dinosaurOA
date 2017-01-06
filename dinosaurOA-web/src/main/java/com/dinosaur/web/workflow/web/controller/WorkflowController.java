package com.dinosaur.web.workflow.web.controller;

import com.dinosaur.module.flowable.workflow.HtmlFormService;
import com.dinosaur.module.flowable.workflow.ProcessService;
import com.dinosaur.module.system.construction.Construction;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Workflow Controller
 * @Author Alcott Hawk
 * @Date 12/8/2016
 */
@Controller
@RequestMapping(value = "/workflow")
public class WorkflowController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProcessService processService;

    @Autowired
    private HtmlFormService htmlFormService;

    /**
     * 获取系统激活可用的流程定义
     * @param pageSize 页面展示数量
     * @param pageNo 页面号
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String getProcess(@RequestParam(value = "pageSize",defaultValue = Construction.PAGE_SIZE_STR) int pageSize,
                             @RequestParam(value = "pageNo",defaultValue = Construction.PAGE_NO_STR) int pageNo,
                             Model model){
        List<ProcessDefinition> processDefinitions = processService.getProcessDefinition(pageSize,pageNo);
        model.addAttribute("processDefinitions",processDefinitions);
        return "view/workflow/list";
    }

    /**
     * 启动有个特定的流程
     * @param processDefinitionId 流程定义id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/start/{processDefinitionId}",method = RequestMethod.GET)
    public String startProcess(@PathVariable(value = "processDefinitionId") String processDefinitionId,
                               HttpServletRequest request,Model model){
        try {
            model.addAttribute("form",htmlFormService.getStartForm(processDefinitionId));
        } catch (Exception e){
            logger.error("表单数据加载失败");
            model.addAttribute("message","表单加载失败！");
        }
        return "view/workflow/start";
    }

}
