package com.dinosaurOA.test.service;

import com.dinosaur.module.group.entity.Group;
import com.dinosaur.module.user.GroupService;
import com.dinosaur.module.user.dao.UserDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户组service单元测试类
 * @Author Alcott Hawk
 * @Date 1/9/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/application.xml","classpath*:/activiti-cfg.xml"})
@Transactional
@Rollback(true)
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserDAO userDAO;

    /**
     * 利用用户id查询用户所属组集合
     */
    @Test
    public void getGroupByUserTest(){
        List<Group> groups = groupService.getGroupByUser("cc5af70d-9fa1-427a-96b5-5f0e895fe6f1");
        System.out.println(groups.size());
    }

}
