package com.dinosaur.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Map;

/**
 * 字符串工具类
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
public class StringUtil {

    private static SecureRandom secureRandom = new SecureRandom();
    private static ObjectMapper mapper = new ObjectMapper();
    public static final int BYTE_SIZE = 8;

    /**
     * 生成随机的Byte[]作为salt.
     *
     * @param numBytes byte数组的大小
     */
    public static byte[] generateSalt(int numBytes) {
        Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);

        byte[] bytes = new byte[numBytes];
        secureRandom.nextBytes(bytes);
        return bytes;
    }

    /**
     * 将jaon字符串转换为map集合
     * @param arg 将要转换的字符串
     * @param t map中值的类型
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> Map<String,T> convertStrinToMap(String arg,Class<T> t) throws IOException {
        Map<String,T> result = mapper.readValue(arg,Map.class);
        return result;
    }

    /**
     * 将字符串系列化为指定对象
     * @param arg 将要反序列化的字符串
     * @param t 目标对象类型
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T convertStringToObject(String arg,Class<T> t) throws IOException {
        return (T) mapper.readValue(arg,t.getClass());
    }

    /**
     * 反序列化一个对象到字符串
     * @param obj 将要反序列化的对象
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public static <T> String convertObjectToString(Class<T> obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}
