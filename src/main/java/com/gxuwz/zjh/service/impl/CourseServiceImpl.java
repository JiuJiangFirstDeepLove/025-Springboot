package com.gxuwz.zjh.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.Course;
import com.gxuwz.zjh.mapper.CourseMapper;
import com.gxuwz.zjh.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Service
public class CourseServiceImpl implements ICourseService {

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
