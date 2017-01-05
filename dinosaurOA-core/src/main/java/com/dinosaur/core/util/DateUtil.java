package com.dinosaur.core.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    /**
     * 获取系统时间毫秒数
     * @return
     */
    public static long getMillisecond(){
        return System.currentTimeMillis();
    }

    /**
     * 获取自1970-1-1 00：00：00到给定时间字符串的毫秒数
     * @param arg
     * @return
     * @throws ParseException
     */
    public static long getMillisecond(String arg) throws ParseException {
        return toDate(arg,"yyyy-MM-dd HH:mm:SS").getTime();
    }

    /**
     * 获取两个时间的时间差
     * @param before
     * @param after
     * @return
     */
    public static long getJetLag(String after, String before){
        try {
            long Jetlag = getMillisecond(after) - getMillisecond(before);
            return  Jetlag;
        } catch (ParseException e) {
            throw new  RuntimeException(e.getMessage());
        }
    }

    /**
     * 将字符串转换为时间
     * @param date 时间字符串
     * @param pattern 格式参数
     * @return
     * @throws ParseException
     */
    public static Date toDate(String date, String pattern) throws ParseException {
        if (StringUtils.isBlank(date)){
            return null;
        }
        if(StringUtils.isBlank(pattern)){
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date newDate = sdf.parse(date);
        return newDate;
    }

}
