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

    /**
     * 获取所有的分类信息
     * @return
     */
    public List getAll(){
        List<Category> menus = categoryDao.findAll();
        List<Map> menuList = new ArrayList<Map>();
        for (Category category : menus){
            if (category.getParentId() == 0){
                List children = findChildren(menus,category.getId());
                this.loadData(menuList,category, children);
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
        List<Map> menuList = new ArrayList<Map>();
        for(Category category : categories){
            if (category.getParentId() == id){
                List children = findChildren(categories,category.getId());
                this.loadData(menuList,category, children);
            }
        }
        return menuList;
    }

    /**
     * 添加分类数据到集合
     * @param menuList 分类目标集合
     * @param category 分类实体
     * @param children 子分类
     */
    private void loadData(List<Map> menuList, Category category, List children){
        Map map = new HashMap();
        map.put("name",category.getName());
        map.put("id",category.getId());
        map.put("children",children);
        if (children.size()>0){
            map.put("open",true);
        }
        menuList.add(map);
    }

}
