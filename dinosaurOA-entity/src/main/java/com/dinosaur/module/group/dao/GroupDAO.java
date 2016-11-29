package com.dinosaur.module.group.dao;

import com.dinosaur.module.group.entity.Group;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 用户组数据访问接口
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
public interface GroupDAO extends JpaSpecificationExecutor<Group>,PagingAndSortingRepository<Group,String>{
}
