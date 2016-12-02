package com.dinosaur.core.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

/**
 * hash 集
 */
public class HashKit {

    public final static String MD5 = "MD5";
    public final static String SHA1 = "SHA-1";
    public final static String SHA256 = "SHA-256";
    public final static String SHA384 = "SHA-384";
    public final static String SHA512 = "SHA-512";


    public static String md5(String salt, String srcStr,int iterations){
        return hash(MD5,salt, srcStr,iterations);
    }

    public static String sha1(String salt, String srcStr,int iterations){
        return hash(SHA1,salt ,srcStr,iterations);
    }

    public static String sha1(String salt, String srcStr){
        return hash(SHA1,salt, srcStr,1);
    }

    public static String sha256(String salt,String srcStr,int iterations){
        return hash(SHA256,salt, srcStr,iterations);
    }

    public static String sha384(String salt,String srcStr,int iterations){
        return hash(SHA384,salt, srcStr,iterations);
    }

    public static String sha512(String salt,String srcStr,int iterations){
        return hash(SHA512,salt, srcStr,iterations);
    }

    /**
     * 对字符串进行散列，支持md5与sha算法
     * @param algorithm 算法
     * @param salt 盐
     * @param srcStr 要加密的字符串
     * @param iterations 迭代次数
     * @return 加密后的字符串
     */
    public static String hash(String algorithm, String salt, String srcStr,int iterations) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(salt.getBytes());
            byte[] bytes = md.digest(srcStr.getBytes("utf-8"));
            for (int i = 0;i<iterations;i++){
                md.reset();
                bytes = md.digest(bytes);
            }
           return Hex.encodeHexString(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
