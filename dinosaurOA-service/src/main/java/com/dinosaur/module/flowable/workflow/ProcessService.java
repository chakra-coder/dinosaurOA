package com.dinosaur.module.flowable.workflow;

import com.dinosaur.core.shiro.ShiroUser;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.impl.form.StartFormDataImpl;
import org.activiti.engine.impl.form.TaskFormDataImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;

import static org.apache.batik.svggen.SVGStylingAttributes.set;

/**
 * 流程Service
 * @Author Alcott Hawk
 * @Date 12/2/2016
 */
@Service
@Transactional
public class ProcessService {

    public static final String ASSIGN = "assign";
    public static final String SIGN = "sign";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private FormService formService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IdentityService identityService;

    /**
     * 查询流程定义，并提供分页
     * @param pageSize 页面大小
     * @param pageNo 页码
     * @return
     */
    public List<ProcessDefinition> getProcessDefinition(int pageSize, int pageNo){
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().active().
                orderByProcessDefinitionName().desc().listPage(pageNo-1,pageSize);
        return processDefinitions;
    }

    /**
     * 查询流程实例，并提供分页
     * @param pageSize 页面大小
     * @param pageNo 页码
     * @return
     */
    public List<ProcessInstance> getProcessInstance(int pageSize, int pageNo){
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().
                orderByProcessDefinitionKey().desc().listPage(pageNo,pageSize);
        return  processInstances;
    }

    /**
     * 获取需要我办理的任务的任务列表，通过不同的任务指派类型获取，<br/>
     * 例如，传入taskType为sign，则返回所有我可以签收的任务列表
     * @param pageSize 页面大小
     * @param pageNo 页面当前页
     * @param assigneeOrGroup 办理人id或侯选组id
     * @param taskType 任务类型{assign，sign}
     * @return
     */
    public List<Task> getTask(int pageSize,int pageNo,String assigneeOrGroup,String taskType){
        TaskQuery query = this.query().orderByTaskCreateTime().desc();
        if (ASSIGN.equals(taskType)){
            List<Task> tasks = query.taskAssignee(assigneeOrGroup).listPage(pageNo,pageSize);
            return  tasks;
        } else if (SIGN.equals(taskType)){
            List<Task> tasks = query.taskCandidateGroup(assigneeOrGroup).listPage(pageNo,pageSize);
            return  tasks;
        } else {
            return new ArrayList<Task>();
        }
    }

    /**
     * 签收指定的任务
     * @param taskId 任务id
     * @return
     */
    public boolean claim(String taskId){
        try {
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            if (null == shiroUser){
                return false;
            }
            taskService.claim(taskId,shiroUser.id);
            return true;
        } catch (Exception e){
            logger.error("任务签收失败："+e.getMessage());
            return false;
        }
    }

    /**
     * 办理指定的任务
     * @param taskId 任务id
     * @param userId 用户id
     * @param request
     * @return
     */
    public boolean doTask(String taskId, Map<String,String[]> param){
        Map<String, String> formProperties = putdata(param);
        try {
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            identityService.setAuthenticatedUserId(shiroUser.id);
            formService.submitTaskFormData(taskId, formProperties);
        } catch (Exception e){
            logger.error("任务办理失败："+e.getMessage());
            return false;
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        return true;
    }

    public boolean startup(String processDefinitionId, Map<String,String[]> param){
        Map<String, String> formProperties = putdata(param);
        try {
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            identityService.setAuthenticatedUserId(shiroUser.id);
            formService.submitStartFormData(processDefinitionId, formProperties);
        } catch (Exception e){
            logger.error("任务办理失败："+e.getMessage());
            return false;
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        return true;
    }

    /**
     * 装入数据到map
     * @param param
     * @return
     */
    private Map<String,String> putdata(Map<String, String[]> param){
        Map<String, String> formProperties = new HashMap<String, String>();
        Set<Entry<String, String[]>> entrySet = param.entrySet();
        entrySet.forEach(obj->{
            formProperties.put(obj.getKey(),obj.getValue()[0]);
        });
        return formProperties;
    }

    /**
     * 获取一个任务查询器
     * @return
     */
    private TaskQuery query(){
        return taskService.createTaskQuery();
    }

}
