package com.dinosaur.core.util;

import com.dinosaur.core.util.entity.JsonObject;

import java.util.Objects;

/**
 * json规范格式返回数据工具类
 * @Author Alcott Hawk
 * @Date 11/28/2016
 */
public class JsonResultUtil {

    private static final int errorState = -1;
    private static final int successState = 1;

    /**
     * 获取包含错误信息的json数据
     * @param message 错误信息
     * @return 复合系统规范的json数据
     */
    public static JsonObject getErrorJson(String message){
        JsonObject jsonObject = new JsonObject();
        jsonObject.setState(errorState);
        jsonObject.setMessage(message);
        return jsonObject;
    }

    /**
     * 获取包含成功信息的json数据
     * @param message 错误信息
     * @return 复合系统规范的json数据
     */
    public static JsonObject getSuccessJson(String message){
        JsonObject jsonObject = new JsonObject();
        jsonObject.setState(successState);
        jsonObject.setMessage(message);
        return jsonObject;
    }

    /**
     * 获取对象的json数据，接送的状态值为成功：数值为1
     * @param data 对象数据
     * @return 复合系统规范的json数据
     */
    public static JsonObject getObjectJson(Objects data){
        JsonObject jsonObject = new JsonObject();
        jsonObject.setState(successState);
        jsonObject.setData(data);
        return jsonObject;
    }

}
