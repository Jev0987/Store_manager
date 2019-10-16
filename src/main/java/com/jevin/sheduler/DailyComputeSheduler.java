package com.jevin.sheduler;

import com.jevin.dao.DailyCountRepository;
import com.jevin.pojo.DailyCount;
import com.jevin.service.ApplyEnterService;
import com.jevin.service.ApplyOutService;
import com.jevin.service.EntrepotStatusService;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.sheduler
 *  @文件名:   DailyComputeSheduler
 *  @创建者:   85169
 *  @创建时间:  2019/10/7 10:10
 *  @描述：    TODO
 */
@Component
public class DailyComputeSheduler {

    private final static long SECOND = 1*1000;

    @Autowired
    private ApplyOutService applyOutService;

    @Autowired
    private ApplyEnterService applyEnterService;

    @Autowired
    private EntrepotStatusService entrepotStatusService;

    @Autowired
    private DailyCountRepository dailyCountRepository;

    FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    /**
     * 统计每天入库数、出库数、库存数
     */
    public void computeCount(){
        System.out.println("定时任务（记录每天入库出库库存数）开始咯！~！~！");
        long begin = System.currentTimeMillis();
        int sizeOfIn = applyEnterService.getNumberOfTodayApplyEnter();
        int sizeOfOut = applyOutService.getNumberOfTodayApplyOut();
        int sizeOfEntrepot = entrepotStatusService.getNumberOfTodayEntrePotStatus();
        DailyCount dailyCount = new DailyCount();
        dailyCount.setSize(sizeOfIn);
        dailyCount.setType("入库数量");
        dailyCount.setComputeDate(new Date());
        dailyCountRepository.save(dailyCount);
        DailyCount dailyCount1 = new DailyCount();
        dailyCount1.setSize(sizeOfOut);
        dailyCount1.setComputeDate(new Date());
        dailyCount1.setType("出库数量");
        dailyCountRepository.save(dailyCount1);
        DailyCount dailyCount2 = new DailyCount();
        dailyCount2.setType("库存数量");
        dailyCount2.setSize(sizeOfEntrepot);
        dailyCount2.setComputeDate(new Date());
        dailyCountRepository.save(dailyCount2);

        System.out.println("数量分别为："+ sizeOfIn + sizeOfOut +sizeOfEntrepot);

        long end = System.currentTimeMillis();
        System.out.println("定时任务结束，共耗时：["+(end-begin)+"]毫秒");
    }

}
