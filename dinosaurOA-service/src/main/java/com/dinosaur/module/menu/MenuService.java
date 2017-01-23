package com.dinosaur.module.menu;

import com.dinosaur.module.menu.dao.MenuDao;
import com.dinosaur.module.menu.entity.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单业务类
 * @Author Alcott Hawk
 * @Date 1/2/2017
 */
@Service
public class MenuService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuDao menuDao;

    /**
     * 添加一个菜单
     * @param menu
     */
    public boolean add(Menu menu,Integer pid){
        if (null == pid){
            return false;
        }
        menu.setParentId(pid);
        menuDao.save(menu);
        return true;
    }

    /**
     * 删除一个菜单
     * @param id
     * @return
     */
    public boolean delete(Integer id){
        if (null == id){
            return false;
        }
        Menu menu = menuDao.findOne(id);
        if (menu.getGroups().size()>0){
            return false;
        }
        menuDao.delete(id);
        return true;
    }

    /**
     * 更新一个菜单
     * @param menu
     */
    public Menu update(Menu menu){
        return menuDao.save(menu);
    }

    /**
     * 获取所有分类信息
     * @return
     */
    public List getAll(){
        List<Menu> menus = menuDao.findAll();
        List<Map> menuList = new ArrayList<Map>();
        for (Menu menu : menus){
            if (menu.getParentId() == 0){
                List children = findChildren(menus,menu.getId());
                this.loadData(menuList,menu, children);
            }
        }
        return menuList;
    }

    /**
     * 查询子菜单
     * @param menus
     * @param id
     * @return
     */
    private List findChildren(List<Menu> menus,int id){
        List<Map> menuList = new ArrayList<Map>();
        for(Menu menu : menus){
            if (menu.getParentId() == id){
                List children = findChildren(menus,menu.getId());
                this.loadData(menuList,menu, children);
            }
        }
        return menuList;
    }

    /**
     * 添加分类数据到集合
     * @param menuList 分类目标集合
     * @param menu 分类实体
     * @param children 子分类
     */
    private void loadData(List<Map> menuList, Menu menu, List children){
        Map map = new HashMap();
        map.put("name",menu.getName());
        map.put("id",menu.getId());
        map.put("url",menu.getUrl());
        map.put("themePic",menu.getThemePic());
        map.put("children",children);
        menuList.add(map);
    }

}
