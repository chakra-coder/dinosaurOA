package com.dinosaur.module.flowable.workflow;

import com.dinosaur.core.shiro.ShiroUser;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.impl.form.FormDataImpl;
import org.activiti.engine.impl.form.StartFormDataImpl;
import org.activiti.engine.impl.form.TaskFormDataImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

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
    private ProcessService processService;

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
                result.put("form",this.load(formData));
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
    public boolean submitForm(String type,String objId,Map<String,String[]> params, String[] attachments){
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
            if (attachments.length>0){
                this.createAttachment(Arrays.asList(attachments), objId, false);
            }
        } else if (type.equals(START)){
            ProcessInstance processInstance = formService.submitStartFormData(objId, formData);
            if (attachments.length>0){
                String pid = processInstance.getProcessInstanceId();
                this.createAttachment(Arrays.asList(attachments), pid, true);
            }
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
     * 执行一个任务的办理
     * @param taskId 任务id
     * @param param 任务表单参数
     * @param attachments 附件
     * @return
     */
    public boolean doTask(String taskId, Map<String, String[]> param, String[] attachments){
        if (processService.doTask(taskId, param)){
            this.createAttachment(Arrays.asList(attachments), taskId, false);
            return true;
        } else {
            return  false;
        }
    }

    /**
     * 查询任务流程附件
     * @param id 流程实例id或者任务id
     * @return
     */
    public List<Attachment> getAttachment(String id){
        String pid = taskService.createTaskQuery().taskId(id).singleResult().getProcessInstanceId();
        List<Attachment> attachments = taskService.getProcessInstanceAttachments(pid);
        return  attachments;
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
            result.put("objId",taskId);
            boolean isFormkey = false;
            if (null != taskFormData){
                result.put("isFormkey",isFormkey);
                result.put("form",this.load(taskFormData));
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
     * 表单属性值装入
     * @param formProperty
     * @return
     */
    private List loadvalue(List<FormProperty> formProperty){
        Map<String, Object> result = null;
        List formproperties = new ArrayList();
        for (FormProperty v : formProperty){
            result = new HashMap<String, Object>();
            Map<String, String> values = (Map<String, String>) v.getType().getInformation("values");
            if (values != null) {
                List enums = new ArrayList();
                Map value = null;
                for (String key : values.keySet()){
                    value = new HashMap();
                    Object objvalue = values.get(key);
                    value.put("key",key);
                    value.put("value",objvalue);
                    enums.add(value);
                }
                result.put("value",enums);
            } else {
                result.put("value",v.getValue());
            }
            result.put("id",v.getId());
            result.put("name",v.getName());
            result.put("type",v.getType().getName());
            result.put("readable",v.isReadable());
            result.put("required",v.isRequired());
            result.put("writable",v.isWritable());
            formproperties.add(result);
        }
        return formproperties;
    }

    /**
     * 加载表单数据
     * @param t
     * @param <T>
     * @return
     * @throws IOException
     */
    private <T extends FormDataImpl> Map<String, Object> load(T t){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("formKey",t.getFormKey());
        result.put("deploymentId",t.getDeploymentId());
        result.put("formProperties",this.loadvalue(t.getFormProperties()));
        if (t instanceof StartFormData) {
            StartFormDataImpl startFormData = (StartFormDataImpl)t;
            result.put("processDefinition",startFormData.getProcessDefinition());
        } else {
            TaskFormDataImpl taskFormData = (TaskFormDataImpl)t;
            result.put("task",taskFormData.getTask());
        }
        return result;
    }

    /**
     * 添加附件到流程
     * @param sources 附件数据
     * @param id taskid或者流程实例id
     * @param isStart 是否是启动流程
     * @return
     */
    private List<Attachment> createAttachment(List<String> sources, String id, boolean isStart){
        List<Attachment> attachments = new ArrayList<>();
        sources.forEach(n->{
            String suffix = StringUtils.substringAfterLast(n, ".");
            String name = StringUtils.substringAfterLast(n, "/");
            String description = StringUtils.substringBefore(name, ".");
            Attachment attachment = null;
            if (isStart){
                String taskId = taskService.createTaskQuery().processInstanceId(id).singleResult().getId();
                attachment = taskService.createAttachment(suffix, taskId, id, name, description, n);
            } else {
                String pid = taskService.createTaskQuery().taskId(id).singleResult().getProcessInstanceId();
                attachment = taskService.createAttachment(suffix, id, pid, name, description , n);
            }
            attachments.add(attachment);
        });
        return attachments;
    }

}
