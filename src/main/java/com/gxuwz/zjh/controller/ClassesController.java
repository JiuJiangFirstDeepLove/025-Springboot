package com.gxuwz.zjh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.Classes;
import com.gxuwz.zjh.entity.User;
import com.gxuwz.zjh.service.IClassesService;
import com.gxuwz.zjh.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 班级表 前端控制器
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Controller
@RequestMapping("/zjh/classes")
public class ClassesController {

    @Autowired
    private IClassesService iClassesService;

    /**
     * 根据是否存在模糊查询内容跳转到不同的分页查询方法
     * @param modelAndView
     * @param classes
     * @param pageNumber
     * @param page
     * @param request
     * @return
     */

    @ResponseBody
    @GetMapping(value = "/nextPage")
    public ModelAndView nextPage(ModelAndView modelAndView, Classes classes, Integer pageNumber,
                                 Page page, HttpServletRequest request) {
        if(request.getSession().getAttribute("result") != null){
            request.getSession().setAttribute("result", "");
        }
        if(classes.getClassId() == null){
            return findClassesAll(modelAndView, page, pageNumber, request);
        }else {
            return findClassesById(modelAndView, classes, pageNumber, page, request);
        }
    }

    /**
     * 根据对应id查询信息
     * @param modelAndView
     * @param classes
     * @param pageNumber
     * @param page
     * @param request
     * @return
     */

    @GetMapping(value = "/findClassesById")
    public ModelAndView findClassesById(ModelAndView modelAndView, Classes classes, Integer pageNumber,
                                     Page page, HttpServletRequest request) {
        if(request.getSession().getAttribute("result") != null){
            request.getSession().setAttribute("result", "");
        }
        // 可以通过 wrapper 进行筛选!!!
        QueryWrapper<Classes> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("class_id");
        String[] keysWord = null;
        // 对User进行模糊查询!!!
        if(classes != null & classes.getClassId() != null){
            wrapper.like("class_id", classes.getClassId());
            modelAndView.addObject("classes", classes);
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
        IPage<Classes> classesIPage = iClassesService.selectClassesPage(page, wrapper);
        // 存放一个数组用来让foreach遍历
        int[] pagesList = new int[(int)classesIPage.getPages()];
        for(int i=0; i< (int)classesIPage.getPages(); i++){
            pagesList[i] = i+1;
        }
        modelAndView.addObject("pagesList", pagesList);
        // 存放page，内有当前页数
        modelAndView.addObject("page", page);
        System.out.println("总条数"+classesIPage.getTotal());
        System.out.println("总页数"+classesIPage.getPages());
        // 存放总页数
        modelAndView.addObject("pages", (int)classesIPage.getPages());
        modelAndView.addObject("numberPages", classesIPage.getTotal());
        List<Classes> classesList = classesIPage.getRecords();
        System.out.println("classesList = "+classesList);
        modelAndView.addObject("classesList", classesList);

        modelAndView.setViewName("classes/classes_list");
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
    @GetMapping(value = "/findClassesAll")
    public ModelAndView findClassesAll(ModelAndView modelAndView, Page page, Integer pageNumber,
                                    HttpServletRequest request) {
        // 可以通过 wrapper 进行筛选!!!
        QueryWrapper<Classes> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("class_id");
        // Current,页码 + Size,每页条数
        if(pageNumber == null){
            page.setCurrent(1);
        }else {
            page.setCurrent((long)pageNumber);
        }
        // 默认每页6行数据！
        page.setSize(6);
        // 调用分页查询方法！!
        IPage<Classes> classesIPage = iClassesService.selectClassesPage(page, wrapper);
        HttpSession session = request.getSession();
        // 存放page，内有当前页数
        modelAndView.addObject("page", page);
        System.out.println("总条数"+classesIPage.getTotal());
        System.out.println("总页数"+classesIPage.getPages());
        // 存放总页数
        modelAndView.addObject("pages", (int)classesIPage.getPages());
        // 存放一个数组用来让foreach遍历
        int[] pagesList = new int[(int)classesIPage.getPages()];
        for(int i=0; i< (int)classesIPage.getPages(); i++){
            pagesList[i] = i+1;
        }
        modelAndView.addObject("pagesList", pagesList);
        modelAndView.addObject("numberPages", classesIPage.getTotal());
        List<Classes> classesList = classesIPage.getRecords();
        System.out.println("classesList = "+classesList);
        modelAndView.addObject("classesList", classesList);

        modelAndView.setViewName("classes/classes_list");
        return modelAndView;
    }

    /**
     * 添加用户信息 / 修改用户信息
     * @param classes
     * @return
     */
    @PostMapping(value = "/addEditClasses")
    public String addEditClasses(Classes classes, HttpServletRequest request) {
        Classes classes1 = iClassesService.findClassesById(classes);
        System.out.println("classes = " + classes);
        System.out.println("classes1 = " + classes1);
        // 新增用户信息
        if(classes1 == null){
            System.out.println("进入新增用户");
            try {
                iClassesService.addClasses(classes);
                request.getSession().setAttribute("result", "addTrue");
            }catch (Exception e){
                request.getSession().setAttribute("result", "addFalse");
            }
        }
        // 修改用户信息
        if(classes1 != null){
            System.out.println("进入修改用户");
            try {
                iClassesService.updateClassesById(classes);
                request.getSession().setAttribute("result", "editTrue");
            }catch (Exception e){
                request.getSession().setAttribute("result", "editFalse");
            }
        }
        return "redirect:/zjh/classes/findClassesAll";
    }

    /**
     * 删除用户信息
     * @param classId
     * @return
     */
    @GetMapping(value = "/deleteClassesById")
    public String deleteClassesById(HttpServletRequest request, String classId) {
        Classes classes = new Classes();
        classes.setClassId(classId);
        try {
            iClassesService.deleteClassesById(classes);
            request.getSession().setAttribute("result", "deleteTrue");
        }catch (Exception e){

        }
        return "redirect:/zjh/classes/findClassesAll";
    }

}
