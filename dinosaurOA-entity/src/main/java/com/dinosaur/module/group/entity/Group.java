package com.dinosaur.module.group.entity;

import com.dinosaur.module.base.entity.IdEntity;
import com.dinosaur.module.user.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<User> users = new ArrayList<User>();

    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "groups",fetch = FetchType.LAZY)
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
}
