package com.gxuwz.zjh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.Leave;
import com.gxuwz.zjh.entity.Student;
import com.gxuwz.zjh.entity.User;
import com.gxuwz.zjh.service.ILeaveService;
import com.gxuwz.zjh.service.IStudentService;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 请假表 前端控制器
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Controller
@RequestMapping("/zjh/leave")
public class LeaveController {

    private final String LIST_ADMIN = "leave/leave_list";
    private final String LIST_USER = "leave/leave_list_user";
    private final String LIST_INST = "leave/leave_list_inst";
    //  index 为界面跳转的参数，1：管理员；2：辅导员；3：学生

    @Autowired
    private ILeaveService iLeaveService;

    @Autowired
    private IStudentService iStudentService;


    /****
     * * * * * * * * * * * * * 管理员的leave方法
     * * * * * * * * * * * * * * * * * * * * *
     * @return
     */

    /**
     * 根据是否存在模糊查询内容跳转到不同的分页查询方法
     * @param modelAndView
     * @param leave
     * @param pageNumber
     * @param page
     * @param request
     * @return
     */

    @ResponseBody
    @GetMapping(value = "/nextPage")
    public ModelAndView nextPage(ModelAndView modelAndView, Leave leave, Integer pageNumber,
                                 Page page, HttpServletRequest request, Integer index) {
        if(request.getSession().getAttribute("result") != null){
            request.getSession().setAttribute("result", "");
        }
        if(leave.getLeaveId() == null){
            return findLeaveAll(modelAndView, page, pageNumber, request, index);
        }else {
            return findLeaveById(modelAndView, leave, pageNumber, page, request, index);
        }
    }

    /**
     * 根据对应用户id查询信息
     * @param modelAndView
     * @param leave
     * @param pageNumber
     * @param page
     * @param request
     * @return
     */

    @GetMapping(value = "/findLeaveById")
    public ModelAndView findLeaveById(ModelAndView modelAndView, Leave leave, Integer pageNumber,
                                        Page page, HttpServletRequest request, Integer index) {
        // 取出session 中的 index，并清空
        if(request.getSession().getAttribute("index") != null){
            index = (Integer) request.getSession().getAttribute("index");
            request.getSession().setAttribute("index", null);
        }
        if(request.getSession().getAttribute("result") != null){
            request.getSession().setAttribute("result", "");
        }
        // 可以通过 wrapper 进行筛选!!!
        QueryWrapper<Leave> wrapper = new QueryWrapper<>();
        // 判断是从哪个地方进来的
        if(index == 2){

        }else if(index == 3){
            if(request.getSession().getAttribute("user") != null){
                User user = (User) request.getSession().getAttribute("user");
                wrapper.like("stu_no", user.getUserId());
            }
        }
        wrapper.orderByAsc("leave_id");
        String[] keysWord = null;
        // 对User进行模糊查询!!!
        if(leave != null & leave.getLeaveId() != null){
            wrapper.like("leave_id", leave.getLeaveId());
            modelAndView.addObject("leave", leave);
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
        IPage<Leave> leaveIPage = iLeaveService.selectLeavePage(page, wrapper);
        // 存放一个数组用来让foreach遍历
        int[] pagesList = new int[(int)leaveIPage.getPages()];
        for(int i=0; i< (int)leaveIPage.getPages(); i++){
            pagesList[i] = i+1;
        }
        modelAndView.addObject("pagesList", pagesList);
        // 存放page，内有当前页数
        modelAndView.addObject("page", page);
        System.out.println("总条数"+leaveIPage.getTotal());
        System.out.println("总页数"+leaveIPage.getPages());
        // 存放总页数
        modelAndView.addObject("pages", (int)leaveIPage.getPages());
        modelAndView.addObject("numberPages", leaveIPage.getTotal());
        List<Leave> leaveList = leaveIPage.getRecords();
        System.out.println("leaveList = "+leaveList);
        modelAndView.addObject("leaveList", leaveList);
        if(index == 1){
            modelAndView.setViewName(LIST_ADMIN);
        }else if (index == 2){
            modelAndView.setViewName(LIST_INST);
        }else if (index == 3){
            modelAndView.setViewName(LIST_USER);
        }
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
    @GetMapping(value = "/findLeaveAll")
    public ModelAndView findLeaveAll(ModelAndView modelAndView, Page page, Integer pageNumber,
                                       HttpServletRequest request, Integer index) {
        // 取出session 中的 index，并清空
        if(request.getSession().getAttribute("index") != null){
            index = (Integer) request.getSession().getAttribute("index");
            request.getSession().setAttribute("index", null);
        }
        // 可以通过 wrapper 进行筛选!!!
        QueryWrapper<Leave> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("leave_id");
        // 判断是从哪个地方进来的
        if(index == 2){

        }else if(index == 3){
            if(request.getSession().getAttribute("user") != null){
                User user = (User) request.getSession().getAttribute("user");
                wrapper.like("stu_no", user.getUserId());
            }
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
        IPage<Leave> leaveIPage = iLeaveService.selectLeavePage(page, wrapper);
        HttpSession session = request.getSession();
        // 存放page，内有当前页数
        modelAndView.addObject("page", page);
        System.out.println("总条数"+leaveIPage.getTotal());
        System.out.println("总页数"+leaveIPage.getPages());
        // 存放总页数
        modelAndView.addObject("pages", (int)leaveIPage.getPages());
        // 存放一个数组用来让foreach遍历
        int[] pagesList = new int[(int)leaveIPage.getPages()];
        for(int i=0; i< (int)leaveIPage.getPages(); i++){
            pagesList[i] = i+1;
        }
        modelAndView.addObject("pagesList", pagesList);
        modelAndView.addObject("numberPages", leaveIPage.getTotal());
        List<Leave> leaveList = leaveIPage.getRecords();
        System.out.println("leaveList = "+leaveList);
        modelAndView.addObject("leaveList", leaveList);
        if(index == 1){
            modelAndView.setViewName(LIST_ADMIN);
        }else if (index == 2){
            modelAndView.setViewName(LIST_INST);
        }else if (index == 3){
            modelAndView.setViewName(LIST_USER);
        }
        return modelAndView;
    }

    /**
     * 添加信息 / 修改信息
     * @param leave
     * @return
     */
    @PostMapping(value = "/addEditLeave")
    public String addEditLeave(@Param("applyTime") String applyTime, @Param("auditTime") String auditTime,
                               Leave leave, HttpServletRequest request, Integer index ) throws ParseException {
        System.out.println("applyTime = " + applyTime);
        System.out.println("applyTime = " + auditTime);
        // 格式化日期时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String applyTime1 = null;
        String auditTime1 = null;
        if(applyTime != null){
            Date time1 = formatter.parse(applyTime);
            applyTime1 =formatter.format(time1);
        }
        if(auditTime != null){
            Date time2 = formatter.parse(auditTime);
            auditTime1 =formatter.format(time2);
        }
        System.out.println("applyTime1 = " + applyTime1);
        System.out.println("auditTime1 = " + auditTime1);

        leave.setApplyTime(applyTime1);
        leave.setAuditTime(auditTime1);

        Leave leave1 = iLeaveService.findLeaveById(leave);
        System.out.println("leave = " + leave);
        System.out.println("leave1 = " + leave1);

        // 接收参数传递 redirect
        request.getSession().setAttribute("index", index);

        if(index == 3){
            leave.setStatus("未审批");
        }

        // 新增用户信息
        if(leave1 == null){
            System.out.println("进入新增用户");
            try {
                iLeaveService.addLeave(leave);
                request.getSession().setAttribute("result", "addTrue");
            }catch (Exception e){
                request.getSession().setAttribute("result", "addFalse");
            }
        }
        // 修改用户信息
        if(leave1 != null){
            System.out.println("进入修改用户");
            try {
                iLeaveService.updateLeaveById(leave);
                request.getSession().setAttribute("result", "editTrue");
            }catch (Exception e){
                request.getSession().setAttribute("result", "editFalse");
            }
        }
        return "redirect:/zjh/leave/findLeaveAll";
    }

    /**
     * 删除信息
     * @param leaveId
     * @return
     */
    @GetMapping(value = "/deleteLeaveById")
    public String deleteLeaveById(HttpServletRequest request, @Param("leaveId") String leaveId, Integer index) {

        // 接收参数传递 redirect
        request.getSession().setAttribute("index", index);

        Leave leave = new Leave();
        leave.setLeaveId(leaveId);
        try {
            iLeaveService.deleteLeaveById(leave);
            request.getSession().setAttribute("result", "deleteTrue");
        }catch (Exception e){

        }
        return "redirect:/zjh/leave/findLeaveAll";
    }

    /**
     * 辅导员审批请假信息
     * @param leave
     * @return
     */
    @PostMapping(value = "/updateLeave")
    public String updateLeave(Leave leave, HttpServletRequest request, @Param("status2") Integer status) {

        if(status == 1){
            leave.setStatus("通过");
        }else if (status == 2){
            leave.setStatus("拒批");
        }else{
            leave.setStatus("未审批");
        }

        // 表明是从辅导员页面过来的
        request.getSession().setAttribute("index", 2);

        try {
            iLeaveService.updateLeaveById(leave);
            request.getSession().setAttribute("result", "editTrue");
        }catch (Exception e){
            request.getSession().setAttribute("result", "editFalse");
        }

        return "redirect:/zjh/leave/findLeaveAll";
    }

}
