package com.dinosaur.core.util.entity;

import java.io.Serializable;

/**
 * json格式规范返回数据类型实体
 * @Author Alcott Hawk
 * @Date 11/28/2016
 */
public class JsonObject implements Serializable{

    private int state;
    private Object data;
    private String message;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
