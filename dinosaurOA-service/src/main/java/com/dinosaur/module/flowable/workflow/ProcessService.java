package com.dinosaur.module.flowable.workflow;

import com.dinosaur.core.shiro.ShiroUser;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.impl.form.StartFormDataImpl;
import org.activiti.engine.impl.form.TaskFormDataImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 流程Service
 * @Author Alcott Hawk
 * @Date 12/2/2016
 */
@Service
@Transactional
public class ProcessService {

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
     * 获取需要我办理的任务的任务列表
     * @param pageSize 获取设计的条数
     * @param pageNo 获取数据的起始位置
     * @param assignee 办理人（办理人的id）
     * @return
     */
    public List<Task> getTask(int pageSize,int pageNo,String assignee){
        List<Task> tasks = taskService.createTaskQuery().orderByTaskCreateTime().
                desc().active().taskAssignee(assignee).listPage(pageNo,pageSize);
        return  tasks;
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
    public boolean doTask(String taskId,String userId, HttpServletRequest request){
        Map<String, String> formProperties = new HashMap<String, String>();

        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
        for (Entry<String, String[]> entry : entrySet) {
            formProperties.put(entry.getKey(),entry.getValue()[0]);
        }

        logger.debug("start form parameters: {}", formProperties);

        try {
            identityService.setAuthenticatedUserId(userId);
            formService.submitTaskFormData(taskId, formProperties);
        } catch (Exception e){
            logger.error("任务办理失败："+e.getMessage());
            return false;
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        return true;
    }

    /**
     * 获取指定的动态表单的表单字段
     * @param processDefinitionId 流程定义id
     * @return
     */
    public Map<String,Object> getStartFormForDynamic(String processDefinitionId){
        Map<String, Object> result = new HashMap<String, Object>();
        StartFormDataImpl startFormData = (StartFormDataImpl) formService.getStartFormData(processDefinitionId);
        startFormData.setProcessDefinition(null);

        List<FormProperty> formProperties = startFormData.getFormProperties();
        this.putData(result,formProperties);
        result.put("form", startFormData);
        return  result;
    }

    /**
     * 获取指定动态流程的任务表单
     * @param taskId 任务id
     * @return
     */
    public Map<String,Object> getTaskFormForDynamic(String taskId){
        Map<String, Object> result = new HashMap<String, Object>();
        TaskFormDataImpl taskFormData = (TaskFormDataImpl) formService.getTaskFormData(taskId);
        taskFormData.setTask(null);
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        this.putData(result,formProperties);
        result.put("form", taskFormData);
        return  result;
    }

    /**
     * 获取指定的外置表单流程的开始表单
     * @param processDefinitionId 流程定义id
     * @return
     */
    public Object getStartFormForFormKey(String processDefinitionId){
        return formService.getRenderedStartForm(processDefinitionId);
    }

    /**
     * 获取指定的外置表单流程的任务表单
     * @param taskId 任务id
     * @return
     */
    public Object getTaskForm(String taskId){
        return formService.getRenderedTaskForm(taskId);
    }

    /**
     * 读取表单属性
     * @param map
     * @param formProperties
     */
    private void putData(Map<String,Object> map,List<FormProperty> formProperties){
        for (FormProperty formProperty : formProperties) {
            Map<String, String> values = (Map<String, String>) formProperty.getType().getInformation("values");
            if (values != null) {
                for (Entry<String, String> enumEntry : values.entrySet()) {
                    logger.debug("enum, key: {}, value: {}", enumEntry.getKey(), enumEntry.getValue());
                }
                map.put("enum_" + formProperty.getId(), values);
            }
        }
    }

}
