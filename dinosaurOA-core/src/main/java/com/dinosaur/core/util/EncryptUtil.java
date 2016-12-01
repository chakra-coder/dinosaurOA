package com.dinosaur.core.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.Validate;

import java.security.SecureRandom;

/**
 * 加密工具类
 */
public class EncryptUtil extends HashKit{

    private static SecureRandom random = new SecureRandom();

    /**
     * 随机生成一个盐的字节数字
     * @param byteSize 数组尺寸，生成的盐的数字的尺寸
     * @return 包含随机盐的字节数组
     */
    public static byte[] salt(int byteSize){
        Validate.isTrue(byteSize>0,"byteSize argument must be a positive integer (1 or larger)",byteSize);
        byte[] bytes = new byte[byteSize];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * 加密一个字符串，使用sha-1算法
     * @param str 待加密字符串
     * @return 加密后的字符串
     */
    public static  String encrypString(String salt,String str){
        return sha1(salt,str);
    }

    public static String encrypString(String salt,String str,int hashIterations){
        return sha1(salt,str,hashIterations);
    }

    /**
     * Hex解码
     * @param input 输入字符串
     * @return byte[] 解码后的字节数组
     */
    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Hex编码
     * @param input 输入字字节数组
     * @return String Hex编码后的字符串
     */
    public static String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }

}
