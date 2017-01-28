package com.dinosaur.module.job.dao;

import com.dinosaur.module.job.entity.Job;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 作业访问接口
 * <p>作业相关的CRUD接口，包括基本的作业业务数据访问</p>
 * @Author Alcott Hawk
 * @Date 1/2/2017
 * @version 1.0
 */
public interface JobDao extends JpaSpecificationExecutor<Job>, PagingAndSortingRepository<Job,String>{

    /**
     * 查询属于特定班级的作业集合
     * @param id
     * @return
     */
    @Query("SELECT j FROM Job j WHERE j.id = ?1")
    List<Job> findByClassRoom(String id);

    /**
     * 查询指定作者的作业
     * @param author
     * @return
     */
    List<Job> findByAuthor(String author);

}
