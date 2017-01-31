package com.dinosaur.module.classroom.entity;

import com.dinosaur.module.base.entity.IdEntity;
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
@Table(name = "d_class")
public class Classroom extends IdEntity{

    private String name;                              //名称
    private String createTime;                        //创建日期
    private String endTime;                           //班级截至日期
    private Set<User> students = new HashSet<User>(); //学生
    private String classTeacher;                      //班主任
    private String instructor;                        //辅导员
    private boolean delete;                         //是否删除
    private boolean suspend;                        //是否挂起
    private String themePic;                          //主题图
    private String createUser;                        //创建者

    public Classroom(){
    }

    public Classroom(String id){
        this.id = id;
    }

    @ManyToMany(mappedBy = "calssrooms", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    @Column(nullable = false,length = 20, name = "name")
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Column(name = "isDelete")
    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    @Column(name = "isSuspend")
    public boolean isSuspend() {
        return suspend;
    }

    public void setSuspend(boolean suspend) {
        this.suspend = suspend;
    }

    public String getThemePic() {
        return themePic;
    }

    public void setThemePic(String themePic) {
        this.themePic = themePic;
    }

    @Column(length = 50, nullable = false)
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

}
