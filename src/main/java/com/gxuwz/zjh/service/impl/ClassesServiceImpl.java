package com.gxuwz.zjh.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.Classes;
import com.gxuwz.zjh.mapper.ClassesMapper;
import com.gxuwz.zjh.service.IClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 班级表 服务实现类
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Service
public class ClassesServiceImpl implements IClassesService {

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
