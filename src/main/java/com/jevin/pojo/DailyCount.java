package com.jevin.pojo;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.pojo
 *  @文件名:   DaliyCount
 *  @创建者:   85169
 *  @创建时间:  2019/10/6 11:02
 *  @描述：    TODO
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class DailyCount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int rowId;

    private int size;//数量

    private String type;//类型：包含入库，出库，库存

    private Date computeDate; //统计日期

    public DailyCount() {
        this.size = size;
        this.type = type;
        this.computeDate = computeDate;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getComputeDate() {
        return computeDate;
    }

    public void setComputeDate(Date computeDate) {
        this.computeDate = computeDate;
    }
}
