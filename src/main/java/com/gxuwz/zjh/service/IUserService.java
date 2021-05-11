package com.gxuwz.zjh.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
public interface IUserService {

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
