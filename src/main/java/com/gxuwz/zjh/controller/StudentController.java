package com.gxuwz.zjh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.Student;
import com.gxuwz.zjh.entity.User;
import com.gxuwz.zjh.service.IStudentService;
import com.gxuwz.zjh.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Controller
@RequestMapping("/zjh/student")
public class StudentController {

    @Autowired
    private IStudentService iStudentService;

    /**
     * 根据是否存在模糊查询内容跳转到不同的分页查询方法
     * @param modelAndView
     * @param student
     * @param pageNumber
     * @param page
     * @param request
     * @return
     */

    @ResponseBody
    @GetMapping(value = "/nextPage")
    public ModelAndView nextPage(ModelAndView modelAndView, Student student, Integer pageNumber,
                                 Page page, HttpServletRequest request) {
        if(request.getSession().getAttribute("result") != null){
            request.getSession().setAttribute("result", "");
        }
        if(student.getStuId() == null){
            return findStudentAll(modelAndView, page, pageNumber, request);
        }else {
            return findStudentById(modelAndView, student, pageNumber, page, request);
        }
    }

    /**
     * 根据对应用户id查询信息
     * @param modelAndView
     * @param student
     * @param pageNumber
     * @param page
     * @param request
     * @return
     */

    @GetMapping(value = "/findStudentById")
    public ModelAndView findStudentById(ModelAndView modelAndView, Student student, Integer pageNumber,
                                     Page page, HttpServletRequest request) {
        if(request.getSession().getAttribute("result") != null){
            request.getSession().setAttribute("result", "");
        }
        // 可以通过 wrapper 进行筛选!!!
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("stu_id");
        String[] keysWord = null;
        // 对User进行模糊查询!!!
        if(student != null & student.getStuId() != null){
            wrapper.like("stu_id", student.getStuId());
            modelAndView.addObject("student", student);
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
        IPage<Student> studentIPage = iStudentService.selectStudentPage(page, wrapper);
        // 存放一个数组用来让foreach遍历
        int[] pagesList = new int[(int)studentIPage.getPages()];
        for(int i=0; i< (int)studentIPage.getPages(); i++){
            pagesList[i] = i+1;
        }
        modelAndView.addObject("pagesList", pagesList);
        // 存放page，内有当前页数
        modelAndView.addObject("page", page);
        System.out.println("总条数"+studentIPage.getTotal());
        System.out.println("总页数"+studentIPage.getPages());
        // 存放总页数
        modelAndView.addObject("pages", (int)studentIPage.getPages());
        modelAndView.addObject("numberPages", studentIPage.getTotal());
        List<Student> studentList = studentIPage.getRecords();
        System.out.println("studentList = "+studentList);
        modelAndView.addObject("studentList", studentList);

        modelAndView.setViewName("student/student_list");
        return modelAndView;
    }

    /**
     * 查询全部信息
     * @param modelAndView
     * @param page
     * @param pageNumber
     * @param request
     * @return ModelAndView
     */
    @GetMapping(value = "/findStudentAll")
    public ModelAndView findStudentAll(ModelAndView modelAndView, Page page, Integer pageNumber,
                                    HttpServletRequest request) {
        // 可以通过 wrapper 进行筛选!!!
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("stu_id");
        // Current,页码 + Size,每页条数
        if(pageNumber == null){
            page.setCurrent(1);
        }else {
            page.setCurrent((long)pageNumber);
        }
        // 默认每页6行数据！
        page.setSize(6);
        // 调用分页查询方法！!
        IPage<Student> studentIPage = iStudentService.selectStudentPage(page, wrapper);
        HttpSession session = request.getSession();
        // 存放page，内有当前页数
        modelAndView.addObject("page", page);
        System.out.println("总条数"+studentIPage.getTotal());
        System.out.println("总页数"+studentIPage.getPages());
        // 存放总页数
        modelAndView.addObject("pages", (int)studentIPage.getPages());
        // 存放一个数组用来让foreach遍历
        int[] pagesList = new int[(int)studentIPage.getPages()];
        for(int i=0; i< (int)studentIPage.getPages(); i++){
            pagesList[i] = i+1;
        }
        modelAndView.addObject("pagesList", pagesList);
        modelAndView.addObject("numberPages", studentIPage.getTotal());
        List<Student> studentList = studentIPage.getRecords();
        System.out.println("studentList = "+studentList);
        modelAndView.addObject("studentList", studentList);

        modelAndView.setViewName("student/student_list");
        return modelAndView;
    }

    /**
     * 添加信息 / 修改信息
     * @param student
     * @return
     */
    @PostMapping(value = "/addEditStudent")
    public String addEditStudent(Student student, HttpServletRequest request) {
        Student student1 = iStudentService.findStudentById(student);
        System.out.println("student = " + student);
        System.out.println("student1 = " + student1);
        // 新增用户信息
        if(student1 == null){
            System.out.println("进入新增用户");
            try {
                iStudentService.addStudent(student);
                request.getSession().setAttribute("result", "addTrue");
            }catch (Exception e){
                request.getSession().setAttribute("result", "addFalse");
            }
        }
        // 修改用户信息
        if(student1 != null){
            System.out.println("进入修改用户");
            try {
                iStudentService.updateStudentById(student);
                request.getSession().setAttribute("result", "editTrue");
            }catch (Exception e){
                request.getSession().setAttribute("result", "editFalse");
            }
        }
        return "redirect:/zjh/student/findStudentAll";
    }

    /**
     * 删除信息
     * @param stuId
     * @return
     */
    @GetMapping(value = "/deleteStudentById")
    public String deleteStudentById(HttpServletRequest request, @Param("stuId") String stuId) {
        Student student = new Student();
        student.setStuId(stuId);
        try {
            iStudentService.deleteStudentById(student);
            request.getSession().setAttribute("result", "deleteTrue");
        }catch (Exception e){

        }
        return "redirect:/zjh/student/findStudentAll";
    }

}
