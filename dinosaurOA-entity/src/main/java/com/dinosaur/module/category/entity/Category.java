package com.dinosaur.module.category.entity;

import com.dinosaur.module.group.entity.Group;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

/**
 * 流程分类实体
 * @Author Alcott Hawk
 * @Date 12/30/2016
 */
@Table(name = "d_category")
@Entity
public class Category {


    private int id;
    private String name;
    private String categoryPath;
    private int parentId;

    private Set<Group> groups = new HashSet<Group>();

    private Map<String,List<Category>> children = new HashMap<String,List<Category>>();

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Transient
    public Map<String,List<Category>> getChildren() {
        return children;
    }

    public void setChildren(Map<String,List<Category>> children) {
        this.children = children;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="d_group_category",
            joinColumns = @JoinColumn(name="category_id"),
            inverseJoinColumns = @JoinColumn(name="group_id"))
    @OrderBy("id")
    @Fetch(FetchMode.JOIN)
    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

}
