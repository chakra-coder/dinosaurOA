package com.dinosaur.core.util;

import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;

/**
 * 字符串工具类
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
public class StringUtil {

    private static SecureRandom secureRandom;
    private static final int BYTE_SIZE = 10;

    /**
     * 返回随机的字符串
      * @return 随机字符串
     */
    public static  String getRandomStr(){
        byte[] bytes = new byte[BYTE_SIZE];
        secureRandom.nextBytes(bytes);
        return Hex.encodeHexString(bytes);
    }

}
