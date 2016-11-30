package com.dinosaur.core.util;

import java.time.LocalDate;

/**
 * 日期工具类
 * 负责日期获取，格式化等操作
 * @Author Alcott Hawk
 * @Date 11/30/2016
 */
public class DateUtil {

    /**
     * 获取当前的时间，输出格式为：2016-11-28
     * @return
     */
    public static String getNow(){
        LocalDate localDate = LocalDate.now();
        return localDate.toString();
    }

}
