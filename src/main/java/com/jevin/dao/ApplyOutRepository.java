package com.jevin.dao;

import com.jevin.pojo.ApplyOutPut;
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
 *  @文件名:   ApplyOutRepository
 *  @创建者:   85169
 *  @创建时间:  2019/9/20 15:45
 *  @描述：    TODO
 */
@Repository
public interface ApplyOutRepository extends JpaRepository<ApplyOutPut , Long>{
    //分页
    Page<ApplyOutPut> findApplyOutPutByStatusNot(String Status , Pageable pageable);

    Page<ApplyOutPut> findApplyOutPutByStatus(String Status , Pageable pageable);

    List<ApplyOutPut> findAllByOutCode(String outCode);

    ApplyOutPut findApplyOutPutById(Integer id);



    //查询
    @Query(" SELECT out FROM ApplyOutPut out WHERE out.applyDate >= :startDate and out.applyDate <= :endDate and status ='已确认' ")
    List<ApplyOutPut> findBetweenDays(@Param("startDate") Date startDate ,@Param("endDate") Date endDate);

    @Query("SELECT out FROM ApplyOutPut out where TO_DAYS(NOW()) - TO_DAYS(out.applyDate) = 1 and status = '已确认'")
    List<ApplyOutPut> findYestDayApplys();

    @Query("SELECT out FROM ApplyOutPut out where TO_DAYS(NOW()) - TO_DAYS(out.applyDate) = 0 and status = '已确认'")
    List<ApplyOutPut> findTodayEnsure();

}
