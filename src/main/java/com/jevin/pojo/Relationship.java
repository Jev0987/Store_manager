package com.jevin.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.pojo
 *  @文件名:   Relationship
 *  @创建者:   85169
 *  @创建时间:  2019/10/13 10:21
 *  @描述：    TODO
 */
@Entity
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;//编号
    private String supplyCode;//供货商代码
    private String supplyName;
    private String demandCode;//提货商代码
    private String demandName;
    private String groupMark;//群组标记
    private String relationId;//唯一Id ， 可以手输

    @Override
    public String toString() {
        return "Relationship{" +
                "id=" + id +
                ", supplyCode='" + supplyCode + '\'' +
                ", supplyName='" + supplyName + '\'' +
                ", demandCode='" + demandCode + '\'' +
                ", demandName='" + demandName + '\'' +
                ", groupMark='" + groupMark + '\'' +
                ", relationId='" + relationId + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplyCode() {
        return supplyCode;
    }

    public void setSupplyCode(String supplyCode) {
        this.supplyCode = supplyCode;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getDemandCode() {
        return demandCode;
    }

    public void setDemandCode(String demandCode) {
        this.demandCode = demandCode;
    }

    public String getDemandName() {
        return demandName;
    }

    public void setDemandName(String demandName) {
        this.demandName = demandName;
    }

    public String getGroupMark() {
        return groupMark;
    }

    public void setGroupMark(String groupMark) {
        this.groupMark = groupMark;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }
}
