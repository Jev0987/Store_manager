package com.jevin.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/*
 *  @项目名：  Store_manager 
 *  @包名：    com.jevin.pojo
 *  @文件名:   ApplyEnter
 *  @创建者:   85169
 *  @创建时间:  2019/9/15 9:54
 *  @描述：    入库申请信息
 */
@Entity
public class ApplyEnter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int enterId;
    private String enterCode;//进货编码
    private String spec;//规格
    private int number;//数量
    private String unit;//数量单位
    private double unitPrice;//单价
    private String coinType;//币值
    private double sumMoney;//金额
    private String billNumber; //发票号
    private double suttleWeight;//净重
    private double roughWeight;//毛重
    private String productName;//品名
    private String originCountry;//原产国
    private String position;//仓位
    private String goodsFrom;//供货商
    private String pono;//采购订单号
    private String status;//状态
    private String applyPersonId;//申请人id
    private String ensurePersonId;//确认人id
    private String remark;//备注
    private String produceDate;//生产日期
    private Date applyDate;//申请日期


    @Override
    public String toString() {
        return "ApplyEnter{" +
                "enterId=" + enterId +
                ", sumMoney=" + sumMoney +
                ", originCountry='" + originCountry + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public int getEnterId() {
        return enterId;
    }

    public void setEnterId(int enterId) {
        this.enterId = enterId;
    }

    public String getEnterCode() {
        return enterCode;
    }

    public void setEnterCode(String enterCode) {
        this.enterCode = enterCode;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGoodsFrom() {
        return goodsFrom;
    }

    public void setGoodsFrom(String goodsFrom) {
        this.goodsFrom = goodsFrom;
    }

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplyPersonId() {
        return applyPersonId;
    }

    public void setApplyPersonId(String applyPersonId) {
        this.applyPersonId = applyPersonId;
    }

    public String getEnsurePersonId() {
        return ensurePersonId;
    }

    public void setEnsurePersonId(String ensurePersonId) {
        this.ensurePersonId = ensurePersonId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
}
