package com.baidu.leave.service.impl;

import com.baidu.leave.dao.ClassesMapper;
import com.baidu.leave.pojo.Classes;
import com.baidu.leave.service.ClassesService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesServiceImpl implements ClassesService {

    @Autowired
    private ClassesMapper classesMapper;

    @Override
    public Classes findClassesById(Classes classes) {
        return classesMapper.findClassesById(classes);
    }

    @Override
    public List<Classes> findClassesAll() {
        return classesMapper.findClassesAll();
    }

    @Override
    public IPage<Classes> selectClassesPage(Page<Classes> page, Wrapper<Classes> wrapper) {
        return classesMapper.selectClassesPage(page, wrapper);
    }

    @Override
    public void addClasses(Classes classes) {
        classesMapper.addClasses(classes);
    }

    @Override
    public void updateClassesById(Classes classes) {
        classesMapper.updateClassesById(classes);
    }

    @Override
    public void deleteClassesById(Classes classes) {
        classesMapper.deleteClassesById(classes);
    }
}
