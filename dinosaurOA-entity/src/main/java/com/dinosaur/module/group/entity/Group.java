package com.dinosaur.module.group.entity;

import com.dinosaur.module.base.entity.IdEntity;
import com.dinosaur.module.category.entity.Category;
import com.dinosaur.module.menu.entity.Menu;
import com.dinosaur.module.user.entity.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统自定义用户组实体，继承自IdEntity。
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
@Table(name = "d_group")
@Entity
public class Group extends IdEntity{

    private String name;
    private String englishName;
    private Set<User> users = new HashSet<User>();
    private Set<Menu> menus = new HashSet<Menu>();
    private Set<Category> categories = new HashSet<Category>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "group", cascade = CascadeType.ALL)
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @ManyToMany(mappedBy = "groups",fetch = FetchType.EAGER)
    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    @ManyToMany(mappedBy = "groups",fetch = FetchType.EAGER)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Column(nullable = false,length = 15)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false,length = 50)
    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
}
