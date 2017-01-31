package com.dinosaur.core.util;

import com.dinosaur.core.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * shiro 工具类
 * @Author Alcott Hawk
 * @Date 1/31/2017
 */
public class ShiroUtil {

    private static Logger logger = LoggerFactory.getLogger(ShiroUtil.class);

    /**
     * 获取当前登录用户id
     * @param <T>
     * @return
     */
    public static  <T extends Object> T getCurrentUserId(){
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (null == shiroUser){
            throw new NullPointerException("用户未登录或者登录已过期");
        } else {
            return (T) shiroUser.id;
        }
    }

}
