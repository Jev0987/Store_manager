package com.jevin.sheduler;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.sheduler
 *  @文件名:   MySchedulerJob
 *  @创建者:   85169
 *  @创建时间:  2019/10/7 21:22
 *  @描述：    TODO
 */

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MySchedulerJob implements Job {

    @Autowired
    private DailyComputeSheduler dailyComputeSheduler;

    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat("HH:mm:ss");
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //执行的定时任务
        dailyComputeSheduler.computeCount();
        System.out.println("每日统计数量：" +dateFormat().format(new Date()));
    }
}
