package com.dinosaur.module.group;

import com.dinosaur.module.group.dao.GroupDAO;
import com.dinosaur.module.user.dao.UserDAO;
import com.dinosaur.module.user.entity.User;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 用户组实体管理
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
public class GroupEntityManager extends org.activiti.engine.impl.persistence.entity.GroupEntityManager{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private UserDAO userDAO;

    /**
     * 通过用户id查询给定用户所属的用户组，因为自定义了系统的用户组，所以重写activiti的此方法
     * @param userId 用户id
     * @return 用户组的集合
     */
    @Override
    public List<Group> findGroupsByUser(String userId) {
        User user = userDAO.findOne(userId);
        Set<com.dinosaur.module.group.entity.Group> groups = user.getGroups();
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
