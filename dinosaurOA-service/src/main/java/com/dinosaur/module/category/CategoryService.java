package com.dinosaur.module.category;

import com.dinosaur.module.category.dao.CategoryDao;
import com.dinosaur.module.category.entity.Category;
import com.dinosaur.module.group.dao.GroupDAO;
import com.dinosaur.module.group.entity.Group;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Autowired
    private GroupDAO groupDAO;

    public List getAll(){
        List<Category> menus = categoryDao.findAll();
        List<Map> menuList = new ArrayList<Map>();
        Map categoryMap = null;
        for (Category category : menus){
            if (category.getParentId() == 0){
                categoryMap = new HashMap();
                categoryMap.put("name",category.getName());
                categoryMap.put("id",category.getId());
                categoryMap.put("children",findChildren(menus,category.getId()));
                menuList.add(categoryMap);
            }
        }
        return menuList;
    }

    /**
     * 保存一个分类信息
     * @param groupId
     * @param name
     * @param pid
     * @return
     */
    public boolean create(String groupId, String name, String pid){
        if (null == pid){
            return false;
        }
        Category category = null;
        if (StringUtils.isNoneBlank(groupId)&&StringUtils.isNoneBlank(name)) {
            Group group = groupDAO.findOne(groupId);
            category = new Category();
            category.setName(name);
            Set<Group> groups = new HashSet<Group>();
            groups.add(group);
            category.setGroups(groups);
            if (StringUtils.isBlank(pid)){
                category.setCategoryPath("|");
            } else {
                Category categoryParent = categoryDao.findOne(Integer.valueOf(pid));
                if (null != categoryParent){
                    category.setCategoryPath(categoryParent.getCategoryPath()+categoryParent.getId()+"|");
                    category.setParentId(categoryParent.getParentId());
                } else {
                    return false;
                }
            }
            categoryDao.save(category);
            groupDAO.save(group);
            return true;
        } else {
            return false;
        }

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
    private List findChildren(List<Category> categories,int id){
        Map list = new HashMap();
        Map<String,Object> children = new HashMap<String,Object>();
        List<Map> menuList = new ArrayList<Map>();
        for(Category category : categories){
            if (category.getParentId() == id){
                list = new HashMap();
                list.put("name",category.getName());
                list.put("id",category.getId());
                list.put("children",this.findChildren(categories,category.getId()));
                menuList.add(list);
            }
        }
        return menuList;
    }

}
