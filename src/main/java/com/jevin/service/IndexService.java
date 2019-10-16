package com.jevin.service;

import com.jevin.dao.ApplyEnterRepository;
import com.jevin.dao.ApplyOutRepository;
import com.jevin.dao.EntrepotStatusRepository;
import com.jevin.pojo.ApplyEnter;
import com.jevin.pojo.ApplyOutPut;
import com.jevin.pojo.EntrepotStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 *  @项目名：  Store_manager 
 *  @包名：    com.jevin.service
 *  @文件名:   IndexService
 *  @创建者:   85169
 *  @创建时间:  2019/9/15 16:48
 *  @描述：    TODO
 */
@Service
public class IndexService {
    @Autowired
    private ApplyEnterRepository applyEnterRepository;

    @Autowired
    private ApplyOutRepository applyOutRepository;

    @Autowired
    private EntrepotStatusRepository entrepotStatusRepository;


    /**
     * 获取入库数
     */
    public int getYestdayApplyEnterCount(){
        int size = 0;
        List<ApplyEnter> yestdayApplys = applyEnterRepository.getYestdayApplys();
        if (yestdayApplys != null){
            size = yestdayApplys.size();
        }
        return size;
    }

    /**
     * 获取昨日出库数
     * @return
     */
    public int getYestdayApplyOutCount(){
        int size = 0;
        List<ApplyOutPut> yestDayApplys = applyOutRepository.findYestDayApplys();
        if (yestDayApplys != null) {
            size = yestDayApplys.size();
        }
        return size;
    }

    public int getEntrePotSize(){
        int size = 0;
        List<EntrepotStatus> totalSize = entrepotStatusRepository.getTotalSize();
        if (totalSize != null) {
            size = totalSize.size();
        }
        return size;
    }
}
