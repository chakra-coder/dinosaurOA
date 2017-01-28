package com.dinosaur.module.user.entity;

import com.dinosaur.module.base.entity.IdEntity;
import com.dinosaur.module.group.entity.Group;
import com.dinosaur.module.job.entity.Job;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统自定义用户实体，继承自IdEntity。
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
@Table(name = "d_user")
@Entity
public class User extends IdEntity {

    private String name;
    private String nickName;
    private String password;
    private String salt;
    private String phone;
    private String pic;
    private String createDate;
    private UserExtend userExtend;

    private Set<Job> jobs = new HashSet<Job>();

    private Set<Group> groups = new HashSet<Group>();

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "userExtend_id")
    @Fetch(FetchMode.JOIN)
    public UserExtend getUserExtend() {
        return userExtend;
    }

    public void setUserExtend(UserExtend userExtend) {
        this.userExtend = userExtend;

    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="d_user_group",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="group_id"))
    @OrderBy("id")
    @Fetch(FetchMode.JOIN)
    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Column(unique = true)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setEmail(String email) {

    }

    public String getEmail() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
