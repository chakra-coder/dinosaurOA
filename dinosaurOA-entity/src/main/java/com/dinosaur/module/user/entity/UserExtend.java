package com.dinosaur.module.user.entity;

import com.dinosaur.module.base.entity.IdEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 用户扩展数据实体
 * @Author Alcott Hawk
 * @Date 1/5/2017
 */
@Entity
@Table(name = "d_user_extend")
public class UserExtend extends IdEntity{

    private String userId;
    private User user;

    @OneToOne(mappedBy = "userExtend", cascade = {CascadeType.MERGE,CascadeType.PERSIST}, optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
