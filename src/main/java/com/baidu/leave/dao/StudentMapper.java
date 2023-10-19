package com.baidu.leave.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baidu.leave.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {

    // 根据 id 查询对应用户信息
    Student findStudentById(Student student);

    List<Student> findStudentByClassId(Student student);

    // 查询全部用户信息
    List<Student> findStudentAll();

    //自定义sql 分页
    IPage<Student> selectStudentPage(Page<Student> page, @Param(Constants.WRAPPER) Wrapper<Student> wrapper);

    // 添加用户信息
    void addStudent(Student student);

    // 修改用户信息
    void updateStudentById(Student student);

    // 删除用户信息
    void deleteStudentById(Student student);

}
