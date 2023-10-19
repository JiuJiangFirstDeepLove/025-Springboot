package com.baidu.leave.service.impl;

import com.baidu.leave.dao.StudentMapper;
import com.baidu.leave.pojo.Student;
import com.baidu.leave.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student findStudentById(Student student) {
        return studentMapper.findStudentById(student);
    }

    @Override
    public  List<Student> findStudentByClassId(Student student) {
        return studentMapper.findStudentByClassId(student);
    }

    @Override
    public List<Student> findStudentAll() {
        return studentMapper.findStudentAll();
    }

    @Override
    public IPage<Student> selectStudentPage(Page<Student> page, Wrapper<Student> wrapper) {
        return studentMapper.selectStudentPage(page, wrapper);
    }

    @Override
    public void addStudent(Student student) {
        studentMapper.addStudent(student);
    }

    @Override
    public void updateStudentById(Student student) {
        studentMapper.updateStudentById(student);
    }

    @Override
    public void deleteStudentById(Student student) {
        studentMapper.deleteStudentById(student);
    }
}
