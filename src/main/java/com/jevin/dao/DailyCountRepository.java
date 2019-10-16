package com.jevin.dao;

import com.jevin.pojo.DailyCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.dao
 *  @文件名:   DailyCountRepository
 *  @创建者:   85169
 *  @创建时间:  2019/10/6 9:42
 *  @描述：    TODO
 */
@Repository
public interface DailyCountRepository extends JpaRepository<DailyCount,Long> {

    //查询统计时间内的
    @Query("SELECT daily FROM DailyCount daily where daily.computeDate >= :startDate and daily.computeDate <= :endDate")
    List<DailyCount> findBetweenDays(@Param("startDate") Date startDate , @Param("endDate") Date endDate);

}
