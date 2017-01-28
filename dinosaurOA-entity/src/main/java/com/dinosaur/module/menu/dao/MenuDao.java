package com.dinosaur.module.menu.dao;

import com.dinosaur.module.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 菜单数据访问接口
 * @Author Alcott Hawk
 * @Date 12/31/2016
 */
public interface MenuDao extends JpaSpecificationExecutor<Menu>, PagingAndSortingRepository<Menu,Integer>{

    @Query("FROM Menu")
    List<Menu> findAll();

}
