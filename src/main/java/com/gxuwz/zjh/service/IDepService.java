package com.gxuwz.zjh.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 二级学院表 服务类
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
public interface IDepService {

    // 根据 id 查询对应用户信息
    Department findDepById(Department department);

    // 查询全部用户信息
    List<Department> findDepAll();

    //自定义sql 分页
    IPage<Department> selectDepPage(Page<Department> page, @Param(Constants.WRAPPER) Wrapper<Department> wrapper);

    // 添加用户信息
    void addDep(Department department);

    // 修改用户信息
    void updateDepById(Department department);

    // 删除用户信息
    void deleteDepById(Department department);

}
