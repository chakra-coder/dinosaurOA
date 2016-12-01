package com.dinosaur.module.user.entity;

import com.dinosaur.module.base.entity.IdEntity;
import com.dinosaur.module.group.entity.Group;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private List<Group> groups = new ArrayList<Group>();

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    @ManyToMany(mappedBy = "users")
    @OrderBy("id")
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
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
