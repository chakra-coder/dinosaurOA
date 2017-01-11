package com.dinosaur.web.workflow.web.controller;

import com.dinosaur.core.shiro.ShiroUser;
import com.dinosaur.core.util.JsonResultUtil;
import com.dinosaur.core.util.entity.JsonObject;
import com.dinosaur.module.flowable.workflow.HtmlFormService;
import com.dinosaur.module.flowable.workflow.ProcessService;
import com.dinosaur.module.system.construction.Construction;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private HtmlFormService htmlFormService;

    /**
     * 获取指派给我的任务，任务明确指派，不需要签收
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String assignList(Model model){
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        // FIXME 流程设计缺陷，流程定义中指定的办理人及侯选组应该使用id来作为唯一确定信息，指定人名或组名导致指派混乱
        List<Task> tasks = processService.getTask(Construction.PAGE_SIZE,Construction.PAGE_NO,shiroUser.loginNmae,ProcessService.ASSIGN);
        model.addAttribute("tasks",tasks);
        return "view/workflow/taskList";
    }

    /**
     * 获取需要我签收的任务，任务未指派，需要签收使用，在候选组中成员可见该任务，签收后组员中签收人可见
     * @param model
     * @return
     */
    @RequestMapping(value = "/sign/list",method = RequestMethod.GET)
    public String signList(Model model){
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        // FIXME 流程设计缺陷，流程定义中指定的办理人及侯选组应该使用id来作为唯一确定信息，指定人名或组名导致指派混乱
        List<Task> tasks = processService.getTask(Construction.PAGE_SIZE,Construction.PAGE_NO,shiroUser.loginNmae,ProcessService.SIGN);
        model.addAttribute("tasks",tasks);
        return  "view/workflow/taskList";
    }

    /**
     * 签收任务
     * @param taskId
     * @return
     */
    @RequestMapping(value="/claim/{taskId}", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject claimTask(@PathVariable("taskId") String taskId){
        if (processService.claim(taskId)){
            return JsonResultUtil.getSuccessJson("任务已签收!");
        } else {
            return JsonResultUtil.getErrorJson("任务签收失败，可能已经被签收！");
        }
    }

    /**
     * 加载任务办理页面及任务办理页面数据
     * @param taskId 任务id
     * @param model
     * @return
     */
    @RequestMapping(value = "/doTask/{taskId}", method = RequestMethod.GET)
    public String doTask(@PathVariable(value = "taskId") String taskId, Model model){
        try {
            model.addAttribute("form",htmlFormService.getTaskForm(taskId));
        } catch (Exception e){
            logger.error("表单数据加载失败");
            model.addAttribute("message","表单加载失败！");
        }
        return "view/workflow/doTask";
    }

    /**
     * 添加任务办理
     * @param taskId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/submit/{taskId}",method = RequestMethod.POST)
    public String doTask(@PathVariable(value = "taskId") String taskId, HttpServletRequest request, Model model){
        Map<String,String[]> parameterMap = request.getParameterMap();
        if (parameterMap.isEmpty()){
            return "view/workflow/error";
        }
        if (processService.doTask(taskId,parameterMap)) {
            return "view/workflow/success";
        } else {
            return "view/workflow/error";
        }
    }

}
