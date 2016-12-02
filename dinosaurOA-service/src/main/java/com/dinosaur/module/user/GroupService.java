package com.dinosaur.module.user;

import com.dinosaur.module.group.dao.GroupDAO;
import com.dinosaur.module.group.entity.Group;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户组service
 * @Author Alcott Hawk
 * @Date 12/2/2016
 */
@Service
@Transactional
public class GroupService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GroupDAO groupDAO;

    /**
     * 查询一个组通过特定的id
     * @param id 组id
     * @return
     */
    public Group findById(String id){
        return groupDAO.findOne(id);
    }

    /**
     * 保存一个组信息
     * @param group
     * @param <S>
     * @return
     */
    public <S extends T> Group add(Group group){
         return groupDAO.save(group);
    }

    /**
     * 删除一个用户组
     * @param id 用户组id
     * @return
     */
    public boolean delete(String id){
        groupDAO.delete(id);
        return true;
    }

}
