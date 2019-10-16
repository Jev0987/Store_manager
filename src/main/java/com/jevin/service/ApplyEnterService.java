package com.jevin.service;

import com.jevin.dao.ApplyEnterRepository;
import com.jevin.pojo.ApplyEnter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.service
 *  @文件名:   ApplyEnterService
 *  @创建者:   85169
 *  @创建时间:  2019/10/7 10:12
 *  @描述：    TODO
 */
@Service
public class ApplyEnterService {

    @Autowired
    private ApplyEnterRepository applyEnterRepository;


    public int getNumberOfTodayApplyEnter(){
        int total = 0;
        List<ApplyEnter> todayEnsure = applyEnterRepository.getTodayEnsure();
        //for (循环变量类型 循环变量名称 : 要被遍历的对象) 循环体
        for (ApplyEnter applyenter : todayEnsure){
            total = applyenter.getNumber() + total;
        }
        return total;
    }
}
