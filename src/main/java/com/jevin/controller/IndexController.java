package com.jevin.controller;

import com.jevin.pojo.User;
import com.jevin.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/*
 *  @项目名：  Store_manager 
 *  @包名：    com.jevin.controller
 *  @文件名:   IndexController
 *  @创建者:   85169
 *  @创建时间:  2019/9/20 16:53
 *  @描述：    TODO
 */
@Controller
public class IndexController {

    @Autowired
    private IndexService service;

    @GetMapping("/getIndex")
    public String getIndex(HttpServletRequest request , ModelMap modelMap) throws Exception {
        String page = "index";
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            page = "login";
        }
        int enterSize = service.getYestdayApplyEnterCount();
        int outputSize = service.getYestdayApplyOutCount();
        int totalSize = service.getEntrePotSize();
        modelMap.addAttribute("enterSize", enterSize);
        modelMap.addAttribute("outSize", outputSize);
        modelMap.addAttribute("entreotSize" , totalSize);
        return page;
    }
}
