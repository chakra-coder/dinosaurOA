package com.dinosaur.module.classroom.entity;

import com.dinosaur.module.base.entity.IdEntity;
import com.dinosaur.module.job.entity.Job;
import com.dinosaur.module.user.entity.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 班级实体类
 * @Author Alcott Hawk
 * @Date 12/30/2016
 */
@Entity
@Table(name = "d_classroom")
public class Classroom extends IdEntity{

    private String name;                              //名称
    private String createTime;                        //创建日期
    private Set<User> students = new HashSet<User>(); //学生
    private String classTeacher;                      //班主任
    private String instructor;                        //辅导员
    private Set<Job> jobs = new HashSet<Job>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "classroom", cascade = CascadeType.ALL)
    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs (Set<Job> jobs) {
        this.jobs = jobs;
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
    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
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
