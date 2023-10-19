package com.baidu.leave.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baidu.leave.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    // 根据 id 查询对应用户信息
    User findUserById(User user);

    // 查询全部用户信息
    List<User> findUserAll();

    //自定义sql 分页
    IPage<User> selectUserPage(Page<User> page, @Param(Constants.WRAPPER) Wrapper<User> wrapper);

    // 添加用户信息
    void addUser(User user);

    // 修改用户信息
    void updateUserById(User user);

    // 删除用户信息
    void deleteUserById(User user);

}
