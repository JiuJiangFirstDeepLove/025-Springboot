package com.gxuwz.zjh.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.Instructor;
import com.gxuwz.zjh.mapper.InstructorMapper;
import com.gxuwz.zjh.service.IInstructorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 辅导员表 服务实现类
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Service
public class InstructorServiceImpl implements IInstructorService {

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
