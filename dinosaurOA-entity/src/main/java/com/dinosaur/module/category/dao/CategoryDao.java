package com.dinosaur.module.category.dao;

import com.dinosaur.module.category.entity.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 分类数据访问层
 * @Author Alcott Hawk
 * @Date 12/30/2016
 */
public interface CategoryDao extends JpaSpecificationExecutor<Category>, PagingAndSortingRepository<Category,Integer>{

    /**
     * 根据父id统计分类数量
     * @param pid 父id
     * @return
     */
    @Query("SELECT COUNT(1) FROM Category c WHERE c.parentId =?1")
    int countByParentId(Integer pid);

    @Query("FROM Category")
    List<Category> findAll();


}
