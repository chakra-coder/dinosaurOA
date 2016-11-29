package com.dinosaur.module.group;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户组会话工厂
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
public class GroupEntityManagerFactory implements SessionFactory {

    private GroupEntityManager groupEntityManager;

    @Override
    public Class<?> getSessionType() {
        return GroupIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return groupEntityManager;
    }

    @Autowired
    public void setGroupEntityManager(GroupEntityManager groupEntityManager){
        this.groupEntityManager = groupEntityManager;
    }
}
