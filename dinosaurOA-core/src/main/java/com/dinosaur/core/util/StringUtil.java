package com.dinosaur.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.HashMap;
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
    public static <T> Map<String,T> convertStrinToMap(String arg) throws IOException {
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
    public static <T> String convertObjectToString(T obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    /**
     * java bean转map对象
     * @param obj
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String,Object> beanToMap(Object obj) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            // 过滤class属性
            if (!key.equals("class")) {
                Method getter = property.getReadMethod();
                Object value = getter.invoke(obj);
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * map转javaBean
     * @param type 目标bean类型
     * @param map 源map对象
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    public static <T extends Object> T MapToBean(Class type, Map map) throws IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        Object object = type.newInstance();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++){
            PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
            String propertyName = propertyDescriptor.getName();
            if (map.containsKey(propertyName)){
                Object value = map.get(propertyName);
                propertyDescriptor.getWriteMethod().invoke(object,value);
            }
        }
        return (T) object;
    }
}
