package com.jevin.util;

/*
 *  @项目名：  Store_manager 
 *  @包名：    com.jevin.util
 *  @文件名:   PageUtil
 *  @创建者:   85169
 *  @创建时间:  2019/9/13 16:46
 *  @描述：    分页工具类
 */
public class PageUtil {

    //获取全部条目和每页的数量
    //返回需要的页数
    public static int getTotalPage(int totalsize,int pagesize){
        //页数
        int total;

        if (totalsize%pagesize == 0){

            total = totalsize/pagesize;

        }else {

            total = totalsize/pagesize + 1;
        }
        return total;
    }
}
