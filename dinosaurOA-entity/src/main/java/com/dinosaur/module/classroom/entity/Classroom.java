package com.dinosaur.module.classroom.entity;

import com.dinosaur.module.base.entity.IdEntity;
import com.dinosaur.module.task.entity.Task;
import com.dinosaur.module.user.entity.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 教师实体类
 * @Author Alcott Hawk
 * @Date 12/30/2016
 */
@Entity
@Table(name = "d_classroom")
public class Classroom extends IdEntity{

    private String name;                              //名称
    private String createTime;                        //创建日期
    private Set<User> users = new HashSet<User>();    //学生
    private String classTeacher;                      //班主任
    private String instructor;                        //辅导员
    private Set<Task> tasks;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    @Column(nullable = false,length = 20)
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

    @ManyToMany(mappedBy = "classrooms",fetch = FetchType.EAGER)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Column(nullable = false)
    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
