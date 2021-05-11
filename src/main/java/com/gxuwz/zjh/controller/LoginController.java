package com.gxuwz.zjh.controller;

import com.gxuwz.zjh.entity.*;
import com.gxuwz.zjh.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * <p>
 * 登录 处理器
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */

@Controller
public class LoginController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IClassesService iClassesService;
    @Autowired
    private IDepService iDepService;
    @Autowired
    private ICourseService iCourseService;

    @GetMapping("/toLoginPage")
    public String toLoginPage(Model model){
        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        return "login";
    }

    /**
     * 默认跳转登录页面
     * @param modelAndView
     * @return
     */
    @GetMapping("/")
    public ModelAndView loginStart(ModelAndView modelAndView){
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /**
     * 登录判断
     * @param user
     * @param modelAndView
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login.action")
    public ModelAndView loginIndex(User user, ModelAndView modelAndView,
                                   HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(user.getUserId() == null){
            request.setAttribute("result", "none1");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }else if(user.getPassword() == null | user.getPassword() == "" ){
            request.setAttribute("result", "none2");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }else {
            User user2 = iUserService.findUserById(user);
            List<Classes> classesList = iClassesService.findClassesAll();
            List<Department> deptList = iDepService.findDepAll();
            List<Course> courseList = iCourseService.findCourseAll();
            if (user2 == null) {
                request.setAttribute("result", "full");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                if (user2.getPassword().equals(user.getPassword())) {
                    request.getSession().setAttribute("user", user2);
                    request.getSession().setAttribute("classesList", classesList);
                    request.getSession().setAttribute("deptList", deptList);
                    request.getSession().setAttribute("courseList", courseList);
                    modelAndView.setViewName("index");
                    return modelAndView;
                }
            }
        }

        modelAndView.setViewName("login");
        return modelAndView;
    }

    /**
     * 测试页面List
     * @param modelAndView
     * @return
     */
    @GetMapping("/formTestList")
    public ModelAndView formTestList(ModelAndView modelAndView){
        modelAndView.setViewName("test/test_list");
        return modelAndView;
    }

}
