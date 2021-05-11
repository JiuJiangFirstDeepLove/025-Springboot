package com.gxuwz.zjh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.Department;
import com.gxuwz.zjh.service.IDepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 二级学院表 前端控制器
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Controller
@RequestMapping("/zjh/dep")
public class DepController {

    @Autowired
    private IDepService iDepService;

    /**
     * 根据是否存在模糊查询内容跳转到不同的分页查询方法
     * @param modelAndView
     * @param department
     * @param pageNumber
     * @param page
     * @param request
     * @return
     */

    @ResponseBody
    @GetMapping(value = "/nextPage")
    public ModelAndView nextPage(ModelAndView modelAndView, Department department, Integer pageNumber,
                                 Page page, HttpServletRequest request) {
        if(request.getSession().getAttribute("result") != null){
            request.getSession().setAttribute("result", "");
        }
        if(department.getDepId() == null){
            return findDepAll(modelAndView, page, pageNumber, request);
        }else {
            return findDepById(modelAndView, department, pageNumber, page, request);
        }
    }

    /**
     * 根据对应id查询信息
     * @param modelAndView
     * @param department
     * @param pageNumber
     * @param page
     * @param request
     * @return
     */

    @GetMapping(value = "/findDepById")
    public ModelAndView findDepById(ModelAndView modelAndView, Department department, Integer pageNumber,
                                       Page page, HttpServletRequest request) {
        if(request.getSession().getAttribute("result") != null){
            request.getSession().setAttribute("result", "");
        }
        // 可以通过 wrapper 进行筛选!!!
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("dep_id");
        String[] keysWord = null;
        // 对User进行模糊查询!!!
        if(department != null & department.getDepId() != null){
            wrapper.like("dep_id", department.getDepId());
            modelAndView.addObject("department", department);
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
        IPage<Department> departmentIPage = iDepService.selectDepPage(page, wrapper);
        // 存放一个数组用来让foreach遍历
        int[] pagesList = new int[(int)departmentIPage.getPages()];
        for(int i=0; i< (int)departmentIPage.getPages(); i++){
            pagesList[i] = i+1;
        }
        modelAndView.addObject("pagesList", pagesList);
        // 存放page，内有当前页数
        modelAndView.addObject("page", page);
        System.out.println("总条数"+departmentIPage.getTotal());
        System.out.println("总页数"+departmentIPage.getPages());
        // 存放总页数
        modelAndView.addObject("pages", (int)departmentIPage.getPages());
        modelAndView.addObject("numberPages", departmentIPage.getTotal());
        List<Department> depList = departmentIPage.getRecords();
        System.out.println("depList = "+depList);
        modelAndView.addObject("depList", depList);

        modelAndView.setViewName("dep/dep_list");
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
    @GetMapping(value = "/findDepAll")
    public ModelAndView findDepAll(ModelAndView modelAndView, Page page, Integer pageNumber,
                                      HttpServletRequest request) {
        // 可以通过 wrapper 进行筛选!!!
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("dep_id");
        // Current,页码 + Size,每页条数
        if(pageNumber == null){
            page.setCurrent(1);
        }else {
            page.setCurrent((long)pageNumber);
        }
        // 默认每页6行数据！
        page.setSize(6);
        // 调用分页查询方法！!
        IPage<Department> departmentIPage = iDepService.selectDepPage(page, wrapper);
        HttpSession session = request.getSession();
        // 存放page，内有当前页数
        modelAndView.addObject("page", page);
        System.out.println("总条数"+departmentIPage.getTotal());
        System.out.println("总页数"+departmentIPage.getPages());
        // 存放总页数
        modelAndView.addObject("pages", (int)departmentIPage.getPages());
        // 存放一个数组用来让foreach遍历
        int[] pagesList = new int[(int)departmentIPage.getPages()];
        for(int i=0; i< (int)departmentIPage.getPages(); i++){
            pagesList[i] = i+1;
        }
        modelAndView.addObject("pagesList", pagesList);
        modelAndView.addObject("numberPages", departmentIPage.getTotal());
        List<Department> depList = departmentIPage.getRecords();
        System.out.println("depList = "+depList);
        modelAndView.addObject("depList", depList);

        modelAndView.setViewName("dep/dep_list");
        return modelAndView;
    }

    /**
     * 添加用户信息 / 修改用户信息
     * @param department
     * @return
     */
    @PostMapping(value = "/addEditDep")
    public String addEditDep(Department department, HttpServletRequest request) {
        Department department1 = iDepService.findDepById(department);
        System.out.println("department = " + department);
        System.out.println("department1 = " + department1);
        // 新增用户信息
        if(department1 == null){
            System.out.println("进入新增用户");
            try {
                iDepService.addDep(department);
                request.getSession().setAttribute("result", "addTrue");
            }catch (Exception e){
                request.getSession().setAttribute("result", "addFalse");
            }
        }
        // 修改用户信息
        if(department1 != null){
            System.out.println("进入修改用户");
            try {
                iDepService.updateDepById(department);
                request.getSession().setAttribute("result", "editTrue");
            }catch (Exception e){
                request.getSession().setAttribute("result", "editFalse");
            }
        }
        return "redirect:/zjh/dep/findDepAll";
    }

    /**
     * 删除用户信息
     * @param depId
     * @return
     */
    @GetMapping(value = "/deleteDepById")
    public String deleteDepById(HttpServletRequest request, String depId) {
        Department department = new Department();
        department.setDepId(depId);
        try {
            iDepService.deleteDepById(department);
            request.getSession().setAttribute("result", "deleteTrue");
        }catch (Exception e){

        }
        return "redirect:/zjh/dep/findDepAll";
    }

}
