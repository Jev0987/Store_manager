package com.jevin.sheduler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.sheduler
 *  @文件名:   SpringUtil
 *  @创建者:   85169
 *  @创建时间:  2019/10/7 21:28
 *  @描述：    获取spring配置文件中所有引用到的bean对象
 */

//@Componet ： 告诉spring有这样的一个类存在。   OR   在配置文件中声明 <bean id="SpringUtil" class="com.jevin.sheduler.SpringUtil">

@Component
public class SpringUtil implements ApplicationContextAware {



    //声明一个静态变量保存
    private static  ApplicationContext applicationContext;


    //获得ApplicationContext对象 ， ApplicationContext对象是由spring注入的。前提必须在spring配置文件中指定该类
    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = arg0;
        }
    }

    //使用静态成员ApplicationContext类型的对象
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name 获取Bean
    public static <T> T getBean(String name){
        return (T) getApplicationContext().getBean(name);
    }

    //通过class 获取Bean
    public static <T> T getBean(Class<T> tClass){
        return (T) getApplicationContext().getBean(tClass);
    }

    //通过name 和 class 获取Bean
    public static <T> T getBean(String name,Class<T> tClass ) {
        return (T) getApplicationContext().getBean(name,tClass);
    }
}
