package com.jevin.controller;

/*
 *  @项目名：  Store_manager 
 *  @包名：    com.jevin.controller
 *  @文件名:   UserController
 *  @创建者:   85169
 *  @创建时间:  2019/9/11 16:12
 *  @描述：    TODO
 */

import com.jevin.dao.CommonRepository;
import com.jevin.dao.UserRepository;
import com.jevin.pojo.User;
import com.jevin.service.IndexService;
import com.jevin.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonRepository commonRepository;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IndexService indexService;

    //每页显示条数
    private Integer pagesize = 15;

    @GetMapping("/")
    public String login(){
        return "login";
    }
    @RequestMapping(value = "/dologin" , method = {RequestMethod.GET,RequestMethod.POST})
    public String dologin(User user, ModelMap modelMap, HttpServletRequest request){
        String page = "index";
        int size = 0;
        List<User> userList = userRepository.findUsersByUsername(user.getUsername());
        //此用户不存在
        if (userList.size() == 0){
            modelMap.addAttribute("message","用户名或密码不正确");
            page = "login_fail";
        } else {
            if (!userList.get(0).getPassword().equals(user.getPassword())){
                modelMap.addAttribute("message","用户名或密码不正确");
                page = "login_fail";
            } else {
                User loginuser = userList.get(0);
                request.getSession().setAttribute("user",loginuser);
            }
            System.out.println("登录用户：" + userList.get(0));
        }
        //登录成功后需要加载首页——展示昨日库存数，昨日出库数，库存总数。
        int enterSize = indexService.getYestdayApplyEnterCount();
        int outSize = indexService.getYestdayApplyOutCount();
        int entreotSize = indexService.getEntrePotSize();
        modelMap.addAttribute("enterSize" , enterSize);
        modelMap.addAttribute("outSize" , outSize);
        modelMap.addAttribute("entreotSize" , entreotSize);

        return page;
    }

    @RequestMapping("/user-updatePwd")
    public String uPwdView(){
        return "user_change_pwd";
    }

    //保存用户
    @RequestMapping("/user-save")
    public String saveUser(User user) throws Exception{
        List<User> userList = userRepository.findUsersByUsername(user.getUsername());
        if (userList.size() >0){
            throw new Exception("该用户已存在，请重新选择不存在的用户名！");
        }
        try{
            user.setPassword(user.getPassword());
            user.setStatus(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        userRepository.save(user);
        return "redirect:/user-getAll?pagenum=1";
    }

    //查找所有的用户
    @RequestMapping("/user-getAll")
    public String getAlluser(User user ,ModelMap modelMap,int pagenum){
        String page = "user_list";
        if (user != null){
            StringBuffer sql = null;
            try {
                sql = commonRepository.getFiledValues(user,pagenum);
            }catch (Exception e){
                e.printStackTrace();
            }
            sql.append("1 = 1");
            //使用BeanPropertyRowMapper 将数据库查询结果转化为java对象
            //jdbcTemplate查询数据库，获取List列表
            int totalpage = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper(User.class)).size();
            /**
             * pagenum ：当前页码
             * pagesize：每页显示条数
             * "select * form table limit x,y"  检索  x+1  ————（x+y）行
             * 若pagenum = 2  ， pagesize = 15  检索  16  ———— 31 行
             */
            sql.append(" LIMIT " +(pagenum - 1) * pagesize + "," + pagesize);
            List<User> users = jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper(User.class));
            System.out.println("已确认的申请：" + users);
            modelMap.addAttribute("users", users);
            modelMap.addAttribute("page",pagenum);
            modelMap.addAttribute("totalpage", PageUtil.getTotalPage(totalpage,pagesize));
        }
        return page;
    }

    //禁用账户
    @RequestMapping("/user-disabledUser")
    public String disableUser(Long id, ModelMap modelMap, HttpSession session){
        //获取当前用户信息
        User user1 = (User) session.getAttribute("user");
        System.out.println("禁用用户id：" + id);
        if (user1.getId() == id){
            session.setAttribute("message","操作错误，无法禁用自己");
            return "redirect:/user-getAll?pagenum=1";
        }
        User user = userRepository.findUsersById(id);
        if (user != null){
            user.setStatus(1);
            userRepository.save(user);
            System.out.println("禁用账户：" + user);
        }
        return "redirect:/user-getAll?pagenum=1";
    }

    //启用账户
    @RequestMapping("/user-enableUser")
    public String enableUser(Long id , ModelMap modelMap , HttpSession session){
        //获取当前用户信息
        User user1 = (User) session.getAttribute("user");
        System.out.println("启用账户id：" + id);
        if (user1.getId() == id){
            session.setAttribute("message" , "操作错误，无法启用自己");
            return "redirect:/user-getAll?pagenum=1";
        }
        User user = userRepository.findUsersById(id);
        if (user != null){
            user.setStatus(2);
            userRepository.save(user);
            System.out.println("启用账户：" + user);
        }
        return "redirect:/user-getAll?pagenum=1";
    }
    //删除账户
    @RequestMapping("/user-delete")
    public String deleteUser(Long id , ModelMap modelMap , HttpSession session){
        User user1 = (User) session.getAttribute("user");
        System.out.println("删除账户id：" + id);
        if (user1.getId() == id){
            session.setAttribute("message" , "操作错误，无法删除自己");
            return "redirect:/user-getAll?pagenum=1";
        }
        session.setAttribute("message" , "删除了用户：" + userRepository.findUsersById(id).getUsername());
        userRepository.deleteById(id);
        return "redirect:/user-getAll?pagenum=1";
    }

    //查找用户
    @RequestMapping("/user-searchById")
    @ResponseBody
    public User searchUser(Long id , HttpSession session){
        User user = userRepository.findUsersById(id);
        return user;
    }

    //编辑用户
    @RequestMapping("/user-edit")
    public String editUser(User user){
        user.setPassword(user.getPassword());
        System.out.println(user);
        userRepository.save(user);
        return "redirect:/user-getAll?pagenum=1";
    }
    //修改密码
    @RequestMapping("/user-dopwd")
    public String changePwd(String oldPassword , String newPassword , String rePassword , ModelMap modelMap , HttpSession session) throws Exception {
        int i = 0 ;
        if (!newPassword.equals(rePassword)){
            System.err.println("修改密码" + oldPassword + "   " + newPassword + rePassword);;
            throw new Exception("新密码与确认密码不同");
        }

        User user = (User) session.getAttribute("user");
        if (user == null){
            throw new Exception("会话过期，请重新登录");
        }
        List<User> userList = userRepository.findUsersByUsername(user.getUsername());
        if (userList.size() == 0){
            throw new Exception("该用户不存在");
        }
        System.out.println("查找到的用户：" + userList);
        //检查密码
        for (User user1 : userList){
            if (user1.getPassword().equals(oldPassword)){
                i++;
                break;
            }
        }
        if (i == 0){
            throw new Exception("密码错误");
        }
        user.setPassword(newPassword);
        userRepository.save(user);
        session.removeAttribute("user");

        return "login";
    }

     //退出系统
    @RequestMapping("user-logout")
    public String Userlogout(HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null){
            session.removeAttribute("user");
        }

        return "login";
    }

}
