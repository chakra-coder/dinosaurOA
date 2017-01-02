package com.dinosaur.module.menu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单实体
 * @Author Alcott Hawk
 * @Date 12/30/2016
 */
@Entity
@Table(name = "d_menu")
public class Menu {

    private int id;
    private String nmae;
    private int parnetId;
    private String url;
    private String group;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false, length = 15)
    public String getNmae() {
        return nmae;
    }

    public void setNmae(String nmae) {
        this.nmae = nmae;
    }

    @Column(nullable = false)
    public int getParnetId() {
        return parnetId;
    }

    public void setParnetId(int parnetId) {
        this.parnetId = parnetId;
    }

    @Column(nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
