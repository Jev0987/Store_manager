package com.jevin.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/*
 *  @项目名：  Store_manager 
 *  @包名：    com.jevin.pojo
 *  @文件名:   EntrepotStatus
 *  @创建者:   85169
 *  @创建时间:  2019/9/18 15:56
 *  @描述：    仓库库存情况
 */
@Entity
public class EntrepotStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String enterCode;//入库编号
    private String supplyName;//供货商
    private String taxCode;//发票号码
    private double sumMoney;//总金额
    private String goodsStatus;//货物状态：待验，良品，不良品
    private String productName;//品名
    private String position;//仓位
    private int totalSize;//库存
    private String produceDate;//生产日期
    private Date entranceDate;//入库日期
    private Date updateDate;//更新日期


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getEnterCode() {
        return enterCode;
    }

    public void setEnterCode(String enterCode) {
        this.enterCode = enterCode;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public Date getEntranceDate() {
        return entranceDate;
    }

    public void setEntranceDate(Date entranceDate) {
        this.entranceDate = entranceDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
