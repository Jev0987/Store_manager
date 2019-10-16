package com.jevin.controller;

import com.jevin.dao.CommonRepository;
import com.jevin.dao.RelationShipRepository;
import com.jevin.pojo.Relationship;
import com.jevin.pojo.User;
import com.jevin.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
 *  @项目名：  Store_manager
 *  @包名：    com.jevin.controller
 *  @文件名:   RelationController
 *  @创建者:   85169
 *  @创建时间:  2019/10/16 18:55
 *  @描述：    TODO
 */
@Controller
public class RelationController {

    @Autowired
    private RelationShipRepository relationShipRepository;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommonRepository<Relationship> commonRepository;

    private Integer pagesize = 20;//每页显示的条数

    @RequestMapping("/relationship-findAll")
    public String getAll(int pagenum , HttpServletRequest request , ModelMap modelMap ,Relationship relationship){
        String page = "supplier_relation";
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "NoAccount";
        }
        StringBuffer sql = null;
        try {
            sql = commonRepository.getFiledValues(relationship, pagenum);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        sql.append(" 1 = 1");
        int totalPage = jdbcTemplate.query(sql.toString() , new BeanPropertyRowMapper(Relationship.class)).size();
        sql.append(" LIMIT " + (pagenum - 1) * pagesize + "," + pagesize);
        List<Relationship> relationships = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(Relationship.class));
        modelMap.addAttribute("ships", relationships);
        modelMap.addAttribute("page", pagenum);
        modelMap.addAttribute("totalpage", PageUtil.getTotalPage(totalPage, pagesize));
        return page;
    }

    @RequestMapping(value = "/relationship-save" , method = {RequestMethod.GET , RequestMethod.POST})
    public String relationSave(Relationship relationship){

        relationShipRepository.save(relationship);

        return "redirect:/relationship-findAll?pagenum=1";
    }

    @RequestMapping(value = "/relationship-delete" ,method = {RequestMethod.GET , RequestMethod.POST})
    public String relationDelete(int id){

        Relationship relationship = relationShipRepository.findRelationshipById(id);

        relationShipRepository.delete(relationship);

        return "redirect:/relationship-findAll?pagenum=1";
    }

    @ResponseBody
    @RequestMapping("relationship-getById")
    public Relationship getById(int id){
        Relationship relationshipById = relationShipRepository.findRelationshipById(id);
        System.err.println("获取关系详情：" + relationshipById);
        return relationshipById;
    }

}
