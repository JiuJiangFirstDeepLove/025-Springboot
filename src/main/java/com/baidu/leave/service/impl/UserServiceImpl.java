package com.baidu.leave.service.impl;

import com.baidu.leave.dao.UserMapper;
import com.baidu.leave.pojo.User;
import com.baidu.leave.service.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(User user) {
        return userMapper.findUserById(user);
    }

    @Override
    public List<User> findUserAll() {
        return userMapper.findUserAll();
    }

    @Override
    public IPage<User> selectUserPage(Page<User> page, Wrapper<User> wrapper) {
        return userMapper.selectUserPage(page, wrapper);
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public void updateUserById(User user) {
        userMapper.updateUserById(user);
    }

    @Override
    public void deleteUserById(User user) {
        userMapper.deleteUserById(user);
    }
}
