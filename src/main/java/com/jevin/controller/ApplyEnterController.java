package com.jevin.controller;

import com.jevin.dao.ApplyEnterRepository;
import com.jevin.dao.CommonRepository;
import com.jevin.dao.EntrepotStatusRepository;
import com.jevin.pojo.ApplyEnter;
import com.jevin.pojo.EntrepotStatus;
import com.jevin.pojo.User;
import com.jevin.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/*
 *  @项目名：  Store_manager 
 *  @包名：    com.jevin.controller
 *  @文件名:   ApplyEnterController
 *  @创建者:   85169
 *  @创建时间:  2019/9/15 10:34
 *  @描述：    入库操作
 */
@Controller
public class ApplyEnterController {

    private Integer pagesize = 20;

    @Autowired
    private CommonRepository commonRepository;
    @Autowired
    private ApplyEnterRepository applyEnterRepository;
    @Autowired
    private EntrepotStatusRepository entrepotStatusRepository;

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取当前未审核入库信息界面
     */
    @RequestMapping(value = "/applyin-getNotAllowApplyEnter" , method = {RequestMethod.GET,RequestMethod.POST})
    public String getNotAllowed(ApplyEnter applyEnter , int pagenum , ModelMap modelMap , HttpServletRequest request) throws Exception {
        String page = "entrance_apply";
        User user = (User) request.getSession().getAttribute("user");
        System.out.println("当前操作人：" + user);
        if (user == null){
            throw new Exception("当前用户尚未登录");
        }
        Long id = user.getId();
        //查询，如果不为空继续查，为空加载分页信息
        if (applyEnter != null){
            StringBuffer sql = null;
            try {
                sql = commonRepository.getFiledValues(applyEnter , pagenum);
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
            sql.append(" Status != '已确认' AND applyPersonId = '" + user.getUsername() +"'");
            int totalpage = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(ApplyEnter.class)).size();
            sql.append(" LIMIT " + (pagenum - 1) * pagesize+ " , "+pagesize);
            List<ApplyEnter> applyEnters = jdbcTemplate.query(sql.toString() , new BeanPropertyRowMapper<>(ApplyEnter.class));
            modelMap.addAttribute("applys" , applyEnters );
            modelMap.addAttribute("page" , pagenum);
            modelMap.addAttribute("totalpage" , PageUtil.getTotalPage(totalpage , pagesize));//返回需要的页数

        }else {
            Pageable pageable = PageRequest.of(pagenum,pagesize);
            Page<ApplyEnter> pager = applyEnterRepository.findApplyEnterByStatusNot("已确认" , pageable);
            modelMap.addAttribute("applys" , pager.getContent());//getContent() , 将页面内容用list返回
            modelMap.addAttribute("page" , pagenum);
            modelMap.addAttribute("totalpage" , pager.getTotalPages());//getTotalPages() , 返回总页数
        }
        return page;
    }

    /**
     * 新增入库申请
     */
    @RequestMapping(value = "/applyin-addapply")
    public String saveApply(ApplyEnter applyEnter, BindingResult bindingResult, HttpServletRequest request) throws Exception {
        String page = "entrance_apply_wait";
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new Exception("该用户尚未登录");
        }
        System.err.println("当前操作用户是：" + user);
        applyEnter.setApplyPersonId(user.getUsername());
        applyEnter.setStatus("待审核");
        applyEnter.setApplyDate(new Date());
        System.err.println("插入数据："+applyEnter);
        applyEnterRepository.save(applyEnter);
        return "redirect:/applyin-getNotAllowApplyEnter?pagenum=1";
    }

    /**
     * 获取尚未确认的申请
     *
     */
    @RequestMapping(value = "/applyin-toBeEnsured" , method = {RequestMethod.GET , RequestMethod.POST})
    public String GetNotAllowedByEnsured(ApplyEnter applyEnter , int pagenum , ModelMap modelMap, HttpServletRequest request ) throws Exception {
        String page = "entrance_apply_wait";
        User user = (User) request.getSession().getAttribute("user");
        //User user = (User) session.getAttribute("user");
        if (user == null){
            throw new Exception("该用户尚未登录");
        }
        if (applyEnter != null){
            StringBuffer sql = null;
            try {
                sql = commonRepository.getFiledValues(applyEnter , pagenum);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            sql.append(" Status = '待审核'");
            int totalpage = jdbcTemplate.query(sql.toString() , new BeanPropertyRowMapper(ApplyEnter.class)).size();
            sql.append(" LIMIT " + (pagenum - 1 ) * pagesize +"," +pagesize);
            List<ApplyEnter> applyEnters = jdbcTemplate.query(sql.toString() , new BeanPropertyRowMapper(ApplyEnter.class));
            modelMap.addAttribute("applys" , applyEnters);
            modelMap.addAttribute("page" , pagenum);
            modelMap.addAttribute("totalpage" , PageUtil.getTotalPage(totalpage,pagesize));
        }else {
            Pageable pageable = PageRequest.of(pagenum , pagesize);
            Page<ApplyEnter> pager = applyEnterRepository.findApplyEnterByStatusNot("已确认",pageable);
            modelMap.addAttribute("applys" , pager.getContent());
            modelMap.addAttribute("page" , pagenum);
            modelMap.addAttribute("totalpage" , pager.getTotalPages());
        }
        return page;
    }

    @RequestMapping(value = "applyin-getHistory",method = {RequestMethod.GET,RequestMethod.POST})
    public String getHistory(ApplyEnter applyEnter , int pagenum , ModelMap modelMap)  {
        String page = "entrance_apply_history";
//        User user = (User) request.getSession().getAttribute("user");
//        if (user == null) {
//            throw new Exception("错误，尚未登录");
//        }
        StringBuffer sql = null;
        if (applyEnter != null) {
            try {
                sql = commonRepository.getFiledValues(applyEnter , pagenum);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            sql.append(" Status = '已确认'");
            int totalSize = jdbcTemplate.query(sql.toString() , new BeanPropertyRowMapper(ApplyEnter.class)).size();
            sql.append(" LIMIT " + (pagenum - 1) * pagesize + " , " + pagesize);
            List<ApplyEnter> applyEnters = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(ApplyEnter.class));
            modelMap.addAttribute("applys", applyEnters);
            modelMap.addAttribute("page", pagesize);
            modelMap.addAttribute("totalpage", totalSize);
        }else {
            Pageable pageable = PageRequest.of(pagenum , pagesize);
            Page<ApplyEnter> pager = applyEnterRepository.findApplyEnterByStatus("已确认", pageable);
            modelMap.addAttribute("applys", pager.getContent());
            modelMap.addAttribute("page",pagenum);
            modelMap.addAttribute("totalpage", pager.getTotalPages());
        }

        return page;
    }



    /**
     * 拒绝入库申请
     * @param enterId
     * @param request
     * @return
     */
    @RequestMapping(value = "applyin-turndown")
    public String turndownTheApply(int enterId , HttpServletRequest request) throws Exception {
        String page = "entrance_apply_wait";
        ApplyEnter applyEnter = applyEnterRepository.findApplyEnterByEnterId(enterId);
        applyEnter.setStatus("被拒绝");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new Exception("该用户尚未登陆");
        }
        applyEnter.setEnsurePersonId(user.getUsername());
        applyEnterRepository.save(applyEnter);
        return "redirect:/applyin-toBeEnsured?pagenum=1";
    }

    /**
     * 详情
     * @param enterId
     * @return
     */

    @RequestMapping(value = "/getApplyEnterById" , method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ApplyEnter getApplyEnterById(int enterId){
        return applyEnterRepository.findApplyEnterByEnterId(enterId);
    }


    /**
     * 搜索，，根据type选择页面
     * @param applyEnter
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping(value = "/applyin-search" , method = {RequestMethod.GET , RequestMethod.POST})
    public String dosearch(ApplyEnter applyEnter , ModelMap modelMap , HttpServletRequest request) {
        String page = "entrance_apply_wait";
        //searchItem 选择要搜索的属性
        //searchValue 搜索的内容
        String searchItem = request.getParameter("searchItem");
        String searchValue = request.getParameter("searchValue");
        Integer pagenum = Integer.parseInt(request.getParameter("pagenum"));
        //若页面在待处理的入库界面，则要跳转的是 。 wait.html , 此时的type是1
        //若页面在历史入库界面，则要跳转的是 。 history.html , 此时的type是2
        Integer type = Integer.parseInt(request.getParameter("type"));
        System.out.println(searchValue != null);
        System.out.println("********");
        System.out.println(!"".equals(searchValue));
        StringBuffer sql = null;
        try {
            System.out.println(commonRepository.getFiledValues(applyEnter , pagenum));
            //纠错：（一直没看出来的错误）；sql.append(commonRepository.getFiledValues(applyEnter,pagenum))
            //append,使用来添加的
            sql = commonRepository.getFiledValues(applyEnter, pagenum);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //searchValue  不为空，或不为空字符
        // tips:  "" != null  为 ture
        if (searchValue != null||!"".equals(searchValue)) {
            if (type == 1) {
                page = "entrance_apply_wait";
                sql.append(searchItem + " like '%" + searchValue + "%' AND Status != '" + "已确认'");
                System.out.println("  1   " + sql.toString());
            } else {
                page = "entrance_apply_history";
                sql.append(searchItem + " like '%" + searchValue + "%' AND Status = '" + "已确认'");
                System.out.println("  2  " + sql.toString());
            }
        } else {
            //1=1代表ture ，如果是单独的sql语句，可以不写where 1=1 ，但是如果在程序中拼装sql语句，比如后面要加条件，就要写这个
            sql.append(" 1 = 1 ");
            System.out.println("  3  " +sql.toString());
        }
        int totalpage = jdbcTemplate.query(sql.toString() , new BeanPropertyRowMapper(ApplyEnter.class)).size();
        List<ApplyEnter> applyEnters = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(ApplyEnter.class));
        modelMap.addAttribute("applys", applyEnters);
        modelMap.addAttribute("searchValue", searchValue);
        modelMap.addAttribute("searchItem", searchItem);
        modelMap.addAttribute("totalpage", PageUtil.getTotalPage(totalpage, pagesize));
        return page;
    }

    /**
     * 删除  待确认界面
     * @param enterId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/applyin-deleteById")
    public String deleteApplyById(int enterId , HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new Exception("该用户尚未登录");
        }
        ApplyEnter applyEnter = applyEnterRepository.findApplyEnterByEnterId(enterId);
        applyEnterRepository.delete(applyEnter);
        return "redirect:/applyin-getNotAllowApplyEnter?pagenum=1";
    }

    /**
     * 删除   历史界面
     * @param enterId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/applyin-his-deleteById")
    public String deletehisApplyById(int enterId , HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new Exception("该用户尚未登录");
        }
        ApplyEnter applyEnter = applyEnterRepository.findApplyEnterByEnterId(enterId);
        applyEnterRepository.delete(applyEnter);
        return "redirect:/applyin-getHistory?pagenum=1";
    }


    /**
     * 审核入库申请
     * @param enterId
     * @param request
     * @return
     */
    @RequestMapping("/applyin-updateStatus" )
    public String ensureApply(int enterId , HttpServletRequest request) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new Exception("该用户尚未登录");
        }
        ApplyEnter applyEnter = applyEnterRepository.findApplyEnterByEnterId(enterId);
        applyEnter.setStatus("已确认");
        applyEnter.setEnsurePersonId(user.getUsername());
        applyEnter.setApplyDate(new Date());
        applyEnterRepository.save(applyEnter);
        //添加到仓库中
        EntrepotStatus entrepotStatus = new EntrepotStatus();
//        entrepotStatus.setId(applyEnter.getEnterId());
        entrepotStatus.setEnterCode(applyEnter.getEnterCode());
        entrepotStatus.setEntranceDate(applyEnter.getApplyDate());
        entrepotStatus.setGoodsStatus("未检验");
        entrepotStatus.setPosition(applyEnter.getPosition());
        entrepotStatus.setProduceDate(applyEnter.getProduceDate());
        entrepotStatus.setProductName(applyEnter.getProductName());
        entrepotStatus.setSupplyName(applyEnter.getGoodsFrom());
        entrepotStatus.setTaxCode(applyEnter.getBillNumber());
        entrepotStatus.setTotalSize(applyEnter.getNumber());
        entrepotStatus.setUpdateDate(new Date());
        entrepotStatus.setSumMoney(applyEnter.getSumMoney());
        entrepotStatusRepository.save(entrepotStatus);
        return "redirect:/applyin-toBeEnsured?pagenum=1";
       }


}
