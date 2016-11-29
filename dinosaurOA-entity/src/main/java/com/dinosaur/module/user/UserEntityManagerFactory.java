package com.dinosaur.module.user;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户会话工厂
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
public class UserEntityManagerFactory implements SessionFactory{

    private UserEntityManager userEntityManager;

    @Override
    public Class<?> getSessionType() {
        return UserIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return userEntityManager;
    }

    @Autowired
    public void setUserEntityManager(UserEntityManager userEntityManager){
        this.userEntityManager = userEntityManager;
    }
}
