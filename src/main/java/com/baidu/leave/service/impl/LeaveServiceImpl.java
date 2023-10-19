package com.baidu.leave.service.impl;

import com.baidu.leave.dao.LeaveMapper;
import com.baidu.leave.pojo.Leave;
import com.baidu.leave.service.LeaveService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;

    @Override
    public Leave findLeaveById(Leave leave) {
        return leaveMapper.findLeaveById(leave);
    }

    @Override
    public  List<Leave> findLeaveByStuNo(Leave leave) {
        return leaveMapper.findLeaveByStuNo(leave);
    }

    @Override
    public List<Leave> findLeaveAll() {
        return leaveMapper.findLeaveAll();
    }

    @Override
    public IPage<Leave> selectLeavePage(Page<Leave> page, Wrapper<Leave> wrapper) {
        return leaveMapper.selectLeavePage(page, wrapper);
    }

    @Override
    public void addLeave(Leave leave) {
        leaveMapper.addLeave(leave);
    }

    @Override
    public void updateLeaveById(Leave leave) {
        leaveMapper.updateLeaveById(leave);
    }

    @Override
    public void deleteLeaveById(Leave leave) {
        leaveMapper.deleteLeaveById(leave);
    }
}
