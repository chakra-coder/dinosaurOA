package com.dinosaur.module.category;

import com.dinosaur.module.category.dao.CategoryDao;
import com.dinosaur.module.category.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类service
 * @Author Alcott Hawk
 * @Date 1/13/2017
 */
@Service
@Transactional
public class CategoryService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CategoryDao categoryDao;

    public List<Category> getAll(){
        List<Category> menus = (List<Category>) categoryDao.findAll();
        List<Category> menuList = new ArrayList<Category>();
        menus.forEach(v->{
            if (v.getParentId() == 0){
                List<Category> children = findChildren(menus,v.getId());
                v.setChildren(children);
                menuList.add(v);
            }
        });
        return menuList;
    }

    /**
     * 保存一个分类信息
     * @param category
     * @param pid
     * @return
     */
    public boolean create(Category category,Integer pid){
        if (null == pid){
            return false;
        }
        category.setParentId(pid);
        categoryDao.save(category);
        return true;
    }

    /**
     * 删除一个分类信息
     * @param id
     * @return
     */
    public boolean delete(Integer id){
        if (null == id){
            return false;
        }
        categoryDao.delete(id);
        return true;
    }

    /**
     * 查询分类是否正在使用
     * @param id 分类
     * @return
     */
    public boolean isUseing(Integer id){
        // TODO 查询分类是否使用
        return true;
    }

    /**
     * 是否包含子分类
     * @param id
     * @return
     */
    public int hasChildren(Integer id){
        if (null == id){
            throw new IllegalArgumentException("id is null");
        }
        return categoryDao.countByParentId(id);
    }

    /**
     * 查询子分类
     * @param categories
     * @param id
     * @return
     */
    private List<Category> findChildren(List<Category> categories,int id){
        List<Category> children = new ArrayList<Category>();
        categories.forEach(v->{
            if (v.getParentId() != 0 && v.getParentId() == id){
                v.setChildren(this.findChildren(categories,v.getId()));
                children.add(v);
            }
        });
        return children;
    }

}
