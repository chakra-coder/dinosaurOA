package com.dinosaur.module.classroom.dao;

import com.dinosaur.module.classroom.entity.Classroom;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 班级数据访问接口
 * @Author Alcott Hawk
 * @Date 12/31/2016
 */
public interface ClassroomDao extends JpaSpecificationExecutor<Classroom>, PagingAndSortingRepository<Classroom,String>{

    /**
     * 查询创建者创建的班级信息
     * @param userId
     * @return
     */
    List<Classroom> findByCreateUser(String userId);

    /**
     * 挂起班级
     * @param id
     */
    @Query("UPDATE Classroom SET isSuspend = true WHERE id = ?1")
    void suspend(String id);

    /**
     * 激活班级
     * @param id
     */
    @Query("UPDATE Classroom SET isSuspend = false WHERE id = ?1")
    void active(String id);

}
