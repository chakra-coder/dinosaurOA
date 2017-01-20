package com.dinosaurOA.test.service;

import com.dinosaur.core.util.StringUtil;
import com.dinosaur.module.category.CategoryService;
import com.dinosaur.module.category.entity.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 分类service测试
 * @Author Alcott Hawk
 * @Date 1/20/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/application.xml","classpath*:/activiti-cfg.xml"})
@Transactional
@Rollback(true)
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类树数据获取测试
     */
    @Test
    public void getCategoryTest(){
        List<Category> categories = categoryService.getAll();
        try {
            System.out.println(StringUtil.convertObjectToString(categories));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
