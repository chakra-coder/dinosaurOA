package com.dinosaur.core.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * 解码工具类
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
public class Decoder {

    /**
     * Hex解码
     * @param input 输入字符串
     * @return byte[]字符串解码后的数组
     */
    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }

}
