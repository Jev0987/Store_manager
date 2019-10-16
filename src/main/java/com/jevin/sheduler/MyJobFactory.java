package com.jevin.sheduler;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.sheduler
 *  @文件名:   MyJobFactory
 *  @创建者:   85169
 *  @创建时间:  2019/10/8 14:49
 *  @描述：    TODO
 */
@Component
public class MyJobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {

        //调用父类方法
        Object o= super.createJobInstance(bundle);
        //进行调用
        capableBeanFactory.autowireBean(o);
        return o;
    }
}
