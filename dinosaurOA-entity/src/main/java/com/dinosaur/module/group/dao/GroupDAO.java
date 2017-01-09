package com.dinosaur.module.group.dao;

import com.dinosaur.module.group.entity.Group;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 用户组数据访问接口
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
public interface GroupDAO extends JpaSpecificationExecutor<Group>,PagingAndSortingRepository<Group,String>{

    @Query("SELECT g FROM Group g JOIN g.users u WHERE u.id = ?1")
    List<Group> getGroupByUser(String userId);

}
