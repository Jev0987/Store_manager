package com.jevin.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/*
 *  @项目名：  Store_manager 
 *  @包名：    com.jevin.pojo
 *  @文件名:   ApplyOutPut
 *  @创建者:   85169
 *  @创建时间:  2019/9/18 15:55
 *  @描述：    出库申请信息
 */
@Entity
public class ApplyOutPut {
    @Id
    private int id;
    private String productName;//品名
    private String outCode;//出库编号
    private String enterCode;//入库编号
    private int size;//数量
    private String spec;//规格
    private double suttleWeight;//净重
    private double roughWeight;//毛重
    private double price;//单价
    private double amout;//总金额
    private String demandName;//提货商
    private String applyPersonId;//申请人id
    private String packagePersonId;//打包人id
    private String ensurePersonId;//审核人id
    private Date applyDate;//申请日期
    private Date entranceDate;//入库日期
    private String produceDate;//生产日期
    private String remark;//备注
    private String status;//状态信息


    public Date getEntranceDate() {
        return entranceDate;
    }

    public void setEntranceDate(Date entranceDate) {
        this.entranceDate = entranceDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOutCode() {
        return outCode;
    }

    public void setOutCode(String outCode) {
        this.outCode = outCode;
    }

    public String getEnterCode() {
        return enterCode;
    }

    public void setEnterCode(String enterCode) {
        this.enterCode = enterCode;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmout() {
        return amout;
    }

    public void setAmout(double amout) {
        this.amout = amout;
    }

    public String getDemandName() {
        return demandName;
    }

    public void setDemandName(String demandName) {
        this.demandName = demandName;
    }

    public String getApplyPersonId() {
        return applyPersonId;
    }

    public void setApplyPersonId(String applyPersonId) {
        this.applyPersonId = applyPersonId;
    }

    public String getPackagePersonId() {
        return packagePersonId;
    }

    public void setPackagePersonId(String packagePersonId) {
        this.packagePersonId = packagePersonId;
    }

    public String getEnsurePersonId() {
        return ensurePersonId;
    }

    public void setEnsurePersonId(String ensurePersonId) {
        this.ensurePersonId = ensurePersonId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
