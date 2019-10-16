package com.jevin.dao;

import com.jevin.pojo.ApplyEnter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/*
 *  @项目名：  Store_manager 
 *  @包名：    com.jevin.dao
 *  @文件名:   ApplyEnterRepository
 *  @创建者:   85169
 *  @创建时间:  2019/9/15 10:33
 *  @描述：    TODO
 */
@Repository
public interface ApplyEnterRepository extends JpaRepository< ApplyEnter , Long> {

    //Page 是 Spring Data提供的一个接口，
    // 该接口表示一部分数据的集合以及其相关的下一部分数据、数据总数等相关信息，
    // 通过该接口，我们可以得到数据的总体信息（数据总数、总页数）以及当前的数据的信息（当前数据的集合、当前页数）

    //Pageable 是Spring Data库中定义的一个接口，该接口是所有分页相关信息的一个抽象，
    // 通过该接口看我们可以得到和分页相关的所有信息（例如pageNumer、pageSize等），
    // 这样，JPA就能够通过pageable参数来得到一个带分页信息的sql语句
    Page<ApplyEnter> findApplyEnterByStatus(String Status , Pageable pageable);
    Page<ApplyEnter> findApplyEnterByStatusNot(String Status , Pageable pageable);

    //通过id查询每个进库成员
    ApplyEnter findApplyEnterByEnterId(Integer id);



    @Query("SELECT enter FROM ApplyEnter enter where enter.applyDate <= :applyDate and status = '待审核'" )
    List<ApplyEnter> findBeforeDate(@Param("applyDate") Date date);
    @Query("SELECT enter FROM ApplyEnter enter where enter.enterCode = enterCode")
    List<ApplyEnter> findApplyEnterByEnterCode(@Param("enterCode") String enterCode);
    @Query("SELECT enter FROM ApplyEnter enter where enter.applyDate >= :startDate and enter.applyDate <= :endDate and enter.status = '已确认'")
    List<ApplyEnter> findBetweenDays(@Param("startDate") Date startDate , @Param("endDate") Date endDate);



    @Query("SELECT number FROM ApplyEnter enter where TO_DAYS(NOW()) - TO_DAYS(enter.applyDate) = 1 AND STATUS ='已确认' ")
    List<ApplyEnter> getYestdayApplys();

    @Query("SELECT enter FROM ApplyEnter enter where TO_DAYS(NOW()) - TO_DAYS(enter.applyDate) = 0 AND STATUS = '已确认'")
    List<ApplyEnter> getTodayEnsure();
}
