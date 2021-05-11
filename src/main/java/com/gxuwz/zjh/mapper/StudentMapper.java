package com.gxuwz.zjh.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gxuwz.zjh.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
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
