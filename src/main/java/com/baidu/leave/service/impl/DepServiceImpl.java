package com.baidu.leave.service.impl;

import com.baidu.leave.dao.DepMapper;
import com.baidu.leave.pojo.Department;
import com.baidu.leave.service.DepService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepServiceImpl implements DepService {

    @Autowired
    private DepMapper depMapper;

    @Override
    public Department findDepById(Department department) {
        return depMapper.findDepById(department);
    }

    @Override
    public List<Department> findDepAll() {
        return depMapper.findDepAll();
    }

    @Override
    public IPage<Department> selectDepPage(Page<Department> page, Wrapper<Department> wrapper) {
        return depMapper.selectDepPage(page, wrapper);
    }

    @Override
    public void addDep(Department department) {
        depMapper.addDep(department);
    }

    @Override
    public void updateDepById(Department department) {
        depMapper.updateDepById(department);
    }

    @Override
    public void deleteDepById(Department department) {
        depMapper.deleteDepById(department);
    }
}
