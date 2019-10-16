package com.jevin.service;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.service
 *  @文件名:   ChartDataProviderService
 *  @创建者:   85169
 *  @创建时间:  2019/10/6 10:58
 *  @描述：    TODO
 */

import com.jevin.dao.DailyCountRepository;
import com.jevin.pojo.DailyCount;
import com.jevin.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChartDataProviderService {
    @Autowired
    private DailyCountRepository repository;


    /**
     * 获得七天的库存量， 直接返回满足echart格式的json串
     * @return
     */
    public List<DailyCount> getStatusCountIn7Days(){
        List<DailyCount> betweenDays = repository.findBetweenDays(DateUtil.getDateBefore(new Date(), 7), new Date());
        return betweenDays;
    }

}
