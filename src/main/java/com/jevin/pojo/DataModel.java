package com.jevin.pojo;

import java.io.Serializable;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.pojo
 *  @文件名:   DataModel
 *  @创建者:   85169
 *  @创建时间:  2019/10/7 8:41
 *  @描述：    TODO
 */
public class DataModel implements Serializable {
    private String date;
    private String size;


    public DataModel(String date, String size) {
        this.date = date;
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
