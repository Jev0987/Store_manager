package com.jevin.controller;

import com.jevin.dao.ApplyOutRepository;
import com.jevin.dao.CommonRepository;
import com.jevin.dao.EntrepotStatusRepository;
import com.jevin.pojo.ApplyOutPut;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/*
 *  @项目名：  Store_manager 
 *  @包名：    com.jevin.controller
 *  @文件名:   ApplyOutController
 *  @创建者:   85169
 *  @创建时间:  2019/9/20 15:29
 *  @描述：    出库操作
 */
@Controller
public class ApplyOutController {
    private Integer pagesize = 20;

    @Autowired
    private ApplyOutRepository applyOutRepository;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private EntrepotStatusRepository entrepotStatusRepository;

    @Resource
    private JdbcTemplate jdbcTemplate;


    /**
     * 获取当前未审核的出库信息界面
     * @param applyOutPut
     * @param request
     * @param pagenum
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/applyout-getNotAllowed")
    public String getNotAllowed(ApplyOutPut applyOutPut , HttpServletRequest request , int pagenum , ModelMap modelMap) throws Exception {
        String page = "exit_apply";
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new Exception("该用户尚未登录");
        }
        Long userId = user.getId();
        if (applyOutPut != null) {
            StringBuffer sql = null;
            try {
                sql = commonRepository.getFiledValues(applyOutPut, pagenum);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            sql.append(" Status != '已确认' AND applyPersonId = '" + user.getUsername() + "'");
            int totalSize = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(ApplyOutPut.class)).size();
            sql.append(" LIMIT " + (pagenum - 1) * pagesize + "," + pagesize);
            List<ApplyOutPut> applyOutPuts = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(ApplyOutPut.class));
            modelMap.addAttribute("applys", applyOutPuts);
            modelMap.addAttribute("page", pagenum);
            modelMap.addAttribute("totalpage", PageUtil.getTotalPage(totalSize, pagesize));

        } else {
            Pageable pageable = PageRequest.of(pagenum, pagesize);
            Page<ApplyOutPut> pages = applyOutRepository.findApplyOutPutByStatusNot("已确认" , pageable);
            modelMap.addAttribute("applys", pages.getContent());
            modelMap.addAttribute("page", pagenum);
            modelMap.addAttribute("totalpage", pages.getTotalPages());
        }
        modelMap.addAttribute("username", user.getUsername());
        return page;
    }

    /**
     * 填写出库申请
     * @param request
     * @param modelMap
     * @param entrepotStatus
     * @param pagenum
     * @return
     * @throws Exception
     */
    @RequestMapping("/applyout-exit")
    public String exitApply(HttpServletRequest request , ModelMap modelMap , EntrepotStatus entrepotStatus ,int pagenum) throws Exception {
        String page = "exit_request_apply";
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new Exception("该用户尚未登录");
        }
        StringBuffer sql = null;
        if (entrepotStatus != null) {

            try {
                sql = commonRepository.getFiledValues(entrepotStatus, pagenum);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //还未做检验功能，以后再修改为 已检验
            sql.append(" goodsStatus = '未检验' ");
            int totalSize = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(EntrepotStatus.class)).size();
            sql.append(" LIMIT " + (pagenum - 1) * pagesize + "," + pagesize);
            List<EntrepotStatus> entrepotStatuses = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(EntrepotStatus.class));
            modelMap.addAttribute("applys", entrepotStatuses);
            modelMap.addAttribute("page", pagenum);
            modelMap.addAttribute("totalpage", PageUtil.getTotalPage(totalSize, pagesize));
        }else {
            Pageable pageable = PageRequest.of(pagenum, pagesize);
            //还未做检验功能，以后再修改为已检验
            Page<EntrepotStatus> pager = entrepotStatusRepository.findAllByGoodsStatus("未检验", pageable);
            modelMap.addAttribute("applys", pager.getContent());
            modelMap.addAttribute("page", pagenum);
            modelMap.addAttribute("totalpage" , pager.getTotalPages());
        }

        return page;
    }

    /**
     * 审核出库申请
     * @param applyOutPut
     * @param request
     * @param modelMap
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/applyout-updateStatus")
    public String SaveApplyOut(ApplyOutPut applyOutPut , HttpServletRequest request , ModelMap modelMap , int id) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new Exception("该用户尚未登录");
        }
        //并未对良品优品做分类
        ApplyOutPut outPut = applyOutRepository.findApplyOutPutById(id);
        String code = outPut.getEnterCode();
        List<EntrepotStatus> entrepotStatuses = entrepotStatusRepository.findEntrepotStatusByEnterCode(code);
        if (entrepotStatuses.isEmpty()){
            throw new Exception("错误，找不到该货物");
        }
        if (entrepotStatuses.size() == 0 || entrepotStatuses == null) {
            outPut.setStatus("被拒绝");
            outPut.setEnsurePersonId(user.getUsername());
            outPut.setApplyDate(new Date());
            request.getSession().setAttribute("message" , "库存中没有该货物!~");
        }else{
            outPut.setStatus("已确认");
            outPut.setEnsurePersonId(user.getUsername());
            outPut.setPackagePersonId(user.getUsername());
            request.getSession().setAttribute("message" , "出库确认成功!~");
        }
        applyOutRepository.save(outPut);
        return "redirect:/applyout-getToBeEnsured?pagenum=1";
    }


    /**
     * 拒绝出库申请
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/applyout-turndown")
    public String turndown(int id , HttpServletRequest request ) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new Exception("该用户尚未登录");
        }
        ApplyOutPut output = applyOutRepository.findApplyOutPutById(id);
        if (output == null) {
            request.getSession().setAttribute("message" , "错误，并未找到该货物!~");
        }else {
            output.setStatus("已退回");
            output.setEnsurePersonId(user.getUsername());
            request.getSession().setAttribute("message","退回成功!~");
        }
        applyOutRepository.save(output);

        return "redirect:/applyout-getToBeEnsured?pagenum=1";
    }

    /**
     * 申请出库
     * @param id
     * @param nums
     * @param applyOutPut
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/applyout" , method = {RequestMethod.GET , RequestMethod.POST})
    public String Applyout(int id , int nums , ApplyOutPut applyOutPut  , HttpServletRequest request ) throws Exception {
        String page = "exit_request_apply";
        int nums2 = 0;
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new Exception("该用户尚未登录");
        }
        Long userid = user.getId();

        ApplyOutPut output = applyOutRepository.findApplyOutPutById(id);
        if (output == null) {
            nums2 = 0;
        }else{
            nums2 = output.getSize();
        }
        List<EntrepotStatus> entrepotStatuses = entrepotStatusRepository.findEntrepotStatusById(id);
        if (entrepotStatuses.isEmpty()) {
            throw new Exception("错误，找不到货物");
        }
        if (entrepotStatuses.size()== 0 || entrepotStatuses == null ){
            applyOutPut.setStatus("已退回");
            request.getSession().setAttribute("message","库存中没有该货物!~");
        }
        if (nums <= entrepotStatuses.get(0).getTotalSize()){
            applyOutPut.setId(entrepotStatuses.get(0).getId());
            applyOutPut.setProductName(entrepotStatuses.get(0).getProductName());
            applyOutPut.setEnterCode(entrepotStatuses.get(0).getEnterCode());
            applyOutPut.setSize(nums+nums2);
            applyOutPut.setEntranceDate(entrepotStatuses.get(0).getEntranceDate());
            applyOutPut.setOutCode("173110027"+applyOutPut.getId());
            applyOutPut.setProduceDate(entrepotStatuses.get(0).getProduceDate());
            applyOutPut.setStatus("待审核");
            applyOutPut.setApplyPersonId(user.getUsername());
            applyOutPut.setAmout(entrepotStatuses.get(0).getSumMoney());
            applyOutPut.setApplyDate(new Date());
            applyOutRepository.save(applyOutPut);
            entrepotStatuses.get(0).setTotalSize(entrepotStatuses.get(0).getTotalSize()-nums);
            entrepotStatusRepository.save(entrepotStatuses.get(0));
            request.getSession().setAttribute("message" ,"申请成功!~");
            if (entrepotStatuses.get(0).getTotalSize() == 0 ){
                entrepotStatusRepository.delete(entrepotStatuses.get(0));
            }
        }else {
            applyOutPut.setStatus("已退回");
            request.getSession().setAttribute("message" , "数量不足，申请失败!~");
        }
        return "redirect:/applyout-exit?pagenum=1";
    }

    /**
     * 新增出库申请，跳转——我的入库申请
     * @param applyOutPut
     * @param request
     * @param modelMap
     * @return
     */
    //    @PostMapping("/applyout-addapply")
    @RequestMapping(value = "/applyout-addapply")
    public String saveApply(ApplyOutPut applyOutPut , BindingResult bindingResult, HttpServletRequest request , ModelMap modelMap) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new Exception("该用户尚未登录");
        }
        Long userId = user.getId();
        applyOutPut.setApplyPersonId(user.getUsername());
        List<EntrepotStatus> entrepotStatuses = entrepotStatusRepository.findEntrepotStatusByEnterCode(applyOutPut.getEnterCode());
        if (entrepotStatuses.size() < 0) {
            request.getSession().setAttribute("message", "仓库中并没有该货物!~");
        } else {
            applyOutPut.setId(entrepotStatuses.get(0).getId());
            applyOutPut.setApplyDate(new Date());
            applyOutPut.setProduceDate(entrepotStatuses.get(0).getProduceDate());
            applyOutPut.setStatus("待审核");
            applyOutRepository.save(applyOutPut);
            request.getSession().setAttribute("message","申请成功，等待审核!~");
        }

        return "redirect:/applyout-getNotAllowed?pagenum=1";
    }

    /**
     * 根据id删除   （我的出库申请列表）
     * @param enterId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "applyout-deleteById" , method = {RequestMethod.GET , RequestMethod.POST})
    public String deleteById(int enterId , HttpServletRequest request ) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new Exception("该用户尚未登录");
        }
        ApplyOutPut applyOutPut = applyOutRepository.findApplyOutPutById(enterId);
        int nums = applyOutPut.getSize();
        List<EntrepotStatus> status =  entrepotStatusRepository.findEntrepotStatusById(enterId);
        //将删除的数据重新加回库存
        if (status.isEmpty()) {
            EntrepotStatus entrepotStatus = new EntrepotStatus();
            entrepotStatus.setId(applyOutPut.getId());
            entrepotStatus.setEntranceDate(applyOutPut.getEntranceDate());
            entrepotStatus.setTotalSize(applyOutPut.getSize());
            entrepotStatus.setEnterCode(applyOutPut.getEnterCode());
            entrepotStatus.setGoodsStatus("未检验");
            entrepotStatus.setProductName(applyOutPut.getProductName());
            entrepotStatus.setUpdateDate(new Date());
            entrepotStatus.setProduceDate(applyOutPut.getProduceDate());
            entrepotStatus.setSumMoney(applyOutPut.getAmout());
            entrepotStatusRepository.save(entrepotStatus);
        }else {
            status.get(0).setTotalSize(nums + status.get(0).getTotalSize());
            entrepotStatusRepository.save(status.get(0));
        }
        applyOutRepository.delete(applyOutPut);
        request.getSession().setAttribute("message" , "删除成功!~");
        return "redirect:/applyout-getNotAllowed?pagenum=1";
    }


    /**
     * 查看待处理的出库申请
     * @param applyOutPut
     * @param modelMap
     * @param request
     * @param pagenum
     * @return
     * @throws Exception
     */
    @RequestMapping("/applyout-getToBeEnsured")
    public String getToBeEnsured(ApplyOutPut applyOutPut , ModelMap modelMap , HttpServletRequest request , int pagenum) throws Exception {
        String page = "exit_apply_wait";
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new Exception("该用户尚未登录");
        }
        if (applyOutPut != null) {
            StringBuffer sql = null;
            try {
                sql = commonRepository.getFiledValues(applyOutPut, pagenum);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            sql.append(" Status != '已确认' AND Status != '已退回' AND applyPersonId = '" + user.getUsername() + "' ");
            int totalSize = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(ApplyOutPut.class)).size();
            sql.append(" LIMIT " + (pagenum - 1) * pagesize + " , " + pagesize);
            List<ApplyOutPut> applyOutPuts = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(ApplyOutPut.class));
            modelMap.addAttribute("applys", applyOutPuts);
            modelMap.addAttribute("page", pagenum);
            modelMap.addAttribute("totalpage", PageUtil.getTotalPage(totalSize,pagenum));
        }else {
            Pageable pageable = PageRequest.of(pagenum, pagesize);
            Page<ApplyOutPut> pager = applyOutRepository.findApplyOutPutByStatusNot("已确认", pageable);
            modelMap.addAttribute("applys", pager.getContent());
            modelMap.addAttribute("page", pagenum);
            modelMap.addAttribute("totalpage" , pager.getTotalPages());
        }
        return page;
    }


    @RequestMapping(value = "/getApplyOutById",method = {RequestMethod.GET , RequestMethod.POST})
    @ResponseBody
    public ApplyOutPut getApplyOutById(int id){
        return applyOutRepository.findApplyOutPutById(id);
    }





    /**
     * 查看出库历史界面
     * @param pagenum
     * @param applyOutPut
     * @param request
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/applyout-getHistory")
    public String getApplyOutHistory(int pagenum , ApplyOutPut applyOutPut , HttpServletRequest request , ModelMap modelMap) throws Exception {
        String page = "exit_apply_history";
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new Exception("该用户尚未登录");
        }
        if (applyOutPut != null) {
            StringBuffer sql = null;
            try {
                sql = commonRepository.getFiledValues(applyOutPut, pagenum);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            sql.append(" Status = '已确认' OR Status = '已退回' ");
            int totalpage = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(ApplyOutPut.class)).size();
            sql.append(" LIMIT " + (pagenum - 1) * pagesize + " , " + pagesize);
            List<ApplyOutPut> applyOutPuts = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(ApplyOutPut.class));
            modelMap.addAttribute("applys", applyOutPuts);
            modelMap.addAttribute("page", pagenum);
            modelMap.addAttribute("totalpage", PageUtil.getTotalPage(totalpage, pagesize));
        }else {
            Pageable pageable = PageRequest.of(pagenum, pagesize);
            Page<ApplyOutPut> pager = applyOutRepository.findApplyOutPutByStatus("已确认", pageable);
            modelMap.addAttribute("applys", pager.getContent());
            modelMap.addAttribute("page", pagenum);
            modelMap.addAttribute("totalpage", pager.getTotalPages());
        }
        return page;
    }

    /**
     * 查找
     * @param outPut
     * @param request
     * @param modelMap
     * @param pagenum
     * @return
     */
    @RequestMapping("/applyout-search")
    public String search(ApplyOutPut outPut , HttpServletRequest request , ModelMap modelMap , @RequestParam(value = "pagenum" , required = false) Integer pagenum){
        String page = "exit_apply_wait";
        String searchItem = request.getParameter("searchItem");
        String searchValue = request.getParameter("searchValue");
        //Integer pagenum= Integer.parseInt(request.getParameter("pagenum"));

        //若页面在待处理的出库界面，则要跳转的是 。 wait.html , 此时的type是1
        //若页面在历史出库界面，则要跳转的是 。 history.html , 此时的type是2
        Integer type = Integer.parseInt(request.getParameter("type"));

        StringBuffer sql = null;
        try {
            sql = commonRepository.getFiledValues(outPut, pagenum);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (searchValue != null || !"".equals(searchValue)) {
            if (type == 1) {
                page = "exit_apply_wait";
                sql.append(searchItem + " like '%" +searchValue +"%' AND Status !='"+"已确认'");
            }else {
                page = "exit_apply_history";
                sql.append(searchItem + " like '%" +searchValue +"%' AND Status ='" +"已确认'");
            }
        }else {
            //1=1代表ture ，如果是单独的sql语句，可以不写where 1=1 ，但是如果在程序中拼装sql语句，比如后面要加条件，就要写这个
            sql.append(" 1 = 1 ");
        }
        int totalpage = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(ApplyOutPut.class)).size();
        List<ApplyOutPut> applyOutPuts = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(ApplyOutPut.class));
        modelMap.addAttribute("applys", applyOutPuts);
        modelMap.addAttribute("searchItem", searchItem);
        modelMap.addAttribute("searchValue", searchValue);
        modelMap.addAttribute("totalpage", PageUtil.getTotalPage(totalpage , pagesize));

        return page;
    }

}
