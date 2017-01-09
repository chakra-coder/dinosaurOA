package com.dinosaurOA.test.service;

import com.dinosaur.module.flowable.workflow.ProcessService;
import com.dinosaur.module.system.construction.Construction;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Test
    public void getTaskTest(){
        List<Task> tasks = processService.getTask(Construction.PAGE_SIZE,Construction.PAGE_NO,"teacher");
        System.out.println(tasks.size());
    }

}
