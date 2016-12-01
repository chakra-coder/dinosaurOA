package com.dinosaur.core.util;

import org.apache.commons.lang3.Validate;

import java.security.SecureRandom;

/**
 * 字符串工具类
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
public class StringUtil {

    private static SecureRandom secureRandom = new SecureRandom();
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

}
