package com.jevin.pojo;

import java.util.Date;

/*
 *  @项目名：  Store_manager 
 *  @包名：    com.jevin.pojo
 *  @文件名:   EnterReport
 *  @创建者:   85169
 *  @创建时间:  2019/9/18 15:55
 *  @描述：    TODO
 */
public class EnterReport {

    private String enterCode;//入库编号
    private String goodsFrom;//供货商
    private String productName;//品名
    private String produceDate;//生产日期
    private String goodsNumber;//货物数量
    private String goodsUnit="PCS";//数量单位
    private double suttleWeight;//净重
    private double roughWeight;//毛重
    private String billnumber;//发票号码
    private Date applyDate;//申请日期

    public String getEnterCode() {
        return enterCode;
    }

    public void setEnterCode(String enterCode) {
        this.enterCode = enterCode;
    }

    public String getGoodsFrom() {
        return goodsFrom;
    }

    public void setGoodsFrom(String goodsFrom) {
        this.goodsFrom = goodsFrom;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public double getSuttleWeight() {
        return suttleWeight;
    }

    public void setSuttleWeight(double suttleWeight) {
        this.suttleWeight = suttleWeight;
    }

    public double getRoughWeight() {
        return roughWeight;
    }

    public void setRoughWeight(double roughWeight) {
        this.roughWeight = roughWeight;
    }

    public String getBillnumber() {
        return billnumber;
    }

    public void setBillnumber(String billnumber) {
        this.billnumber = billnumber;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
}
