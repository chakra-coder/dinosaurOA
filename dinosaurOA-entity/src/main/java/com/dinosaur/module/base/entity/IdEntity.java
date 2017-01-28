package com.dinosaur.module.base.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * ID基类
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
@MappedSuperclass
public class IdEntity {

    protected  String id;

    @Id
    @GenericGenerator(name="uuidGenerator", strategy="uuid2")
    @GeneratedValue(generator = "uuidGenerator")
    @Column(name = "id", nullable = false, unique = true, length = 50)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
