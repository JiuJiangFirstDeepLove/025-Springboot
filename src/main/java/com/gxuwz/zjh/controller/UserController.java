package com.gxuwz.zjh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.Leave;
import com.gxuwz.zjh.entity.User;
import com.gxuwz.zjh.service.ILeaveService;
import com.gxuwz.zjh.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Controller
@RequestMapping("/zjh/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 根据是否存在user模糊查询内容跳转到不同的分页查询方法
     * @param modelAndView
     * @param user
     * @param pageNumber
     * @param page
     * @param request
     * @return
     */

    @ResponseBody
    @GetMapping(value = "/nextPage")
    public ModelAndView nextPage(ModelAndView modelAndView, User user, Integer pageNumber,
                                     Page page, HttpServletRequest request) {
        if(request.getSession().getAttribute("result") != null){
            request.getSession().setAttribute("result", "");
        }
        if(user.getUserId() == null){
            return findUserAll(modelAndView, page, pageNumber, request);
        }else {
            return findUserById(modelAndView, user, pageNumber, page, request);
        }
    }

    /**
     * 根据对应用户id查询该用户信息
     * @param modelAndView
     * @param user
     * @param pageNumber
     * @param page
     * @param request
     * @return
     */

    @GetMapping(value = "/findUserById")
    public ModelAndView findUserById(ModelAndView modelAndView, User user, Integer pageNumber,
                                     Page page, HttpServletRequest request) {
        if(request.getSession().getAttribute("result") != null){
            request.getSession().setAttribute("result", "");
        }
        // 可以通过 wrapper 进行筛选!!!
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("user_id");
        String[] keysWord = null;
        // 对User进行模糊查询!!!
        if(user != null & user.getUserId() != null){
            wrapper.like("user_id", user.getUserId());
            modelAndView.addObject("user", user);
        }
        // Current,页码 + Size,每页条数
        if(pageNumber == null){
            page.setCurrent(1);
        }else {
            page.setCurrent((long)pageNumber);
        }
        // 默认每页6行数据！
        page.setSize(6);
        // 调用分页查询方法！!
        IPage<User> userIPage = iUserService.selectUserPage(page, wrapper);
        // 存放一个数组用来让foreach遍历
        int[] pagesList = new int[(int)userIPage.getPages()];
        for(int i=0; i< (int)userIPage.getPages(); i++){
            pagesList[i] = i+1;
        }
        modelAndView.addObject("pagesList", pagesList);
        // 存放page，内有当前页数
        modelAndView.addObject("page", page);
        System.out.println("总条数"+userIPage.getTotal());
        System.out.println("总页数"+userIPage.getPages());
        // 存放总页数
        modelAndView.addObject("pages", (int)userIPage.getPages());
        modelAndView.addObject("numberPages", userIPage.getTotal());
        List<User> userList = userIPage.getRecords();
        System.out.println("userList = "+userList);
        modelAndView.addObject("userList", userList);

        modelAndView.setViewName("user/user_list");
        return modelAndView;
    }

    /**
     * 查询全部用户信息
     * @param modelAndView
     * @param page
     * @param pageNumber
     * @param request
     * @return ModelAndView
     */
    @GetMapping(value = "/findUserAll")
    public ModelAndView findUserAll(ModelAndView modelAndView, Page page, Integer pageNumber,
                                    HttpServletRequest request) {
        // 可以通过 wrapper 进行筛选!!!
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("user_id");
        // Current,页码 + Size,每页条数
        if(pageNumber == null){
            page.setCurrent(1);
        }else {
            page.setCurrent((long)pageNumber);
        }
        // 默认每页6行数据！
        page.setSize(6);
        // 调用分页查询方法！!
        IPage<User> userIPage = iUserService.selectUserPage(page, wrapper);
        HttpSession session = request.getSession();
        // 存放page，内有当前页数
        modelAndView.addObject("page", page);
        System.out.println("总条数"+userIPage.getTotal());
        System.out.println("总页数"+userIPage.getPages());
        // 存放总页数
        modelAndView.addObject("pages", (int)userIPage.getPages());
        // 存放一个数组用来让foreach遍历
        int[] pagesList = new int[(int)userIPage.getPages()];
        for(int i=0; i< (int)userIPage.getPages(); i++){
            pagesList[i] = i+1;
        }
        modelAndView.addObject("pagesList", pagesList);
        modelAndView.addObject("numberPages", userIPage.getTotal());
        List<User> userList = userIPage.getRecords();
        System.out.println("userList = "+userList);
        modelAndView.addObject("userList", userList);

        modelAndView.setViewName("user/user_list");
        return modelAndView;
    }

    /**
     * 添加用户信息 / 修改用户信息
     * @param user
     * @return
     */
    @PostMapping(value = "/addEditUser")
    public String addEditUser(User user, HttpServletRequest request) {
        User user1 = iUserService.findUserById(user);
        System.out.println("user = " + user);
        System.out.println("user1 = " + user1);
        // 新增用户信息
        if(user1 == null){
            System.out.println("进入新增用户");
            try {
                iUserService.addUser(user);
                request.getSession().setAttribute("result", "addTrue");
            }catch (Exception e){
                request.getSession().setAttribute("result", "addFalse");
            }
        }
        // 修改用户信息
        if(user1 != null){
            System.out.println("进入修改用户");
            try {
                iUserService.updateUserById(user);
                request.getSession().setAttribute("result", "editTrue");
            }catch (Exception e){
                request.getSession().setAttribute("result", "editFalse");
            }
        }
        return "redirect:/zjh/user/findUserAll";
    }

    /**
     * 删除用户信息
     * @param userId
     * @return
     */
    @GetMapping(value = "/deleteUserById")
    public String deleteUserById(HttpServletRequest request, String userId) {
        User user = new User();
        user.setUserId(userId);
        try {
            iUserService.deleteUserById(user);
            request.getSession().setAttribute("result", "deleteTrue");
        }catch (Exception e){

        }
        return "redirect:/zjh/user/findUserAll";
    }


    /**
     * 打开用户个人信息界面
     * @param modelAndView
     * @return
     */
    @GetMapping(value = "/editUser")
    public ModelAndView editUser(ModelAndView modelAndView, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/user/user_me");
        return modelAndView;
    }

    /**
     * 用户修改个人信息
     * @param user
     * @return
     */
    @PostMapping(value = "/updateUser")
    public ModelAndView updateUser(ModelAndView modelAndView, User user, HttpServletRequest request,
                                   @Param("password_edit") String password_edit,
                                   @Param("confirm_password") String confirm_password) {
        String password = user.getPassword();
        User user1 = iUserService.findUserById(user);
        if(user1.getPassword().equals(password)){
            if(password_edit != "" && confirm_password != ""){
                if(password_edit.equals(confirm_password)){
                    user.setPassword(confirm_password);
                    iUserService.updateUserById(user);
                    request.setAttribute("error", "true");
                }else {
                    request.setAttribute("error", "twoNewPwdNone");
                }
            }else {
                request.setAttribute("error", "newPwdNone");
            }
        }else {
            request.setAttribute("error", "oldPwdError");
        }
        modelAndView.setViewName("user/user_me");
        return modelAndView;
    }

}
