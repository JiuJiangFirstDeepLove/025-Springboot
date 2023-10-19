package com.baidu.leave.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baidu.leave.pojo.Instructor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InstructorService {

    // 根据 id 查询对应用户信息
    Instructor findInstructorById(Instructor instructor);

    // 查询全部用户信息
    List<Instructor> findInstructorAll();

    //自定义sql 分页
    IPage<Instructor> selectInstructorPage(Page<Instructor> page, @Param(Constants.WRAPPER) Wrapper<Instructor> wrapper);

    // 添加用户信息
    void addInstructor(Instructor instructor);

    // 修改用户信息
    void updateInstructorById(Instructor instructor);

    // 删除用户信息
    void deleteInstructorById(Instructor instructor);

}
