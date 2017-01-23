package com.dinosaur.module.menu.entity;

import com.dinosaur.module.group.entity.Group;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单实体
 * @Author Alcott Hawk
 * @Date 12/30/2016
 */
@Entity
@Table(name = "d_menu")
public class Menu {

    private int id;
    private String name;
    private int parentId;
    private String url;
    private String menuPath;
    private String themePic;
    private Set<Group> groups = new HashSet<Group>();

    private List<Menu> children = new ArrayList<Menu>();

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false, length = 15)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(columnDefinition = "int default 0", nullable = false)
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Column(nullable = false,length = 50)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="d_group_menu",
            joinColumns = @JoinColumn(name="menu_id"),
            inverseJoinColumns = @JoinColumn(name="group_id"))
    @OrderBy("id")
    @Fetch(FetchMode.JOIN)
    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Transient
    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    @Column(nullable = false)
    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public String getThemePic() {
        return themePic;
    }

    public void setThemePic(String themePic) {
        this.themePic = themePic;
    }
}
