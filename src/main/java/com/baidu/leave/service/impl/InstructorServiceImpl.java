package com.baidu.leave.service.impl;

import com.baidu.leave.dao.InstructorMapper;
import com.baidu.leave.pojo.Instructor;
import com.baidu.leave.service.InstructorService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorMapper instructorMapper;

    @Override
    public Instructor findInstructorById(Instructor instructor) {
        return instructorMapper.findInstructorById(instructor);
    }

    @Override
    public List<Instructor> findInstructorAll() {
        return instructorMapper.findInstructorAll();
    }

    @Override
    public IPage<Instructor> selectInstructorPage(Page<Instructor> page, Wrapper<Instructor> wrapper) {
        return instructorMapper.selectInstructorPage(page, wrapper);
    }

    @Override
    public void addInstructor(Instructor instructor) {
        instructorMapper.addInstructor(instructor);
    }

    @Override
    public void updateInstructorById(Instructor instructor) {
        instructorMapper.updateInstructorById(instructor);
    }

    @Override
    public void deleteInstructorById(Instructor instructor) {
        instructorMapper.deleteInstructorById(instructor);
    }
}
