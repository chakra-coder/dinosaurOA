package com.dinosaur.module.classroom.dao;

import com.dinosaur.module.classroom.entity.Classroom;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 班级数据访问接口
 * @Author Alcott Hawk
 * @Date 12/31/2016
 */
public interface ClassroomDao extends JpaSpecificationExecutor<Classroom>, PagingAndSortingRepository<Classroom,String>{
}
