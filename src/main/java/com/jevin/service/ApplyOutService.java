package com.jevin.service;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.service
 *  @文件名:   ApplyOutService
 *  @创建者:   85169
 *  @创建时间:  2019/10/7 10:12
 *  @描述：    TODO
 */

import com.jevin.dao.ApplyOutRepository;
import com.jevin.pojo.ApplyOutPut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyOutService {

    @Autowired
    private ApplyOutRepository applyOutRepository;

    public int getNumberOfTodayApplyOut(){
        int total = 0;
        List<ApplyOutPut> todayEnsure = applyOutRepository.findTodayEnsure();
        for (ApplyOutPut applyOutPut : todayEnsure) {
            total = applyOutPut.getSize()+total;
        }
        return total;
    }
}
