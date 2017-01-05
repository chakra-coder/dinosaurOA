package com.dinosaur.module.task.entity;

import com.dinosaur.module.base.entity.IdEntity;
import com.dinosaur.module.user.entity.User;

import javax.persistence.*;

/**
 * 作业实体类
 * @Author Alcott Hawk
 * @Date 12/31/2016
 * @version 1.0
 */
@Entity
@Table(name = "d_task")
public class Task extends IdEntity{

    private String name;              //名称
    private String description;       //说明
    private String createTime;        //创建日期
    private int duration;             //持续时间
    private String endTime;           //结束时间
    private String content;           //内容
    private String authot;            //作者
    private String classroom;         //班级
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "user_id" )
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthot() {
        return authot;
    }

    public void setAuthot(String authot) {
        this.authot = authot;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    @Column(nullable = false,length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
