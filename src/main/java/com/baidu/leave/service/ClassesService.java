package com.baidu.leave.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baidu.leave.pojo.Classes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 班级表 服务类
 * </p>
 *
 */
public interface ClassesService {

    // 根据 id 查询对应用户信息
    Classes findClassesById(Classes classes);

    // 查询全部用户信息
    List<Classes> findClassesAll();

    //自定义sql 分页
    IPage<Classes> selectClassesPage(Page<Classes> page, @Param(Constants.WRAPPER) Wrapper<Classes> wrapper);

    // 添加用户信息
    void addClasses(Classes classes);

    // 修改用户信息
    void updateClassesById(Classes classes);

    // 删除用户信息
    void deleteClassesById(Classes classes);

}
