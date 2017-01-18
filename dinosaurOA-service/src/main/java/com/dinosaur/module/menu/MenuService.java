package com.dinosaur.module.menu;

import com.dinosaur.module.menu.dao.MenuDao;
import com.dinosaur.module.menu.entity.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<Menu> getAll(){
        List<Menu> menus = (List<Menu>) menuDao.findAll();
        List<Menu> menuList = new ArrayList<Menu>();
        menus.forEach(v->{
            if (v.getParentId() == 0){
                List<Menu> children = findChildren(menus,v.getId());
                v.setChildren(children);
                menuList.add(v);
            }
        });
        return menuList;
    }

    /**
     * 查询子菜单
     * @param menus
     * @param id
     * @return
     */
    private List<Menu> findChildren(List<Menu> menus,int id){
        List<Menu> children = new ArrayList<Menu>();
        menus.forEach(v->{
            if (v.getParentId() != 0 && v.getParentId() == id){
                v.setChildren(this.findChildren(menus,v.getId()));
                children.add(v);
            }
        });
        return children;
    }

}
