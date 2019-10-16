package com.jevin.dao;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.dao
 *  @文件名:   RelationShipRepository
 *  @创建者:   85169
 *  @创建时间:  2019/10/16 17:08
 *  @描述：    TODO
 */

import com.jevin.pojo.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationShipRepository extends JpaRepository<Relationship , Long> {

    Relationship findRelationshipById(int id);

    List<Relationship> findRelationshipBySupplyName(String supplyName);

}
