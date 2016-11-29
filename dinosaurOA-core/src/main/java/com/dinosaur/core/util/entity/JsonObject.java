package com.dinosaur.core.util.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * json格式规范返回数据类型实体
 * @Author Alcott Hawk
 * @Date 11/28/2016
 */
public class JsonObject implements Serializable{

    private int state;
    private Objects data;
    private String message;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Objects getData() {
        return data;
    }

    public void setData(Objects data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
