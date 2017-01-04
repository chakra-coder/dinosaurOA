package com.dinosaur.module.flowable.workflow;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.impl.form.StartFormDataImpl;
import org.activiti.engine.impl.form.TaskFormDataImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程表单业务
 * <p>展示流程中的表单数据的处理业务，流程的启动表单，任务办理表单等数据，<br/>支持动态表单及外置表单的处理</p>
 * @Author Alcott Hawk
 * @Date 1/4/2017
 */
@Service
@Transactional
public class HtmlFormService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FormService formService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

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
