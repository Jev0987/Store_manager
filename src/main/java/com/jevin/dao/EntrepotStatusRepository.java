package com.jevin.dao;

import com.jevin.pojo.EntrepotStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/*
 *  @项目名：  Store_manager 
 *  @包名：    com.jevin.dao
 *  @文件名:   EntrepotStatusRepository
 *  @创建者:   85169
 *  @创建时间:  2019/9/19 10:33
 *  @描述：    TODO
 */
@Repository
public interface EntrepotStatusRepository extends JpaRepository<EntrepotStatus, Long> {

    Page<EntrepotStatus> findAllByGoodsStatus(String Status, Pageable pageable);
    //通过入库编号查找货物，返回list集合
    List<EntrepotStatus> findEntrepotStatusByEnterCode(String enterCode);
    //通过id查找货物，返回list集合
    List<EntrepotStatus> findEntrepotStatusById(int id);

//    @Query(" SELECT entrepot FROM EntrepotStatus entrepot where entrepot.enterCode = :enterCode")
//    List<EntrepotStatus> findEntrepotStatusByEnterCode(@Param("enterCode") String enterCode);
    @Query(" SELECT entrepot FROM EntrepotStatus entrepot where entrepot.entranceDate <= :entranceDate and goodsStatus ='良品'")
    List<EntrepotStatus> findBeforeDate(@Param("entranceDate") Date date);
    @Query(" SELECT entrepot FROM EntrepotStatus entrepot")
    List<EntrepotStatus> getTotalSize();

}
