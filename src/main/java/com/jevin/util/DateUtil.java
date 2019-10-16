package com.jevin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.util
 *  @文件名:   DateUtil
 *  @创建者:   85169
 *  @创建时间:  2019/10/6 10:32
 *  @描述：    TODO
 */
public class DateUtil {

    //SimpleDateFormat 格式化日期
    //yyyy:年   MM:月   dd:日
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * parse(): 将字符串类型s 转化为 yyyy-MM-dd
     * @param s
     * @return
     */
    public static Date stringToDate(String s) {
        Date date = null;
        try {
            date = FORMAT.parse(s);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    /**
     * format(): 将date类型date 转化为 yyyy-MM-dd
     * @param date
     * @return
     */
    public static String dateToString(Date date){
        String date1 = null;
        date1 = FORMAT.format(date);
        return date1;
    }

    /**
     * 获得制定时间前多少天的日期 yyyy-MM-dd
     * @param today
     * @param beforeDays
     * @return
     */
    public static Date getDateBefore(Date today , int beforeDays){
        //获取当前时间毫秒数
        long time = today.getTime();

        //获取7天毫秒数
        long sevenTime = beforeDays*24*60*60*1000;

        //七天之间的毫秒数 = 七天毫秒数 - 当前时间毫秒数
        long times = sevenTime - time;

        Date dat = new Date(times);

        return dat;
    }
}
