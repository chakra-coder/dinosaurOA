package com.dinosaur.module.category.entity;

import com.dinosaur.module.group.entity.Group;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private Group group;

    private List<Category> children = new ArrayList<Category>();

    @Id
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

    @Column(nullable = false)
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Transient
    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "group_id" )
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
