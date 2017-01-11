package com.dinosaurOA.test.service;

import com.dinosaur.module.flowable.workflow.HtmlFormService;
import com.dinosaur.module.flowable.workflow.ProcessService;
import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

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
    private HtmlFormService htmlFormService;


    @Autowired
    private FormService formService;

    /**
     * 表单类型转换测试
     */
    @Test
    public void formConvertTest() {
            Map data = htmlFormService.getTaskForm("1d23bbbb-d644-11e6-be81-005056c00008");
            System.out.println(data);
        }

}
