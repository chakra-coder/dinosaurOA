package com.dinosaur.module.user;

import com.dinosaur.module.user.dao.UserDAO;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户实体管理
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
public class UserEntityManager extends org.activiti.engine.impl.persistence.entity.UserEntityManager{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDAO userDAO;

    /**
     * 查询一个用户通过用户id
     * @param userId 用户id
     * @return activiti的用户对象
     */
    @Override
    public User findUserById(String userId) {
        com.dinosaur.module.user.entity.User user = userDAO.findOne(userId);
        User activitiUser = new UserEntity();
        activitiUser.setId(user.getId());
        activitiUser.setEmail(user.getEmail());
        activitiUser.setPassword(user.getPassword());
        return activitiUser;
    }

    /**
     * 通过用户id查询给定用户所属的用户组，因为自定义了系统的用户组，所以重写activiti的此方法
     * @param userId 用户id
     * @return 用户组的集合
     */
    @Override
    public List<Group> findGroupsByUser(String userId) {
        com.dinosaur.module.user.entity.User user = userDAO.findOne(userId);
        List<com.dinosaur.module.group.entity.Group> groups = user.getGroups();
        // maybe have a batter way.
        List<Group> activitiGroups = new ArrayList<Group>();
        Group activitiGroup = null;
        for (com.dinosaur.module.group.entity.Group group : groups){
            activitiGroup = new GroupEntity();
            activitiGroup.setName(group.getName());
            activitiGroup.setId(group.getId());
            activitiGroup.setType(group.getName());
            activitiGroups.add(activitiGroup);
        }
        return activitiGroups;
    }
}
