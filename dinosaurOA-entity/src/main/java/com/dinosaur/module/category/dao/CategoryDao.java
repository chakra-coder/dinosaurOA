package com.dinosaur.module.category.dao;

import com.dinosaur.module.category.entity.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 分类数据访问层
 * @Author Alcott Hawk
 * @Date 12/30/2016
 */
public interface CategoryDao extends JpaSpecificationExecutor<Category>, PagingAndSortingRepository<Category,Integer>{
}
