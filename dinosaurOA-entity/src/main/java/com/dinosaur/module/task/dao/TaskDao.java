package com.dinosaur.module.task.dao;

import com.dinosaur.module.task.entity.Task;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 作业访问接口
 * <p>作业相关的CRUD接口，包括基本的作业业务数据访问</p>
 * @Author Alcott Hawk
 * @Date 1/2/2017
 * @version 1.0
 */
public interface TaskDao extends JpaSpecificationExecutor<Task>, PagingAndSortingRepository<Task,String>{
}
