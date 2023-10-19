package com.baidu.leave.service.impl;

import com.baidu.leave.dao.CourseMapper;
import com.baidu.leave.pojo.Course;
import com.baidu.leave.service.CourseService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Course findCourseById(Course course) {
        return courseMapper.findCourseById(course);
    }

    @Override
    public List<Course> findCourseAll() {
        return courseMapper.findCourseAll();
    }

    @Override
    public IPage<Course> selectCoursePage(Page<Course> page, Wrapper<Course> wrapper) {
        return courseMapper.selectCoursePage(page, wrapper);
    }

    @Override
    public void addCourse(Course course) {
        courseMapper.addCourse(course);
    }

    @Override
    public void updateCourseById(Course course) {
        courseMapper.updateCourseById(course);
    }

    @Override
    public void deleteCourseById(Course course) {
        courseMapper.deleteCourseById(course);
    }
}
