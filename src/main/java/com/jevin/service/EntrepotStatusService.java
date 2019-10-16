package com.jevin.service;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.service
 *  @文件名:   EntrepotStatusService
 *  @创建者:   85169
 *  @创建时间:  2019/10/7 10:12
 *  @描述：    TODO
 */

import com.jevin.dao.EntrepotStatusRepository;
import com.jevin.pojo.EntrepotStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EntrepotStatusService {
    @Autowired
    private EntrepotStatusRepository entrepotStatusRepository;

    public int getNumberOfTodayEntrePotStatus(){
        int total = 0;
        List<EntrepotStatus> totalSize = entrepotStatusRepository.findAll();
        for (EntrepotStatus entrepotStatus : totalSize) {
            total = entrepotStatus.getTotalSize()+total;
        }
        return total;
    }

}
