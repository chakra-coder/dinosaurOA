package com.dinosaur.module.flowable.workflow;

import com.dinosaur.core.shiro.ShiroUser;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.impl.form.StartFormDataImpl;
import org.activiti.engine.impl.form.TaskFormDataImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 流程表单业务
 * <p>展示流程中的表单数据的处理业务，流程的启动表单，任务办理表单等数据，<br/>支持动态表单及外置表单的处理</p>
 * @Author Alcott Hawk
 * @Date 1/4/2017
 */
@Service
@Transactional
public class HtmlFormService {

    public static final String TASK = "task";
    public static final String START = "start";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FormService formService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    /**
     * 获取流程启动表单数据<br/>
     * 启动表单支持获取引擎表单及外置表单
     * @param processDefinitionId <br/>流程定义id
     * @return 存有启动表单值的Map对象
     */
    public Map<String,Object> getStartForm(String processDefinitionId){
        if (StringUtils.isBlank(processDefinitionId)){
            throw new IllegalArgumentException("参数为空");
        }
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        boolean isFormkey = false;
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("objId",processDefinitionId);
        if (null != processDefinition){
            isFormkey = processDefinition.hasStartFormKey();
            if (isFormkey){
                Object form = formService.getRenderedStartForm(processDefinitionId);
                result.put("form",form);
                result.put("isFormKey",isFormkey);
                return result;
            } else {
                StartFormDataImpl formData = (StartFormDataImpl) formService.getStartFormData(processDefinitionId);
                result.put("form",formData);
                result.put("isFormKey",isFormkey);
                return result;
            }
        }
        return null;
    }

    /**
     * 提交表单数据到流程引擎
     * @param type <br/>
     *             <p>提交表单类型，为start或者task</p>
     * @param objId <br/>
     *              <p>id为流程定义id或者任务id，在type为start时为流程定义id，type为task时为任务id</p>
     * @param params
     * @return
     */
    public boolean submitForm(String type,String objId,Map<String,String[]> params){
        Set<Map.Entry<String,String[]>> entrySet = params.entrySet();
        Map<String,  String> formData=new HashMap<String,String>();
        for (Map.Entry<String, String[]> entry : entrySet) {
            String key= entry.getKey();
            String value[]= entry.getValue();
            formData.put(key, value[0]);
        }
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        identityService.setAuthenticatedUserId(shiroUser.id);
        if (type.equals(TASK)){
            formService.submitTaskFormData(objId,formData);
        } else if (type.equals(START)){
            formService.submitStartFormData(objId, formData);
        } else {
            return false;
        }
        try {
            return true;
        } catch (Exception e){
            logger.error("流程启动失败："+e.getMessage());
            return false;
        }
    }

    /**
     * 获取流程任务表单数据
     * @param taskId <br/>任务id
     * @return 存有任务表单值的Map对象

     */
    public Map<String, Object> getTaskForm(String taskId) {
        if (StringUtils.isBlank(taskId)){
            throw new IllegalArgumentException("参数为空");
        }
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (null != task){
            TaskFormDataImpl taskFormData = (TaskFormDataImpl) formService.getTaskFormData(taskId);
            Map<String, Object> result = new HashMap<String, Object>();
            boolean isFormkey = false;
            if (null != taskFormData){
                result.put("isFormkey",isFormkey);
                result.put("form",taskFormData);
                return result;
            } else {
                isFormkey = true;
                Object taskForm = formService.getRenderedTaskForm(taskId);
                result.put("isFormkey",isFormkey);
                result.put("form",taskForm);
                return result;
            }
        } else {
            return null;
        }
    }

    /**
     * 装入表单属性值到Map中
     * @param formProperties <br/>具有表单属性值的集合
     * @return 存有表单属性值的Map对象
     */
    private Map<String, Object> putData(List<FormProperty> formProperties){
        Map<String, Object> result = new HashMap<String, Object>();
        for (FormProperty formProperty : formProperties) {
            Map<String, String> values = (Map<String, String>) formProperty.getType().getInformation("values");
            if (values != null) {
                for (Map.Entry<String,String> enumEntry : values.entrySet()) {
                    logger.debug("enum, key: {}, value: {}", enumEntry.getKey(), enumEntry.getValue());
                }
                result.put(formProperty.getId(), values);
            }
        }
        return result;
    }

}
