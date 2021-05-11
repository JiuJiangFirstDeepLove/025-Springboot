package com.gxuwz.zjh.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.Department;
import com.gxuwz.zjh.mapper.DepMapper;
import com.gxuwz.zjh.service.IDepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 二级学院表 服务实现类
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Service
public class DepServiceImpl implements IDepService {

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
