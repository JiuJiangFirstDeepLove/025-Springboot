package com.baidu.leave.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baidu.leave.pojo.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseMapper {

    // 根据 id 查询对应用户信息
    Course findCourseById(Course course);

    // 查询全部用户信息
    List<Course> findCourseAll();

    //自定义sql 分页
    IPage<Course> selectCoursePage(Page<Course> page, @Param(Constants.WRAPPER) Wrapper<Course> wrapper);

    // 添加用户信息
    void addCourse(Course course);

    // 修改用户信息
    void updateCourseById(Course course);

    // 删除用户信息
    void deleteCourseById(Course course);
    
}
