package com.dinosaurOA.test.service;

import com.dinosaur.core.util.StringUtil;
import com.dinosaur.module.flowable.workflow.ProcessService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.form.TaskFormDataImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Alcott Hawk
 * @Date 1/9/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/application.xml","classpath*:/activiti-cfg.xml"})
@Transactional
@Rollback(true)
public class ProcessServiceTest {

    @Autowired
    private ProcessService processService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Test
    public void formConvertTest(){
        try {
        TaskFormDataImpl taskFormData = (TaskFormDataImpl) formService.getTaskFormData("1d23bbbb-d644-11e6-be81-005056c00008");
            String str = StringUtil.convertObjectToString(taskFormData.getFormProperties());
            System.out.println(str);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void beanToMapTest(){
    }


}
