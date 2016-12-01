package com.dinosaur.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    /**
     * 返回当前时间的字符串
     * @return 格式为（yyyy-MM-dd HH:mm:ss）的时间字符串
     */
    public static String getCurrentTime(){
        LocalDateTime localDateTime=LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(dateTimeFormatter);
    }

}
