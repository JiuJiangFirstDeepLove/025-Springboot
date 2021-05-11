package com.gxuwz.zjh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.Classes;
import com.gxuwz.zjh.entity.Course;
import com.gxuwz.zjh.service.IClassesService;
import com.gxuwz.zjh.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Controller
@RequestMapping("/zjh/course")
public class CourseController {

    @Autowired
    private ICourseService iCourseService;

    /**
     * 根据是否存在模糊查询内容跳转到不同的分页查询方法
     * @param modelAndView
     * @param course
     * @param pageNumber
     * @param page
     * @param request
     * @return
     */

    @ResponseBody
    @GetMapping(value = "/nextPage")
    public ModelAndView nextPage(ModelAndView modelAndView, Course course, Integer pageNumber,
                                 Page page, HttpServletRequest request) {
        if(request.getSession().getAttribute("result") != null){
            request.getSession().setAttribute("result", "");
        }
        if(course.getCourseId() == null){
            return findCourseAll(modelAndView, page, pageNumber, request);
        }else {
            return findCourseById(modelAndView, course, pageNumber, page, request);
        }
    }

    /**
     * 根据对应id查询信息
     * @param modelAndView
     * @param course
     * @param pageNumber
     * @param page
     * @param request
     * @return
     */

    @GetMapping(value = "/findCourseById")
    public ModelAndView findCourseById(ModelAndView modelAndView, Course course, Integer pageNumber,
                                        Page page, HttpServletRequest request) {
        if(request.getSession().getAttribute("result") != null){
            request.getSession().setAttribute("result", "");
        }
        // 可以通过 wrapper 进行筛选!!!
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("course_id");
        String[] keysWord = null;
        // 对User进行模糊查询!!!
        if(course != null & course.getCourseId() != null){
            wrapper.like("course_id", course.getCourseId());
            modelAndView.addObject("course", course);
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
        IPage<Course> courseIPage = iCourseService.selectCoursePage(page, wrapper);
        // 存放一个数组用来让foreach遍历
        int[] pagesList = new int[(int)courseIPage.getPages()];
        for(int i=0; i< (int)courseIPage.getPages(); i++){
            pagesList[i] = i+1;
        }
        modelAndView.addObject("pagesList", pagesList);
        // 存放page，内有当前页数
        modelAndView.addObject("page", page);
        System.out.println("总条数"+courseIPage.getTotal());
        System.out.println("总页数"+courseIPage.getPages());
        // 存放总页数
        modelAndView.addObject("pages", (int)courseIPage.getPages());
        modelAndView.addObject("numberPages", courseIPage.getTotal());
        List<Course> courseList = courseIPage.getRecords();
        System.out.println("courseList = "+courseList);
        modelAndView.addObject("courseList", courseList);

        modelAndView.setViewName("course/course_list");
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
    @GetMapping(value = "/findCourseAll")
    public ModelAndView findCourseAll(ModelAndView modelAndView, Page page, Integer pageNumber,
                                       HttpServletRequest request) {
        // 可以通过 wrapper 进行筛选!!!
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("course_id");
        // Current,页码 + Size,每页条数
        if(pageNumber == null){
            page.setCurrent(1);
        }else {
            page.setCurrent((long)pageNumber);
        }
        // 默认每页6行数据！
        page.setSize(6);
        // 调用分页查询方法！!
        IPage<Course> courseIPage = iCourseService.selectCoursePage(page, wrapper);
        HttpSession session = request.getSession();
        // 存放page，内有当前页数
        modelAndView.addObject("page", page);
        System.out.println("总条数"+courseIPage.getTotal());
        System.out.println("总页数"+courseIPage.getPages());
        // 存放总页数
        modelAndView.addObject("pages", (int)courseIPage.getPages());
        // 存放一个数组用来让foreach遍历
        int[] pagesList = new int[(int)courseIPage.getPages()];
        for(int i=0; i< (int)courseIPage.getPages(); i++){
            pagesList[i] = i+1;
        }
        modelAndView.addObject("pagesList", pagesList);
        modelAndView.addObject("numberPages", courseIPage.getTotal());
        List<Course> courseList = courseIPage.getRecords();
        System.out.println("courseList = "+courseList);
        modelAndView.addObject("courseList", courseList);

        modelAndView.setViewName("course/course_list");
        return modelAndView;
    }

    /**
     * 添加用户信息 / 修改用户信息
     * @param course
     * @return
     */
    @PostMapping(value = "/addEditCourse")
    public String addEditCourse(Course course, HttpServletRequest request) {
        Course course1 = iCourseService.findCourseById(course);
        System.out.println("course = " + course);
        System.out.println("course1 = " + course1);
        // 新增用户信息
        if(course1 == null){
            System.out.println("进入新增用户");
            try {
                iCourseService.addCourse(course);
                request.getSession().setAttribute("result", "addTrue");
            }catch (Exception e){
                request.getSession().setAttribute("result", "addFalse");
            }
        }
        // 修改用户信息
        if(course1 != null){
            System.out.println("进入修改用户");
            try {
                iCourseService.updateCourseById(course);
                request.getSession().setAttribute("result", "editTrue");
            }catch (Exception e){
                request.getSession().setAttribute("result", "editFalse");
            }
        }
        return "redirect:/zjh/course/findCourseAll";
    }

    /**
     * 删除用户信息
     * @param courseId
     * @return
     */
    @GetMapping(value = "/deleteCourseById")
    public String deleteCourseById(HttpServletRequest request, String courseId) {
        Course course = new Course();
        course.setCourseId(courseId);
        try {
            iCourseService.deleteCourseById(course);
            request.getSession().setAttribute("result", "deleteTrue");
        }catch (Exception e){

        }
        return "redirect:/zjh/course/findCourseAll";
    }

}
