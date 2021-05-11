package com.gxuwz.zjh.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxuwz.zjh.entity.Leave;
import com.gxuwz.zjh.mapper.LeaveMapper;
import com.gxuwz.zjh.service.ILeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 请假表 服务实现类
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Service
public class LeaveServiceImpl implements ILeaveService {

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
