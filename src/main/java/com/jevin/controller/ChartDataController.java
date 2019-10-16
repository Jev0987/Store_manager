package com.jevin.controller;

import com.alibaba.fastjson.JSON;
import com.jevin.pojo.DailyCount;
import com.jevin.pojo.DataModel;
import com.jevin.service.ChartDataProviderService;
import com.jevin.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.controller
 *  @文件名:   ChartDataController
 *  @创建者:   85169
 *  @创建时间:  2019/10/7 10:08
 *  @描述：    TODO
 */
@Controller
public class ChartDataController {

    @Autowired
    private ChartDataProviderService service;

    @ResponseBody
    @RequestMapping(value = "/chart/status" , method = {RequestMethod.GET , RequestMethod.POST})
    public String getStatusChartdata(){
        List<DailyCount> statusCountIn7Days = service.getStatusCountIn7Days();
        List<DataModel> data = new ArrayList<>();
        for (DailyCount dailyCount : statusCountIn7Days) {
            if (dailyCount.getType().equals("库存数量")){
                data.add(new DataModel(DateUtil.dateToString(dailyCount.getComputeDate()), String.valueOf(dailyCount.getSize())));
            }
        }
        System.out.println(JSON.toJSONString(data));
        return JSON.toJSONString(data);
    }
}
