package com.dinosaur.module.category.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 流程分类实体
 * @Author Alcott Hawk
 * @Date 12/30/2016
 */
@Table(name = "d_category")
@Entity
public class Category {

    @Id
    private int id;
    private String name;
    private String categoryPath;
    private int parentId;

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
}
