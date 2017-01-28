package com.dinosaur.module.classroom.entity;

import com.dinosaur.module.base.entity.IdEntity;
import javax.persistence.*;

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
    private String classTeacher;                      //班主任
    private String instructor;                        //辅导员
    private boolean isDelete;                         //是否删除
    private boolean isSuspend;                        //是否挂起
    private String themePic;                          //主题图

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

    @Column(nullable = false, length = 50)
    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    @Column(nullable = false, length = 50)
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
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    @Column(name = "isSuspend")
    public boolean isSuspend() {
        return isSuspend;
    }

    public void setSuspend(boolean suspend) {
        isSuspend = suspend;
    }

    public String getThemePic() {
        return themePic;
    }

    public void setThemePic(String themePic) {
        this.themePic = themePic;
    }
}
