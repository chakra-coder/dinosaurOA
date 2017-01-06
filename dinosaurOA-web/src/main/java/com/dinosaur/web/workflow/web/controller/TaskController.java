package com.dinosaur.web.workflow.web.controller;

import com.dinosaur.module.flowable.workflow.ProcessService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 任务Controller
 * @Author Alcott Hawk
 * @Date 1/6/2017
 */
@Controller
@RequestMapping(value = "/task")
public class TaskController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProcessService processService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Task> list(){
        // TODO 任务查询
        return null;
    }

    @RequestMapping(value="/claim/{taskId}", method = RequestMethod.POST)
    @ResponseBody
    public String claimTask(@PathVariable("taskId") String taskId){
        if (processService.claim(taskId)){
            return "任务已签收！";
        } else {
            return "任务签收失败，可能已经被签收！";
        }
    }

    @RequestMapping(value = "/doTask/{taskId}",method = RequestMethod.POST)
    @ResponseBody
    public boolean doTask(@PathVariable(value = "taskId") String taskId, HttpServletRequest request, RedirectAttributes attributes){
        Map<String,String[]> parameterMap = request.getParameterMap();
        if (processService.doTask(taskId,parameterMap)) {
            attributes.addAttribute("message","任务办理完成！");
            return true;
        } else {
            attributes.addAttribute("message","任务办理失败！");
            return false;
        }
    }

}
