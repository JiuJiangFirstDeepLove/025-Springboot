package com.gxuwz.zjh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.Instructor;
import com.gxuwz.zjh.service.IInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 辅导员表 前端控制器
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Controller
@RequestMapping("/zjh/instructor")
public class InstructorController {

    @Autowired
    private IInstructorService iInstructorService;

    /**
     * 根据是否存在模糊查询内容跳转到不同的分页查询方法
     * @param modelAndView
     * @param instructor
     * @param pageNumber
     * @param page
     * @param request
     * @return
     */

    @ResponseBody
    @GetMapping(value = "/nextPage")
    public ModelAndView nextPage(ModelAndView modelAndView, Instructor instructor, Integer pageNumber,
                                 Page page, HttpServletRequest request) {
        if(request.getSession().getAttribute("result") != null){
            request.getSession().setAttribute("result", "");
        }
        if(instructor.getInstId() == null){
            return findInstructorAll(modelAndView, page, pageNumber, request);
        }else {
            return findInstructorById(modelAndView, instructor, pageNumber, page, request);
        }
    }

    /**
     * 根据对应id查询信息
     * @param modelAndView
     * @param instructor
     * @param pageNumber
     * @param page
     * @param request
     * @return
     */

    @GetMapping(value = "/findInstructorById")
    public ModelAndView findInstructorById(ModelAndView modelAndView, Instructor instructor, Integer pageNumber,
                                    Page page, HttpServletRequest request) {
        if(request.getSession().getAttribute("result") != null){
            request.getSession().setAttribute("result", "");
        }
        // 可以通过 wrapper 进行筛选!!!
        QueryWrapper<Instructor> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("inst_id");
        String[] keysWord = null;
        // 对User进行模糊查询!!!
        if(instructor != null & instructor.getInstId() != null){
            wrapper.like("inst_id", instructor.getInstId());
            modelAndView.addObject("instructor", instructor);
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
        IPage<Instructor> instructorIPage = iInstructorService.selectInstructorPage(page, wrapper);
        // 存放一个数组用来让foreach遍历
        int[] pagesList = new int[(int)instructorIPage.getPages()];
        for(int i=0; i< (int)instructorIPage.getPages(); i++){
            pagesList[i] = i+1;
        }
        modelAndView.addObject("pagesList", pagesList);
        // 存放page，内有当前页数
        modelAndView.addObject("page", page);
        System.out.println("总条数"+instructorIPage.getTotal());
        System.out.println("总页数"+instructorIPage.getPages());
        // 存放总页数
        modelAndView.addObject("pages", (int)instructorIPage.getPages());
        modelAndView.addObject("numberPages", instructorIPage.getTotal());
        List<Instructor> instructorList = instructorIPage.getRecords();
        System.out.println("instructorList = "+instructorList);
        modelAndView.addObject("instructorList", instructorList);

        modelAndView.setViewName("instructor/instructor_list");
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
    @GetMapping(value = "/findInstructorAll")
    public ModelAndView findInstructorAll(ModelAndView modelAndView, Page page, Integer pageNumber,
                                   HttpServletRequest request) {
        // 可以通过 wrapper 进行筛选!!!
        QueryWrapper<Instructor> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("inst_id");
        // Current,页码 + Size,每页条数
        if(pageNumber == null){
            page.setCurrent(1);
        }else {
            page.setCurrent((long)pageNumber);
        }
        // 默认每页6行数据！
        page.setSize(6);
        // 调用分页查询方法！!
        IPage<Instructor> instructorIPage = iInstructorService.selectInstructorPage(page, wrapper);
        HttpSession session = request.getSession();
        // 存放page，内有当前页数
        modelAndView.addObject("page", page);
        System.out.println("总条数"+instructorIPage.getTotal());
        System.out.println("总页数"+instructorIPage.getPages());
        // 存放总页数
        modelAndView.addObject("pages", (int)instructorIPage.getPages());
        // 存放一个数组用来让foreach遍历
        int[] pagesList = new int[(int)instructorIPage.getPages()];
        for(int i=0; i< (int)instructorIPage.getPages(); i++){
            pagesList[i] = i+1;
        }
        modelAndView.addObject("pagesList", pagesList);
        modelAndView.addObject("numberPages", instructorIPage.getTotal());
        List<Instructor> instructorList = instructorIPage.getRecords();
        System.out.println("instructorList = "+instructorList);
        modelAndView.addObject("instructorList", instructorList);

        modelAndView.setViewName("instructor/instructor_list");
        return modelAndView;
    }

    /**
     * 添加用户信息 / 修改用户信息
     * @param instructor
     * @return
     */
    @PostMapping(value = "/addEditInstructor")
    public String addEditInstructor(Instructor instructor, HttpServletRequest request) {
        Instructor instructor1 = iInstructorService.findInstructorById(instructor);
        System.out.println("instructor = " + instructor);
        System.out.println("instructor1 = " + instructor1);
        // 新增用户信息
        if(instructor1 == null){
            System.out.println("进入新增用户");
            try {
                iInstructorService.addInstructor(instructor);
                request.getSession().setAttribute("result", "addTrue");
            }catch (Exception e){
                request.getSession().setAttribute("result", "addFalse");
            }
        }
        // 修改用户信息
        if(instructor1 != null){
            System.out.println("进入修改用户");
            try {
                iInstructorService.updateInstructorById(instructor);
                request.getSession().setAttribute("result", "editTrue");
            }catch (Exception e){
                request.getSession().setAttribute("result", "editFalse");
            }
        }
        return "redirect:/zjh/instructor/findInstructorAll";
    }

    /**
     * 删除用户信息
     * @param instId
     * @return
     */
    @GetMapping(value = "/deleteInstructorById")
    public String deleteInstructorById(HttpServletRequest request, String instId) {
        Instructor instructor = new Instructor();
        instructor.setInstId(instId);
        try {
            iInstructorService.deleteInstructorById(instructor);
            request.getSession().setAttribute("result", "deleteTrue");
        }catch (Exception e){

        }
        return "redirect:/zjh/instructor/findInstructorAll";
    }
    
}
