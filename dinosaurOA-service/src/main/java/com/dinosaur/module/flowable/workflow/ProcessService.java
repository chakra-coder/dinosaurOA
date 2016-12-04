package com.dinosaur.module.flowable.workflow;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * 查询流程定义，并提供分页
     * @param pageSize 页面大小
     * @param pageNo 页码
     * @return
     */
    public List<ProcessDefinition> getProcessDefinition(int pageSize, int pageNo){
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().active().
                orderByProcessDefinitionName().desc().listPage(pageNo,pageSize);
        return processDefinitions;
    }

    /**
     * 查询流程实例，并提供分页
     * @param pageSize 页面大小
     * @param pageNo 页码
     * @return
     */
    public List<ProcessInstance> getProcessInstance(int pageSize, int pageNo){
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().orderByProcessDefinitionKey().desc().listPage(pageNo,pageSize);
        return  processInstances;
    }

}
