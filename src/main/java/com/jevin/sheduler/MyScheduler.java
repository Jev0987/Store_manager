package com.jevin.sheduler;

import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.sheduler
 *  @文件名:   MySheduler
 *  @创建者:   85169
 *  @创建时间:  2019/10/7 21:08
 *  @描述：    TODO
 */
@Component
public class MyScheduler {

    public void schedulerJob() throws SchedulerException {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        //获得上面创建的bean
        StdScheduler stdScheduler = (StdScheduler) applicationContext.getBean("mySchedulerFactoryBean");
        Scheduler scheduler =stdScheduler;
        startScheduler1(scheduler);
        scheduler.start();
    }


    public void startScheduler1(Scheduler scheduler) throws SchedulerException {

        //创建MySchedulerJob的定时任务    名称：job1 ， 组： jobGroup1
        JobDetail build = JobBuilder.newJob(MySchedulerJob.class).withIdentity("job1", "jobGroup1").build();

        //目标 创建触发器   触发器名称：trigger1 ，组：triggerGroup1    每天23:50:0执行
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 12 20 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1" , "triggerGroup1")
                .withSchedule(cronScheduleBuilder).build();

        //创建调度器，粘合工作和触发器，将任务及触发器放入调度器
        scheduler.scheduleJob(build,cronTrigger);
    }
}
